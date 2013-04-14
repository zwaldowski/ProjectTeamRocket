package edu.gatech.oad.rocket.findmythings;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.api.services.fmthings.model.CollectionResponseDBItem;
import com.google.api.services.fmthings.model.DBItem;
import edu.gatech.oad.rocket.findmythings.list.AlternatingTwoLineListAdapter;
import edu.gatech.oad.rocket.findmythings.list.ArrayListFragment;
import edu.gatech.oad.rocket.findmythings.list.ThrowableLoader;
import edu.gatech.oad.rocket.findmythings.model.Type;
import edu.gatech.oad.rocket.findmythings.service.EndpointUtils;
import edu.gatech.oad.rocket.findmythings.service.Fmthings;

import java.util.List;

public class ItemListFragment extends ArrayListFragment<DBItem> {

	public static final int LOAD_LIMIT = 25;

	// class needs to be changed to alter these
	private boolean hasType = true;
	private Type type = Type.LOST;
	private String lastNextPageToken = null;
	private String searchQuery = null;

	public static ItemListFragment newInstance(Type type) {

		ItemListFragment fragment = new ItemListFragment();

		fragment.hasType = type != null;
		fragment.type = type;

		return fragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		((TextView)getListView().getEmptyView()).setText(R.string.no_items_found);
	}

	@Override
	public Loader<List<DBItem>> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return new ThrowableLoader<List<DBItem>>(getActivity()){

			@Override
			public List<DBItem> loadData() throws Exception {
				// limit, type, cursor
				Fmthings.Items.List query = EndpointUtils.getEndpoint().items().list();

				query.setLimit(LOAD_LIMIT);
				if (hasType) query.setType(type.toString());
				if (lastNextPageToken != null) query.setCursor(lastNextPageToken);
				if (searchQuery != null) query.setQuery(searchQuery);

				CollectionResponseDBItem result = query.execute();
				lastNextPageToken = result.getNextPageToken();
				return result.getItems();
			}
		};
	}

	@Override
	protected ArrayAdapter<DBItem> onCreateAdapter() {
		return new AlternatingTwoLineListAdapter<DBItem>(getActivity());
	}

	@Override
	protected int getErrorMessage(Exception exception) {
		return R.string.error_loading_items;
	}

    public void onListItemClick(ListView l, View v, int position, long id) {
        DBItem item = ((DBItem) l.getItemAtPosition(position));
		startActivity(new Intent(getActivity(), ItemDetailActivity.class).putExtra(ItemDetailActivity.ITEM_EXTRA, item));
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

}
