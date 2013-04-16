package edu.gatech.oad.rocket.findmythings;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import com.google.api.services.fmthings.model.CollectionResponseDBItem;
import com.google.api.services.fmthings.model.DBItem;
import edu.gatech.oad.rocket.findmythings.list.*;
import edu.gatech.oad.rocket.findmythings.service.EndpointUtils;
import edu.gatech.oad.rocket.findmythings.service.Fmthings;
import edu.gatech.oad.rocket.findmythings.shared.Type;

import java.util.List;

public class ItemListFragment extends ArrayListFragment<DBItem, ItemFilterConstraint> {

	public static final String ARG_TYPE = "type";
	public static final String ARG_QUERY = "searchQuery";

	public static final int LOAD_LIMIT = 25;

	private String lastNextPageToken = null;

	public ItemListFragment() {
		super();
	}

	static ItemListFragment newInstance(Type type) {
		ItemListFragment f = new ItemListFragment();

		// Supply input as an argument.
		Bundle args = new Bundle();
		args.putSerializable(ARG_TYPE, type);
		f.setArguments(args);

		return f;
	}

	private String getSearchQuery() {
		return getArguments().getString(ARG_QUERY);
	}

	private Type getType() {
		return (Type)getArguments().getSerializable(ARG_TYPE);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setEmptyText(R.string.no_items_found);
	}

	@Override
	public Loader<List<DBItem>> onCreateLoader(int id, Bundle args) {
		if (isForceRefresh(args)) lastNextPageToken = null;
		// TODO Auto-generated method stub
		return new ListAsyncTaskLoader<DBItem>(getActivity()){

			@Override
			public List<DBItem> loadData() throws Exception {
				// limit, type, cursor
				Fmthings.Items.List query = EndpointUtils.getEndpoint().items().list();

				query.setLimit(LOAD_LIMIT);

				Type type = getType();
				if (type != null) query.setType(type.toString());

				if (lastNextPageToken != null) query.setCursor(lastNextPageToken);

				String searchQuery = getSearchQuery();
				if (!TextUtils.isEmpty(searchQuery)) query.setQuery(searchQuery);

				CollectionResponseDBItem result = query.execute();
				lastNextPageToken = result.getNextPageToken();
				return result.getItems();
			}
		};
	}

	@Override
	protected int getErrorMessage(Exception exception) {
		return R.string.error_loading_items;
	}

	@Override
	protected CustomArrayAdapter<DBItem, ItemFilterConstraint> onCreateAdapter() {
		return new AlternatingTwoLineListAdapter<DBItem, ItemFilterConstraint>(getActivity()) {
			@Override
			public boolean applyFilter(DBItem object, ItemFilterConstraint constraint) {
				// TODO
				return false;
			}
		};
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
        DBItem item = ((DBItem) l.getItemAtPosition(position));
		startActivity(new Intent(getActivity(), ItemDetailActivity.class).putExtra(ItemDetailActivity.ITEM_EXTRA, item));
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

}
