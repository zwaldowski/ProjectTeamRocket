package edu.gatech.oad.rocket.findmythings.util;

import android.content.Context;

/**
 * CS 2340 - FindMyStuff Android App
 * class that helps with enumerated types
 *
 * @author TeamRocket
 * */
public final class EnumHelper {

	public static interface StringBasedEnum {

		public abstract String getText();

	}

	private EnumHelper() {}
	
	public static <T extends Enum<?> & StringBasedEnum> T forTextString(Class<T> clazz, String text) {
		if (text == null || text.length() == 0) return null;
		for (T enumt : clazz.getEnumConstants()) {
			if (enumt.getText().equals(text)) {
				return enumt;
			}
		}
		return null;
	}
	
	/**
	 * Simply unpacks an enumerated type for a given integer.
	 * @param value An enumerated type expressed as an int
	 * @param aClass The enumerated type in question
	 * @return The corresponding enumerated type for the given integer
	 */
	public static <T extends Enum<?>> T forInt(int value, Class<T> aClass) {
		return aClass.getEnumConstants()[value];
	}
	
	/**
	 * Simply unpacks an enumerated for a given integer-containing string.
	 * @param value An enumerated type expressed as an int, inside a string
	 * @param aClass The enumerated type in question
	 * @return The corresponding enumerated type for the given integer string
	 */
	public static <T extends Enum<?>> T forIntString(String value, Class<T> aClass) {
		int intValue = Integer.parseInt(value);
		return aClass.getEnumConstants()[intValue];
	}
	
	/**
	 * @param context
	 * @param rID
	 * @param value
	 * @return
	 */
	public static <T extends Enum<?>> String localizedFromArray(Context context, int rID, T value) {
		return context.getResources().getStringArray(rID)[value.ordinal()];
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static <T extends Enum<?>> String toIntString(T value) {
		return ((Integer)value.ordinal()).toString();
	}

}
