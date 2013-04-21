package edu.gatech.oad.rocket.findmythings;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.api.client.util.DateTime;
import edu.gatech.oad.rocket.findmythings.list.*;
import edu.gatech.oad.rocket.findmythings.model.Category;
import edu.gatech.oad.rocket.findmythings.model.CollectionResponseDBItem;
import edu.gatech.oad.rocket.findmythings.model.DBItem;
import edu.gatech.oad.rocket.findmythings.model.Type;
import edu.gatech.oad.rocket.findmythings.service.EndpointUtils;
import edu.gatech.oad.rocket.findmythings.service.Fmthings;

import java.util.Date;
import java.util.List;

public class ItemListFragment extends FilterableArrayListFragment<DBItem, ItemFilterConstraint> {

	public static final String ARG_TYPE = "type";
	public static final String ARG_QUERY = "searchQuery";

	public static final int LOAD_LIMIT = 25;

	private String lastNextPageToken = null;

	private View mLoadMoreFooter;

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
	protected FilterableArrayListAdapter<DBItem, ItemFilterConstraint> onCreateAdapter() {
		mLoadMoreFooter = getActivity().getLayoutInflater().inflate(R.layout.list_footer_button, null);
		TextView label = (TextView)mLoadMoreFooter.getRootView().findViewById(R.id.button_cell_title);
		label.setText(R.string.item_load_more);
		getListView().addFooterView(mLoadMoreFooter);

		return new AlternatingFilterableArrayListAdapter<DBItem, ItemFilterConstraint>(getActivity()) {
			@Override
			public boolean applyFilter(DBItem object, ItemFilterConstraint constraint) {
				Date consDate = constraint.getDateAfter();
				if (consDate != null) {
					DateTime objDate = object.getDate();
					if (objDate == null) return false;
					Date objTime = new Date(objDate.getValue());
					if (objTime.before(consDate)) return false;
				}

				Boolean consOpen = constraint.isOpen();
				if (consOpen != null) {
					boolean objOpen = object.getOpen();
					if (objOpen != consOpen) return false;
				}

				Category consCat = constraint.getCategory();
				if (consCat != null) {
					String objCatString = object.getCategory();
					if (TextUtils.isEmpty(objCatString)) return false;
					Category objCat = Category.valueOf(objCatString);
					if (consCat != objCat) return false;
				}

				return true;
			}
		};
	}

	public void onListItemClick(ListView l, View v, int position, long id) {

		if (l.getAdapter().getItemViewType(position) != AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
			DBItem item = ((DBItem) l.getItemAtPosition(position));

			// this is a hash-map!
			startActivity(new Intent(getActivity(), ItemDetailActivity.class).putExtra(ItemDetailActivity.ITEM_EXTRA, item));
			getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		} else {
			if (v.equals(mLoadMoreFooter)) {
				refresh();
			}
		}
    }

	public void performFilter(ItemFilterConstraint constraint) {
		performFilter(constraint, null);
	}

	public void performFilter(ItemFilterConstraint constraint, CustomFilter.FilterListener listener) {
		CustomFilter<DBItem, ItemFilterConstraint> filter = getListAdapter().getFilter();
		filter.filter(constraint, listener);
	}

}
