package edu.gatech.oad.rocket.findmythings.util;

import android.app.Activity;
import android.widget.Toast;

public final class ToastHelper {

	private ToastHelper(){}

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
