package edu.gatech.oad.rocket.findmythings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import edu.gatech.oad.rocket.findmythings.control.*;

/**
 * CS 2340 - FindMyStuff Android App
 * activity that deals with the account window
 *
 * @author TeamRocket
 * */
public class AccountActivity extends Activity {
	
	/**
	 * References to the layout 
	 */
	private EditText mName, mEmail, mPhone, mAddy;

	/**
	 * creates new window with correct layout
	 * @param Bundle savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account);

		mName = (EditText) findViewById(R.id.personname);
		mEmail = (EditText) findViewById(R.id.emailview);
		mPhone = (EditText) findViewById(R.id.phoneview);
		mAddy = (EditText) findViewById(R.id.addressview);

		//Current Login manager
		LoginManager manage = LoginManager.getLoginManager();
		
		if(manage.isLoggedIn()) {
			// Display user info
			mEmail.setText(manage.getCurrentUser().getEmail());
			if(manage.getCurrentUser().getName()!=null)
				mName.setText(manage.getCurrentUser().getName());
			if(manage.getCurrentUser().getAddress()!=null)
				mAddy.setText(manage.getCurrentUser().getAddress());
			if(manage.getCurrentUser().getPhone()!=null)
				mPhone.setText(manage.getCurrentUser().getPhone().getNumber());
		}
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * creates the options menu 
	 * @param Menu menu
	 * @return boolean true when done
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_account, menu);
		return true;
	}
	
	/**
	 * deals with action when an options button is selected
	 * @param MenuItem item
	 * @return boolean  
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.toEdit:
			return toEdit();
		}
		return super.onOptionsItemSelected(item);
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
	 * creates new intent of AccountEditActivity - window to change account seetings
	 */
	public boolean toEdit() {
		Intent goToNextActivity = new Intent(this, AccountEditActivity.class);
		startActivity(goToNextActivity);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		return true;
	}
	
	/**
	 * Called to pop the login window from the navigation stack
	 */
	@Override 
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_down_modal);
    }

}
