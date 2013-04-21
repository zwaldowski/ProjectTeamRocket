package edu.gatech.oad.rocket.findmythings.list;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.*;
import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.util.ToastHelper;

import java.util.List;

/**
 * A type-parametrized List Fragment that asynchronously loads content with a progress bar.
 * User: zw
 * Date: 4/13/13
 * Time: 7:04 PM
 */
public abstract class ArrayListFragment<T> extends ListFragment implements
		LoaderManager.LoaderCallbacks<List<T>> {

	private static final String FORCE_REFRESH = "forceRefresh";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		setListAdapter(onCreateAdapter());
		setListShown(false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu optionsMenu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_arraylist, optionsMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (isUnavailable()) return false;

		switch (item.getItemId()) {
			case R.id.refresh:
				forceRefresh();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Force a refresh of the items displayed ignoring any cached items
	 */
	protected void forceRefresh() {
		Bundle bundle = new Bundle();
		bundle.putBoolean(FORCE_REFRESH, true);
		refresh(bundle);
	}

	/**
	 * @param args
	 *            bundle passed to the loader by the LoaderManager
	 * @return true if the bundle indicates a requested forced refresh of the
	 *         items
	 */
	protected static boolean isForceRefresh(Bundle args) {
		return args != null && args.getBoolean(FORCE_REFRESH, false);
	}

	private void refresh(final Bundle args) {
		if (isUnavailable()) return;

		setListShown(false);

		if (isForceRefresh(args)) {
			getListAdapter().clear();
		}

		getLoaderManager().restartLoader(0, args, this);
	}

	/**
	 * Refresh the fragment's list
	 */
	public void refresh() {
		refresh(null);
	}

	public void onLoadFinished(Loader<List<T>> loader, List<T> items) {
		Exception exception = getException(loader);
		if (exception != null) {
			showError(getErrorMessage(exception));
			setListShown(true);
			return;
		}



		if (items != null) {
			getListAdapter().addAll(items);
		}
		setListShown(true);
	}

	/**
	 * Is this fragment still part of an activity and usable from the UI-thread?
	 *
	 * @return true if usable on the UI-thread, false otherwise
	 */
	protected boolean isUnavailable() {
		return getActivity() == null;
	}

	/**
	 * Show exception in a Toast
	 *
	 * @param message An error string expressed from a resource value
	 */
	protected void showError(final int message) {
		ToastHelper.showLong(getActivity(), message);
	}

	/**
	 * Get exception from loader if it provides one by being a
	 * {@link ThrowableAsyncTaskLoader}
	 *
	 * @param loader The loader being used and to check for an exception using
	 * @return exception or null if none provided
	 */
	protected Exception getException(final Loader<List<T>> loader) {
		if (loader instanceof ThrowableAsyncTaskLoader)
			return ((ThrowableAsyncTaskLoader<List<T>>) loader).popLastException();
		else
			return null;
	}

	/**
	 * Set empty text on list fragment
	 *
	 * @param resId An Android resource ID to set the empty text string using
	 */
	protected void setEmptyText(final int resId) {
		setEmptyText(getString(resId));
	}

	/**
	 * Get error message to display for exception
	 *
	 * @param exception The exception to process
	 * @return string resource id
	 */
	protected abstract int getErrorMessage(Exception exception);

	/**
	 * Create adapter to display items
	 *
	 * @return adapter
	 */
	protected abstract ArrayListAdapter<T> onCreateAdapter();

	/**
	 * Get list adapter
	 *
	 * @return list adapter
	 */
	@SuppressWarnings("unchecked")
	public ArrayListAdapter<T> getListAdapter() {
		return (ArrayListAdapter<T>)super.getListAdapter();
	}

	/**
	 * Set list adapter to use on list view
	 *
	 * @param adapter Provide an adapter for the list view.
	 */
	public void setListAdapter(final ArrayListAdapter<T> adapter) {
		super.setListAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<List<T>> loader) {}

}
