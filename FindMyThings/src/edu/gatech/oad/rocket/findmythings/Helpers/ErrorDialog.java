package edu.gatech.oad.rocket.findmythings.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Extremely simple class for creating a quick error dialog 
 * @author TeamRocket
 *
 */
public class ErrorDialog {
	private String message, buttonpos, buttonneg;
		
	/**
	 * 
	 * @param message 
	 */
	public ErrorDialog(String message) {
		this.message = message;
		buttonpos = "Ok";
		buttonneg = "Cancel";
	}
	
	/**
	 * 
	 * @param message
	 * @param pos
	 * @param neg
	 */
	public ErrorDialog(String message, String pos, String neg) {
		this.message = message;
		buttonpos = pos;
		buttonneg = neg;
	}
	/**
	 * Returns an AlertDialog with the message given in the constructor
	 * @param context
	 * @return
	 */
	public AlertDialog.Builder getDialog(Context context) {
		AlertDialog.Builder noConnection = new AlertDialog.Builder(context);
		noConnection.setMessage(message);
		noConnection.setPositiveButton(buttonpos, new DialogInterface.OnClickListener() {
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
	public AlertDialog.Builder getDialog(Context context, DialogInterface.OnClickListener pos) {
		AlertDialog.Builder noConnection = new AlertDialog.Builder(context);
		
		noConnection.setMessage(message);
		noConnection.setPositiveButton(buttonpos,pos);
		
		return noConnection;
	}
	/**
	 * 
	 * @param context
	 * @param pos 
	 * @param neg
	 * @return
	 */
	public AlertDialog.Builder getDialog(Context context, DialogInterface.OnClickListener pos, DialogInterface.OnClickListener neg) {
		AlertDialog.Builder noConnection = new AlertDialog.Builder(context);
		
		noConnection.setMessage(message);
		noConnection.setPositiveButton(buttonpos,pos);
		noConnection.setNegativeButton(buttonneg,neg);
		
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
