package edu.gatech.oad.rocket.findmythings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import edu.gatech.oad.rocket.findmythings.model.Item;
import edu.gatech.oad.rocket.findmythings.util.*;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * An activity representing a single Item detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link MainActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ItemDetailFragment}.
 *
 * @author TeamRocket
 */
public class ItemDetailActivity extends FragmentActivity {

	/**
	 * The class of Item displayed.
	 */
	private Item mItem;

	/**
	 * creates new window with correct layout
	 * @param Bundle savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extraInfo = getIntent().getExtras();
		if (extraInfo != null) {
			int value = extraInfo.getInt("id");
			mItem = MainActivity.currList.get(value);
		}

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			ItemDetailFragment fragment = new ItemDetailFragment();
			getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
		}
	}
	 
	/**
	 * deals with action to do once a key is pressed down
	 * @param int keyCode - key pressed
	 * @param KeyEvent event - event to do in case of pressed
	 * @return boolean true when done
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		//Tells Activity what to do when back key is pressed
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			super.onBackPressed();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * A read-only getter for the kinds of Item displayed in this view.
	 * @return An enumerated Type value.
	 */
	public Item getItem() {
		return mItem;
	}

	/**
	 * Method called when the location button is clicked
	 * Goes to MapsActivity
	 * @param locationButton
	 */
	public void toMap (View locationButton) {
		if(hasInternet() && ItemDetailFragment.mItem.getLoc()!=null && ItemDetailFragment.mItem.getLoc().length()>0) {
			int googlePlayAccess = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
			if(googlePlayAccess != ConnectionResult.SUCCESS)
			{
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(googlePlayAccess, this, 2340);
				if(dialog != null)
				{
					dialog.show();
				}
				else
				{
					new ErrorDialog("Something went wrong. Please make sure that you have the Play Store installed and that you are connected to the internet.").getDialog(this).show();
				}
			} else {
				startActivity(new Intent(getApplicationContext(), MapsActivity.class));
			}
		} else {
			if(ItemDetailFragment.mItem.getLoc()!=null && ItemDetailFragment.mItem.getLoc().length()>0)
				new ErrorDialog("Error: no active internet connection.").getDialog(this).show();
		}
	}
	
	/**
	 * Checks to see if the user has an active network connection 
	 * @return boolean
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
	
	/**
	 * Called to pop the detail window from the navigation stack
	 */
	@Override 
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
