package edu.gatech.oad.rocket.findmythings.model;

/**
 * CS 2340 - FindMyStuff Android App
 * An enumerated type representing categories of items
 *
 * @author TeamRocket
 * */
public enum Category {
	HEIR, KEEPSAKE, MISC;

	/**
	 * A key suitable for putting a Category into a {@link android.os.Bundle}, {@link android.content.Intent}, etc.
	 */
	public static final String ID = "item_category";

}
