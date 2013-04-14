package edu.gatech.oad.rocket.findmythings.list;

/**
 * An item displayed in {@link edu.gatech.oad.rocket.findmythings.list.AlternatingTwoLineListAdapter}.
 * User: zw
 * Date: 4/14/13
 * Time: 2:46 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TwoLineListProvider {

	public String getTitle();
	public String getDescription();

}
