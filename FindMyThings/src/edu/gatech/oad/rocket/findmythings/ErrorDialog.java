package edu.gatech.oad.rocket.findmythings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Extremely simple class for creating a quick error dialog 
 * @author TeamRocket
 *
 */
public class ErrorDialog {
	private String message;
		
	/**
	 * 
	 * @param message 
	 */
	public ErrorDialog(String message) {
		this.message = message;
	}
	/**
	 * Returns an AlertDialog with the message given in the constructor
	 * @param context
	 * @return
	 */
	public AlertDialog.Builder getDialog(Context context) {
		AlertDialog.Builder noConnection = new AlertDialog.Builder(context);
		noConnection.setMessage(message);
		noConnection.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int id) {
            	//close	
            }
		});
		
		return noConnection;
	}
	/**
	 * Returns dialog with custom onclicklistener for OK button
	 * @param context
	 * @param temp
	 * @return
	 */
	public AlertDialog.Builder getDialog(Context context, DialogInterface.OnClickListener temp) {
		AlertDialog.Builder noConnection = new AlertDialog.Builder(context);
		noConnection.setMessage(message);
		noConnection.setPositiveButton("Ok",temp);
		
		return noConnection;
	}
	/**
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	

}
