package edu.gatech.oad.rocket.findmythings.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public final class ToastHelper {

	private ToastHelper(){}

	private static void show(final Activity activity, final int resId,
			final int duration) {
		if (activity == null)
			return;

		final Context context = activity.getApplication();
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(context, resId, duration).show();
			}
		});
	}

	private static void show(final Activity activity, final String message,
			final int duration) {
		if (activity == null)
			return;
		if (TextUtils.isEmpty(message))
			return;

		final Context context = activity.getApplication();
		activity.runOnUiThread(new Runnable() {

			public void run() {
				Toast.makeText(context, message, duration).show();
			}
		});
	}

	/**
	 * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
	 *
	 * @param activity
	 * @param resId
	 */
	public static void showLong(final Activity activity, int resId) {
		show(activity, resId, LENGTH_LONG);
	}

	/**
	 * Show message in {@link Toast} with {@link Toast#LENGTH_SHORT} duration
	 *
	 * @param activity
	 * @param resId
	 */
	public static void showShort(final Activity activity, final int resId) {
		show(activity, resId, LENGTH_SHORT);
	}

	/**
	 * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
	 *
	 * @param activity
	 * @param message
	 */
	public static void showLong(final Activity activity, final String message) {
		show(activity, message, LENGTH_LONG);
	}

	/**
	 * Show message in {@link Toast} with {@link Toast#LENGTH_SHORT} duration
	 *
	 * @param activity
	 * @param message
	 */
	public static void showShort(final Activity activity, final String message) {
		show(activity, message, LENGTH_SHORT);
	}

	/**
	 * Shows an error alert dialog with the given message.
	 * 
	 * @param activity
	 *            activity
	 * @param message
	 *            message to show or {@code null} for none
	 */
	public static void showError(final Activity activity, String message) {
		final String errorMessage = message == null ? "Error" : "[Error ] "
				+ message;
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
				.show();
			}
		});
	}

}
