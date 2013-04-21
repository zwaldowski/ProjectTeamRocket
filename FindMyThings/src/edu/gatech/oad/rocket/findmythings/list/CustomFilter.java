/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.gatech.oad.rocket.findmythings.list;

import android.os.*;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

/**
 * In Android parlance, a filter constrains data by a filtering pattern.
 *
 * This class extends the Android filter paradigm with custom, parametrized constraints.
 * <p>Filtering operations are performed asynchronously. When these methods are called, a filtering request
 * is posted in a request queue and processed later. Any call to one of these methods will cancel any previous
 * non-executed filtering request.</p>
 *
 * @see android.widget.Filter
 * @see android.widget.Filterable
 */
public abstract class CustomFilter<T, U extends CustomFilter.Constraint<T>> {
    private static final String LOG_TAG = "CustomFilter";

    private static final String THREAD_NAME = "CustomFilter";
    private static final int FILTER_TOKEN = 0xD0D0F00D;
    private static final int FINISH_TOKEN = 0xDEADBEEF;

    private RequestHandler<T, U> mThreadHandler;
    private ResultsHandler<T, U> mResultsHandler;

    private final Object mLock = new Object();

	/**
	 * An object describing how to filter the given parametrized object.
	 */
	public static abstract class Constraint<T> implements Parcelable {

		public abstract boolean isEmpty();

	}

    /**
     * <p>Creates a new asynchronous filter.</p>
     */
    public CustomFilter() {
        mResultsHandler = new ResultsHandler<T, U>(this);
    }

    /**
     * <p>Starts an asynchronous filtering operation. Calling this method
     * cancels all previous non-executed filtering requests and posts a new
     * filtering request that will be executed later.</p>
     *
     * @param constraint the constraint used to filter the data
     */
    public final void filter(U constraint) {
        filter(constraint, null);
    }

    /**
     * <p>Starts an asynchronous filtering operation. Calling this method
     * cancels all previous non-executed filtering requests and posts a new
     * filtering request that will be executed later.</p>
     *
     * <p>Upon completion, the listener is notified.</p>
     *
     * @param constraint the constraint used to filter the data
     * @param listener a listener notified upon completion of the operation
     */
    public final void filter(U constraint, FilterListener listener) {
        synchronized (mLock) {
            if (mThreadHandler == null) {
                HandlerThread thread = new HandlerThread(
                        THREAD_NAME, android.os.Process.THREAD_PRIORITY_BACKGROUND);
                thread.start();
                mThreadHandler = new RequestHandler<T, U>(this, thread.getLooper());
            }

            final long delay = 0;

            Message message = mThreadHandler.obtainMessage(FILTER_TOKEN);
            message.obj = new RequestArguments<T, U>(constraint, listener);

            mThreadHandler.removeMessages(FILTER_TOKEN);
            mThreadHandler.removeMessages(FINISH_TOKEN);
            mThreadHandler.sendMessageDelayed(message, delay);
        }
    }

    /**
     * <p>Invoked in a worker thread to filter the data according to the
     * constraint. Subclasses must implement this method to perform the
     * filtering operation. Results computed by the filtering operation
     * must be returned as a {@link List} that
     * will then be published in the UI thread through publishResults()</p>
     *
     * <p><strong>Contract:</strong> When the constraint is null, the original
     * data must be restored.</p>
     *
     * @param constraint the constraint used to filter the data
     * @return the results of the filtering operation
     */
    protected abstract List<T> performFiltering(U constraint);

    /**
     * <p>Invoked in the UI thread to publish the filtering results in the
     * user interface. Subclasses must implement this method to display the
     * results computed in {@link #performFiltering}.</p>
     *
	 * @param results the results of the filtering operation
	 * @param constraint the constraint used to filter the data
	 */
    protected abstract void publishResults(List<T> results, U constraint);

    /**
     * <p>Listener used to receive a notification upon completion of a filtering
     * operation.</p>
     */
    public static interface FilterListener {
        /**
         * <p>Notifies the end of a filtering operation.</p>
         *
         * @param count the number of values computed by the filter
         */
        public void onFilterComplete(int count);
    }

    /**
     * <p>Worker thread handler. When a new filtering request is posted from
     * {@link android.widget.Filter#filter(CharSequence, android.widget.Filter.FilterListener)},
     * it is sent to this handler.</p>
     */
    private static class RequestHandler<T, U extends CustomFilter.Constraint<T>> extends Handler {

	WeakReference<CustomFilter<T, U>> mFilter;

		RequestHandler(CustomFilter<T, U> filter, Looper looper) {
			super(looper);
            mFilter = new WeakReference<CustomFilter<T, U>>(filter);
        }

        /**
         * <p>Handles filtering requests by calling
         * {@link CustomFilter#performFiltering} and then sending a message
         * with the results to the results handler.</p>
         *
         * @param msg the filtering request
         */
		@SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
			CustomFilter<T, U> filt = mFilter.get();
			if (filt == null) return;

            int what = msg.what;
            Message message;
            switch (what) {
                case FILTER_TOKEN:
                    RequestArguments<T, U> args = (RequestArguments<T, U>) msg.obj;
                    try {
                        args.results = filt.performFiltering(args.constraint);
                    } catch (Exception e) {
                        args.results = Collections.emptyList();
                        Log.w(LOG_TAG, "An exception occurred during performFiltering()!", e);
                    } finally {
                        message = filt.mResultsHandler.obtainMessage(what);
                        message.obj = args;
                        message.sendToTarget();
                    }

                    synchronized (filt.mLock) {
                        if (filt.mThreadHandler != null) {
                            Message finishMessage = filt.mThreadHandler.obtainMessage(FINISH_TOKEN);
                            filt.mThreadHandler.sendMessageDelayed(finishMessage, 3000);
                        }
                    }
                    break;
                case FINISH_TOKEN:
                    synchronized (filt.mLock) {
                        if (filt.mThreadHandler != null) {
				filt.mThreadHandler.getLooper().quit();
                            filt.mThreadHandler = null;
                        }
                    }
                    break;
            }
        }
    }

    /**
     * <p>Handles the results of a filtering operation. The results are
     * handled in the UI thread.</p>
     */
    private static class ResultsHandler<T, U extends CustomFilter.Constraint<T>> extends Handler {

	 WeakReference<CustomFilter<T, U>> mFilter;

	 ResultsHandler(CustomFilter<T, U> filter) {
             mFilter = new WeakReference<CustomFilter<T, U>>(filter);
         }

        /**
         * <p>Messages received from the request handler are processed in the
         * UI thread. The processing involves calling
         * publishResults(T, FilterResults)
         * to post the results back in the UI and then notifying the listener,
         * if any.</p>
         *
         * @param msg the filtering results
         */
        @Override @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            RequestArguments<T, U> args = (RequestArguments<T, U>) msg.obj;
            CustomFilter<T, U> filt = mFilter.get();

            if (filt != null) {
		filt.publishResults(args.results, args.constraint);
                if (args.listener != null) {
                    int count = args.results != null ? args.results.size() : -1;
                    args.listener.onFilterComplete(count);
                }
            }


        }
    }

    /**
     * <p>Holds the arguments of a filtering request as well as the results
     * of the request.</p>
     */
    private static class RequestArguments<V, W> {

		public RequestArguments(W constraint, FilterListener listener) {
			this.constraint = constraint;
			this.listener = listener;
		}

        /**
         * <p>The constraint used to filter the data.</p>
         */
		W constraint;

        /**
         * <p>The listener to notify upon completion. Can be null.</p>
         */
        FilterListener listener;

        /**
         * <p>The results of the filtering operation.</p>
         */
        List<V> results;
    }
}
