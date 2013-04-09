package edu.gatech.oad.rocket.findmythings.server.spi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBItem;
import edu.gatech.oad.rocket.findmythings.server.util.SearchableHelper;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.HashMap;

@Api(name = "fmthings", version = "v1")
public class ItemV1 extends BaseEndpoint {

	@ApiMethod(name = "items.list", path = "items")
	public CollectionResponse<DBItem> listItems(@Nullable @Named("type") String type,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {
		HashMap<String, Object> filter = new HashMap<>();
		filter.put("type", type);
		Query<DBItem> baseQuery = DatabaseService.ofy().load().type(DBItem.class).order("-dateSubmitted");
		return ItemV1.pagedQuery(baseQuery, cursorString, limit, filter);
	}

	@ApiMethod(name = "items.get", path = "items/get")
	public DBItem getItem(@Named("id") Long id) {
		return DatabaseService.ofy().load().type(DBItem.class).id(id).get();
	}

	@ApiMethod(name = "items.insert", path = "items/insert")
	public DBItem insertItem(DBItem item) {
		if (currentUserCanSubmit()) {
			Key<DBItem> result = DatabaseService.ofy().save().entity(item).now();
			return DatabaseService.ofy().load().key(result).get();
		}
		return null;
	}

	@ApiMethod(name = "items.update", path = "items/update")
	public DBItem updateItem(DBItem item) {
		if (currentUserIsAdmin() || item.getSubmittingUser().equals(getCurrentMemberEmail())) {
			Key<DBItem> result = DatabaseService.ofy().save().entity(item).now();
			return DatabaseService.ofy().load().key(result).get();
		}
		return item;
	}

	@ApiMethod(name = "items.delete", path = "items/delete")
	public DBItem removeItem(@Named("id") Long id) {
		DBItem ret = DatabaseService.ofy().load().type(DBItem.class).id(id).get();
		DatabaseService.ofy().delete().entity(ret);
		return ret;
	}

	@ApiMethod(name = "items.search", path = "search")
	public CollectionResponse<DBItem> searchItems(@Named("query") String query,
			@Nullable @Named("type") String type,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {
		HashMap<String, Object> filter = new HashMap<>();
		filter.put("type", type);
		SearchableHelper.addSearchFilter(filter, query);
		return ItemV1.pagedQuery(DBItem.class, cursorString, limit, filter);
	}

	@ApiMethod(name = "items.getMine", path = "items/mine")
	public CollectionResponse<DBItem> listMyItems(@Nullable @Named("type") String type,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {
		String email = getCurrentMemberEmail();
		if (email == null) return null;
		return listItemsForMember(email, type, cursorString, limit);
	}

	@ApiMethod(name = "items.getByUser", path = "items/forMember")
	public CollectionResponse<DBItem> listItemsForMember(@Named("email") String email,
			@Nullable @Named("type") String type,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {
		HashMap<String, Object> filter = new HashMap<>();
		filter.put("type", type);
		filter.put("submittingUser", email);
		return ItemV1.pagedQuery(DBItem.class, cursorString, limit, filter);
	}

}
