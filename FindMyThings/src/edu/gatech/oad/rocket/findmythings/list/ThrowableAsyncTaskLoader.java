package edu.gatech.oad.rocket.findmythings.list;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * An asynchronous provider - for use with a list, etc. - that supports catching exceptions.
 * User: zw
 * Date: 4/14/13
 * Time: 1:41 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ThrowableAsyncTaskLoader<T> extends AsyncTaskLoader<T> {

	private Exception lastException;
	protected T mData;

	public ThrowableAsyncTaskLoader(Context context) {
		super(context);
	}

	@Override
	public T loadInBackground() {
		mData = null;
		lastException = null;
		try {
			mData = loadData();
		} catch (Exception e) {
			lastException = e;
		}
		return mData;
	}

	/**
	 * @return exception
	 */
	public Exception getLastException() {
		return lastException;
	}

	/**
	 * Clear the stored exception and return it
	 *
	 * @return exception
	 */
	public Exception popLastException() {
		final Exception throwable = lastException;
		lastException = null;
		return throwable;
	}

	public abstract T loadData() throws Exception;
}
