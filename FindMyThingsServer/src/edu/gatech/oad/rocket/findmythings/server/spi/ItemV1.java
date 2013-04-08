package edu.gatech.oad.rocket.findmythings.server.spi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBItem;
import edu.gatech.oad.rocket.findmythings.server.util.SearchableHelper;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Api(name = "fmthings", version = "v1")
public class ItemV1 extends BaseEndpoint {

	private CollectionResponse<DBItem> getItemList(Query<DBItem> query, String type, String cursorString, Integer limit) {
		Cursor cursor = cursorString == null ? null : Cursor.fromWebSafeString(cursorString);
		if (type != null) query = query.filter("type", type);
		if (cursor != null) query = query.startAt(cursor);
		if (limit != null) query = query.limit(limit);

		QueryResultIterator<DBItem> iterator = query.chunk(Integer.MAX_VALUE).iterator();
		List<DBItem> list = new ArrayList<>();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}

		cursor = iterator.getCursor();
		if (cursor != null) {
			cursorString = cursor.toWebSafeString();
		} else {
			cursorString = "";
		}

		return CollectionResponse.<DBItem>builder().setItems(list).setNextPageToken(cursorString).build();
	}

	@ApiMethod(name = "items.list", path = "items")
	public CollectionResponse<DBItem> listItems(@Nullable @Named("type") String type,
												@Nullable @Named("cursor") String cursorString,
												@Nullable @Named("limit") Integer limit) {
		Query<DBItem> baseQuery = DatabaseService.ofy().load().type(DBItem.class).order("-dateSubmitted");
		return getItemList(baseQuery, type, cursorString, limit);
	}

	@ApiMethod(name = "items.get", path = "items/get")
	public DBItem getItem(@Named("id") Long id) {
		return DatabaseService.ofy().load().type(DBItem.class).id(id).get();
	}

	@ApiMethod(name = "items.insert", path = "items/insert")
	public DBItem insertItem(DBItem item) {
		Key<DBItem> result = DatabaseService.ofy().save().entity(item).now();
		return DatabaseService.ofy().load().key(result).get();
	}

	@ApiMethod(name = "items.update", path = "items/update")
	public DBItem updateItem(DBItem item) {
		Key<DBItem> result = DatabaseService.ofy().save().entity(item).now();
		return DatabaseService.ofy().load().key(result).get();
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
		Query<DBItem> baseQuery = SearchableHelper.search(DatabaseService.ofy(), DBItem.class, query);
		return getItemList(baseQuery, type, cursorString, limit);
	}

}
