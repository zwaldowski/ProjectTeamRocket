package edu.gatech.oad.rocket.findmythings.server.util;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: zw
 * Date: 4/8/13
 * Time: 2:36 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract interface Searchable {

	abstract String getSearchableContent();

	abstract Set<String> getSearchTokens();

}
