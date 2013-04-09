package edu.gatech.oad.rocket.findmythings.server.spi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.security.ProfileRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.CollectionResponse.Builder;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

abstract class BaseEndpoint {
	
	static <T> void pagedQueryArray(Query<T> query, String cursorString, Integer limit, Map<String, Object> filters, List<T> outList, StringBuilder outCursorString) {
		Cursor cursor = cursorString == null ? null : Cursor.fromWebSafeString(cursorString);
		if (filters != null) {
			for (Entry<String, Object> entry : filters.entrySet()) {
				Object value = entry.getValue();
				if (value != null) query = query.filter(entry.getKey(), entry.getValue());
			}
		}
		if (cursor != null) query = query.startAt(cursor);
		if (limit != null) query = query.limit(limit);

		QueryResultIterator<T> iterator = query.chunk(Integer.MAX_VALUE).iterator();
		while (iterator.hasNext()) {
			outList.add(iterator.next());
		}

		cursor = iterator.getCursor();
		if (cursor != null) {
			cursorString = cursor.toWebSafeString();
		} else {
			cursorString = "";
		}
		outCursorString.setLength(0);
		outCursorString.append(cursorString);
	}

	static <T> CollectionResponse<T> pagedQuery(Query<T> query, String cursorString, Integer limit, Map<String, Object> filters) {
		List<T> list = new ArrayList<>();
		StringBuilder outCursorString = new StringBuilder();
		pagedQueryArray(query, cursorString, limit, filters, list, outCursorString);
		Builder<T> build = CollectionResponse.<T>builder().setItems(list);
		if (outCursorString.length() > 0) build = build.setNextPageToken(outCursorString.toString());
		return build.build();
	}

	static <T> CollectionResponse<T> pagedQuery(Class<T> clazz, String cursorString, Integer limit, Map<String, Object> filters) {
		Query<T> query = DatabaseService.ofy().load().type(clazz);
		return pagedQuery(query, cursorString, limit, filters);
	}

	AppMember getMemberWithEmail(String email) {
		if (email == null || email.length() == 0) return null;
		RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		for (Realm realm : manager.getRealms()) {
			if (realm instanceof ProfileRealm) {
				AppMember potential = ((ProfileRealm) realm).getAccount(email);
				if (potential != null && potential.getEmail().equals(email)) return potential;
			}
		}
		return null;
	}

	boolean memberExistsWithEmail(String email) {
		if (email == null || email.length() == 0) return false;
		RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		for (Realm realm : manager.getRealms()) {
			if (realm instanceof ProfileRealm) {
				if (((ProfileRealm) realm).accountExists(email)) return true;
			}
		}
		return false;
	}
	
	String getCurrentMemberEmail() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		if (principals == null || principals.isEmpty()) return null;
		return (String)principals.getPrimaryPrincipal();
	}
	
	boolean currentUserIsAdmin() {
		return SecurityUtils.getSubject().hasRole("admin");
	}
	
	boolean currentUserCanSubmit() {
		return SecurityUtils.getSubject().isPermitted("submit") || SecurityUtils.getSubject().hasRole("admin");
	}

}
