package edu.gatech.oad.rocket.findmythings.list;

import com.google.api.services.fmthings.model.DBItem;

/**
 * Created with IntelliJ IDEA.
 * User: zw
 * Date: 4/16/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemFilterConstraint implements CustomFilterConstraint<DBItem> {

	@Override
	public boolean isEmpty() {
		return true;
	}

}
