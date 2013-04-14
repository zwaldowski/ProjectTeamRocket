package edu.gatech.oad.rocket.findmythings.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utilities for working with model objects.
 * User: zw
 * Date: 4/14/13
 * Time: 3:58 AM
 * To change this template use File | Settings | File Templates.
 */
public final class ModelHelper {

	private ModelHelper() {}

	private static final class ModelHelperDateSingleton {
		public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
	}

	public static String getDateString(Date date) {
		return ModelHelperDateSingleton.FORMAT.format(date);
	}

}
