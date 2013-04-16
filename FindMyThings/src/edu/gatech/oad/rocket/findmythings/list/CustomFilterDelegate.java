package edu.gatech.oad.rocket.findmythings.list;

import java.util.List;

/**
 * A class that responds to an Adapter-like filter result publishing.
 * User: zw
 * Date: 4/16/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CustomFilterDelegate<T> {

	void publishFilterResults(List<T> objects, int count);

}
