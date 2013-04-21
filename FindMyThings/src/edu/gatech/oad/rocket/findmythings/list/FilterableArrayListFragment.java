package edu.gatech.oad.rocket.findmythings.list;

import android.app.LoaderManager;

import java.util.List;

/**
 * A type-parametrized List Fragment that asynchronously loads content with a progress bar with filtering support.
 * User: zw
 * Date: 4/21/13
 * Time: 3:38 AM
 */
public abstract class FilterableArrayListFragment<T, U extends CustomFilter.Constraint<T>> extends ArrayListFragment<T> implements
		LoaderManager.LoaderCallbacks<List<T>>  {

	/**
	 * Create adapter to display items
	 *
	 * @return adapter
	 */
	protected abstract FilterableArrayListAdapter<T, U> onCreateAdapter();

	/**
	 * Get list adapter
	 *
	 * @return list adapter
	 */
	@SuppressWarnings("unchecked")
	public FilterableArrayListAdapter<T, U> getListAdapter() {
		return (FilterableArrayListAdapter<T, U>) super.getListAdapter();
	}

	/**
	 * Set list adapter to use on list view
	 *
	 * @param adapter Provide an adapter for the list view.
	 */
	public void setListAdapter(final FilterableArrayListAdapter<T, U> adapter) {
		super.setListAdapter(adapter);
	}
}
