package edu.gatech.oad.rocket.findmythings.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import edu.gatech.oad.rocket.findmythings.R;

/**
 * CS 2340 - FindMyStuff Android App
 * Extremely simple class for creating a quick error dialog 
 * @author TeamRocket
 */
public class ErrorDialog {
	private int messageID, positiveButtonID, negativeButtonID;

	/**
	 * An dialog builder with "OK" and "Cancel".
	 * @param messageID
	 */
	public ErrorDialog(int messageID) {
		this(messageID, R.string.dialog_positive_default, R.string.dialog_negative_default);
	}

	/**
	 * An dialog builder with custom buttons.
	 * @param messageID
	 * @param positiveMessageID
	 * @param negativeMessageID
	 */
	public ErrorDialog(int messageID, int positiveMessageID, int negativeMessageID) {
		this.messageID = messageID;
		this.positiveButtonID = positiveMessageID;
		this.negativeButtonID = negativeMessageID;
	}
	
	/**
	 * Returns an AlertDialog with the message given in the constructor
	 * @param context
	 * @return
	 */
	public AlertDialog.Builder getDialog(Context context) {
		AlertDialog.Builder noConnection = new AlertDialog.Builder(context);
		noConnection.setMessage(messageID);
		noConnection.setPositiveButton(positiveButtonID, new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int id) {
            	//close	
            }
		});
		
		return noConnection;
	}
	
	/**
	 * Returns dialog with custom on-click listener for the positive button
	 * @param context
	 * @param positive
	 * @return
	 */
	public AlertDialog.Builder getDialog(Context context, DialogInterface.OnClickListener positive) {
		AlertDialog.Builder noConnection = new AlertDialog.Builder(context);

		noConnection.setMessage(messageID);
		noConnection.setPositiveButton(positiveButtonID, positive);
		
		return noConnection;
	}
	
	/**
	 * returns dialog with custom on-click listener for both positive and negative buttons
	 * @param context
	 * @param positive
	 * @param negative
	 * @return AlertDialog.Builder
	 */
	public AlertDialog.Builder getDialog(Context context, DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
		AlertDialog.Builder noConnection = new AlertDialog.Builder(context);
		
		noConnection.setMessage(messageID);
		noConnection.setPositiveButton(positiveButtonID, positive);
		noConnection.setNegativeButton(negativeButtonID, negative);
		
		return noConnection;
	}

}
