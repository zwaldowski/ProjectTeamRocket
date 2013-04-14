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
public abstract class ThrowableLoader<T> extends AsyncTaskLoader<T> {

	private Exception lastException;

	public ThrowableLoader(Context context) {
		super(context);
	}

	@Override
	public T loadInBackground() {
		lastException = null;
		try {
			return loadData();
		} catch (Exception e) {
			lastException = e;
			return null;
		}
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
