package edu.gatech.oad.rocket.findmythings.server.spi;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBMember;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.security.ProfileIniRealm;

import javax.annotation.Nullable;
import javax.inject.Named;

@Api(name = "fmthings", version = "v1")
public class MemberV1 extends BaseEndpoint {

	@ApiMethod(name = "members.list", path = "members")
	public CollectionResponse<AppMember> listMembers(@Nullable @Named("cursor") String cursorString,
													 @Nullable @Named("limit") Integer limit) {
		List<AppMember> list = new ArrayList<>();
		Cursor cursor; int bakedInOffset;
		if (cursorString != null && cursorString.startsWith("FMTBAKEDIN")) {
			cursor = null;
			String[] split = cursorString.split("-");
			bakedInOffset = split.length == 1 ? -1 : Integer.parseInt(split[1]);
		} else if (cursorString == null) {
			cursor = null;
			bakedInOffset = 0;
		} else {
			cursor = Cursor.fromWebSafeString(cursorString);
			bakedInOffset = -1;
		}

		int bakedInIncludeCount = 0;
		if (bakedInOffset != -1) {
			List<AppMember> bakedInMembers = new ArrayList<>();
			RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
			for (Realm realm : manager.getRealms()) {
				if (realm instanceof ProfileIniRealm) {
					bakedInMembers.addAll(((ProfileIniRealm) realm).getMembers());
				}
			}

			list.addAll(bakedInMembers.subList(bakedInOffset, bakedInMembers.size()));
			bakedInIncludeCount = bakedInMembers.size() - bakedInOffset;
		}

		if (limit != null && bakedInIncludeCount <= limit) {
			cursorString = bakedInIncludeCount == limit ? "FMTBAKEDIN" : "FMTBAKEDIN-" + bakedInIncludeCount;
		} else {
			Query<DBMember> baseQuery = DatabaseService.ofy().load().type(DBMember.class).order("dateRegistered");
			if (cursor != null) baseQuery = baseQuery.startAt(cursor);
			if (limit != null) baseQuery = baseQuery.limit(limit);

			QueryResultIterator<DBMember> iterator = baseQuery.chunk(Integer.MAX_VALUE).iterator();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}

			cursor = iterator.getCursor();
			if (cursor != null) {
				cursorString = cursor.toWebSafeString();
			} else {
				cursorString = "";
			}
		}

		return CollectionResponse.<AppMember>builder().setItems(list).setNextPageToken(cursorString).build();
	}

	@ApiMethod(name = "members.get", path = "members/get")
	public AppMember getMember(@Named("email") String email) {
		return getMemberWithEmail(email);
	}

	@ApiMethod(name = "members.update", path = "members/update")
	public void updateMember(AppMember member) {
		if (member.isEditable()) {
			DatabaseService.ofy().save().entity((DBMember)member);
		}
	}
}
