package edu.gatech.oad.rocket.findmythings.list;

import com.google.api.services.fmthings.model.DBItem;

/**
 * A custom filter used for
 * User: zw
 * Date: 4/16/13
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemFilter extends CustomAdapterFilter<DBItem> {

	public ItemFilter(CustomFilterDelegate<DBItem> delegate) {
		super(delegate);
	}

	@Override
	protected FilterResults performFiltering(CustomFilterConstraint<DBItem> constraint) {
		// TODO
		return null;
	}

	public class Constraint implements CustomFilterConstraint<DBItem> {
		// TODO
	}

}
