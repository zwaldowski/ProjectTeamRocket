package edu.gatech.oad.rocket.findmythings.list;

import java.util.List;

/**
 * A filter that delegates result publishing to something like an Adapter.
 * User: zw
 * Date: 4/16/13
 * Time: 6:47 PM
 */
public abstract class CustomAdapterFilter<T> extends CustomFilter<T> {

	private final CustomFilterDelegate<T> mDelegate;

	public CustomAdapterFilter(CustomFilterDelegate<T> delegate) {
		super();
		mDelegate = delegate;
	}

	@Override @SuppressWarnings("unchecked")
	protected void publishResults(CustomFilterConstraint<T> constraint, FilterResults results)  {
		mDelegate.publishFilterResults((List<T>)results.values, results.count);
	}

}
