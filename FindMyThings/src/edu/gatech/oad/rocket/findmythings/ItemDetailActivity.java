package edu.gatech.oad.rocket.findmythings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import edu.gatech.oad.rocket.findmythings.model.DBItem;
import edu.gatech.oad.rocket.findmythings.util.ErrorDialog;

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

	public static final String ITEM_EXTRA = "item";

	private ItemDetailFragment mFrag;

	/**
	 * creates new window with correct layout
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			mFrag = new ItemDetailFragment();
			mFrag.setArguments(getIntent().getExtras());
			getFragmentManager().beginTransaction().add(R.id.item_detail_container, mFrag).commit();
		}
	}
	 
	/**
	 * deals with action to do once a key is pressed down
	 * @param keyCode - key pressed
	 * @param event - event to do in case of pressed
	 * @return true when done
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
	 * reacts to the action bar options that are available for the user
	 * @param item menu selected
	 * @return boolean
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Method called when the location button is clicked
	 * Goes to MapsActivity
	 * @param locationButton
	 */
	public void toMap (View locationButton) {
		if (hasInternet()) {
			DBItem mItemNew = mFrag.getItem();
			String loc = mItemNew.getLocation();
			if (loc != null && loc.length() > 0) {
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
						new ErrorDialog(R.string.item_detail_playstore_err).getDialog(this).show();
					}
				} else {
					startActivity(new Intent(this, MapsActivity.class).putExtra(MapsActivity.LOCATION_EXTRA, loc));
				}
			}
		} else {
			new ErrorDialog(R.string.item_detail_inet_err).getDialog(this).show();
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
		return hasWifi || hasMobile;
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
