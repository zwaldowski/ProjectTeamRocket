package edu.gatech.oad.rocket.findmythings;

/**
 * An enumerated type representing
 * @author zwaldowski
 *
 */
public enum Type {
	LOST, FOUND, DONATION, REQUEST;

	/**
	 * A key suitable for putting a Type into a {@link android.os.Bundle}, {@link android.content.Intent}, etc.
	 */
	public static final String ID = "item_type";
}