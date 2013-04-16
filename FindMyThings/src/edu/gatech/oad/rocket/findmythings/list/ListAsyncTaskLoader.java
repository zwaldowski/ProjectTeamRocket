package edu.gatech.oad.rocket.findmythings.list;

import android.content.Context;

import java.util.List;

/**
 * An asynchronous task-based loader specifically modified for lists.
 * User: zw
 * Date: 4/14/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ListAsyncTaskLoader<T> extends ThrowableAsyncTaskLoader<List<T>> {

	public ListAsyncTaskLoader(Context context) {
		super(context);
	}

	@Override
	public void deliverResult(List<T> data) {
		if (isReset()) {
			return;
		}

		mData = data;

		if (isStarted()) {
			// If the Loader is in a started state, deliver the results to the
			// client. The superclass method does this for us.
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mData != null) {
			// Deliver any previously loaded data immediately.
			deliverResult(mData);
		}

		if (takeContentChanged() || mData == null) {
			// When the observer detects a change, it should call onContentChanged()
			// on the Loader, which will cause the next call to takeContentChanged()
			// to return true. If this is ever the case (or if the current data is
			// null), we force a new load.
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {
		// The Loader is in a stopped state, so we should attempt to cancel the
		// current load (if there is one).
		cancelLoad();
	}

	@Override
	protected void onReset() {
		// Ensure the loader has been stopped.
		onStopLoading();
	}

	@Override
	public void onCanceled(List<T> data) {
		// Attempt to cancel the current asynchronous load.
		super.onCanceled(data);
	}



}
