package edu.gatech.oad.rocket.findmythings.model;

/**
 * CS 2340 - FindMyStuff Android App
 * An enumerated type representing types of items
 *
 * @author TeamRocket
 */
public enum Type {
	LOST, FOUND, DONATION, REQUEST;

	/**
	 * A key suitable for putting a Type into a {@link android.os.Bundle}, {@link android.content.Intent}, etc.
	 */
	public static final String ID = "item_type";
}