package edu.gatech.oad.rocket.findmythings;


/**
 * FOR NOW THIS IS NOT BEEN USED -- RANDOM STUFF
 * 
 * 
 */




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

public class SearchActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	}


	//Addd SEARCH button
	//when category button, show buttons
	//when date, show textView
	//when status, show buttons
	

	public int onRadioButtonClicked(View view) {
		int x = (Integer) null;
		switch(view.getId()) {
			case R.id.categoryButton:
				x = 0;
				break;
			case R.id.dateButton:
				x = 1;
				break;
			case R.id.statusButton:
				x = 2;
				break;
		}
		return x;
	}

	/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.admin_ok:
			return createAdmin();
		case R.id.admin_cancel:
		case android.R.id.home:
			return goToParentActivity();
		}
		return super.onOptionsItemSelected(item);
	}
	*/
	
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
