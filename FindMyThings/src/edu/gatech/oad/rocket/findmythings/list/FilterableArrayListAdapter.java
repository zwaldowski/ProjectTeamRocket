package edu.gatech.oad.rocket.findmythings.list;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete BaseAdapter that is backed by a List of arbitrary
 * objects with custom, generic filtering logic.
 * * User: zw
 * Date: 4/21/13
 * Time: 3:40 AM
 */
public abstract class FilterableArrayListAdapter<T, U extends CustomFilter.Constraint<T>> extends ArrayListAdapter<T> {

	// A copy of the original mObjects array, initialized from and then used instead as soon as
	// the mFilter ArrayFilter is used. mObjects will then only contain the filtered values.
	private CustomArrayFilter mFilter;



	/**
	 * Constructor
	 *
	 * @param context The current context.
	 * @param textViewResourceId The resource ID for a layout file containing a TextView to use when
	 *                 instantiating views.
	 */
	public FilterableArrayListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	/**
	 * Constructor
	 *
	 * @param context The current context.
	 * @param resource The resource ID for a layout file containing a layout to use when
	 *                 instantiating views.
	 * @param textViewResourceId The id of the TextView within the layout resource to be populated
	 */
	public FilterableArrayListAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	/**
	 * {@inheritDoc}
	 */
	public CustomArrayFilter getFilter() {
		if (mFilter == null) {
			mFilter = new CustomArrayFilter();
		}
		return mFilter;
	}

	protected abstract boolean applyFilter(T object, U constraint);

	private class CustomArrayFilter extends CustomFilter<T, U> {
		@Override
		protected List<T> performFiltering(U constraint) {
			if (mOriginalValues == null) {
				synchronized (mLock) {
					mOriginalValues = new ArrayList<T>(mObjects);
				}
			}

			if (constraint == null || constraint.isEmpty()) {
				ArrayList<T> list;
				synchronized (mLock) {
					list = new ArrayList<T>(mOriginalValues);
				}
				return list;
			} else {
				ArrayList<T> values;
				synchronized (mLock) {
					values = new ArrayList<T>(mOriginalValues);
				}

				final ArrayList<T> newValues = new ArrayList<T>();

				for (final T value : values) {
					if (applyFilter(value, constraint)) {
						newValues.add(value);
					}
				}

				return newValues;
			}
		}

		@Override
		protected void publishResults(List<T> results, U constraint) {
			mObjects = results;
			if (results.size() > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}

}
