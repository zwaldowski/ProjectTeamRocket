package edu.gatech.oad.rocket.findmythings;

import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import edu.gatech.oad.rocket.findmythings.list.*;
import edu.gatech.oad.rocket.findmythings.list.AlternatingArrayListAdapter;
import edu.gatech.oad.rocket.findmythings.list.ArrayListAdapter;
import edu.gatech.oad.rocket.findmythings.model.AppMember;
import edu.gatech.oad.rocket.findmythings.model.CollectionResponseAppMember;
import edu.gatech.oad.rocket.findmythings.service.EndpointUtils;
import edu.gatech.oad.rocket.findmythings.service.Fmthings;

import java.util.List;

/**
 * Fetches and lists users, i.e., from the admin interface
 * User: zw
 * Date: 4/21/13
 * Time: 3:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserListFragment extends ArrayListFragment<AppMember> {

	public static final String ARG_QUERY = "searchQuery";

	public static final int LOAD_LIMIT = 25;

	private String lastNextPageToken = null;

	private View mLoadMoreFooter;

	public UserListFragment() {
		super();
	}

	private String getSearchQuery() {
		return getArguments().getString(ARG_QUERY);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setEmptyText(R.string.no_users_found);
	}

	@Override
	protected int getErrorMessage(Exception exception) {
		return R.string.error_loading_users;
	}

	@Override
	public Loader<List<AppMember>> onCreateLoader(int id, Bundle args) {
		if (isForceRefresh(args)) lastNextPageToken = null;
		return new ListAsyncTaskLoader<AppMember>(getActivity()){

			@Override
			public List<AppMember> loadData() throws Exception {
				// limit, type, cursor
				Fmthings.Members.List query = EndpointUtils.getEndpoint().members().list();

				query.setLimit(LOAD_LIMIT);

				if (lastNextPageToken != null) query.setCursor(lastNextPageToken);

				String searchQuery = getSearchQuery();
				if (!TextUtils.isEmpty(searchQuery)) query.setEmail(searchQuery);

				CollectionResponseAppMember result = query.execute();
				lastNextPageToken = result.getNextPageToken();
				return result.getItems();
			}
		};
	}

	@Override
	protected ArrayListAdapter<AppMember> onCreateAdapter() {
		mLoadMoreFooter = getActivity().getLayoutInflater().inflate(R.layout.list_footer_button, null);
		TextView label = (TextView)mLoadMoreFooter.getRootView().findViewById(R.id.button_cell_title);
		label.setText(R.string.item_load_more);
		getListView().addFooterView(mLoadMoreFooter);

		return new AlternatingArrayListAdapter<AppMember>(getActivity());
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		if (l.getAdapter().getItemViewType(position) != AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
			// TODO: Edit user, view user, some shit like that
		} else {
			if (v.equals(mLoadMoreFooter)) {
				refresh();
			}
		}
	}

}
