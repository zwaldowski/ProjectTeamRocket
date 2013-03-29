package edu.gatech.oad.rocket.findmythings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * An activity for the Search Window.
 * 
 * @author TeamRocket
 */
public class SearchActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	}


	public void onRadioButtonClicked(View view) {
		switch(view.getId()) {
			case R.id.categoryButton:
					//do something
				break;
			case R.id.dateButton:
					//do something
				break;
			case R.id.statusButton:
					//do something
				break;
		}
		
	}
	
	//might be useful
	/**
	 * Checks to see if the user has an active network connection 
	 * @return
	 */
	public boolean hasInternet() {
		boolean hasWifi = false;
		boolean hasMobile = false;
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if ( ni != null ) {
		    if (ni.getType() == ConnectivityManager.TYPE_WIFI)
		        if (ni.isConnectedOrConnecting())
		        	hasWifi = true;
		    if (ni.getType() == ConnectivityManager.TYPE_MOBILE)
		        if (ni.isConnectedOrConnecting())
		        	hasMobile = true;
		}
		if(hasWifi || hasMobile)
			return true;
		return false;
		
	}
}
