package edu.gatech.oad.rocket.findmythings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import edu.gatech.oad.rocket.findmythings.control.*;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * @author TeamRocket
 * */
public class AccountEditActivity extends Activity {

	/**
	 * References to the layout 
	 */
	private EditText mName, mEmail, mPhone, mAddy;
	
	/**
	 * Reference to current LoginManagers
	 */
	private LoginManager manage = LoginManager.getLoginManager();
	
	/**
	 * creates new window with correct layout
	 * @param Bundle savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account_edit);
		
		mName = (EditText) findViewById(R.id.personnameedit);
		mEmail = (EditText) findViewById(R.id.emailedit);
		mPhone = (EditText) findViewById(R.id.phoneedit);
		mAddy = (EditText) findViewById(R.id.addressedit);

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
		getMenuInflater().inflate(R.menu.my_account_edit, menu);
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
        	Intent i = new Intent(getApplicationContext(), MainActivity.class);
			finish();
			startActivity(i);
			return true;
		case R.id.edit_ok:
			manage.getCurrentUser().setName(TextUtils.isEmpty(mName.getText().toString())? "":mName.getText().toString());
			manage.getCurrentUser().setAddress(TextUtils.isEmpty(mAddy.getText().toString())? "":mAddy.getText().toString());
			if(manage.getCurrentUser().getPhone()!=null)
				manage.getCurrentUser().getPhone().setNumber(TextUtils.isEmpty(mPhone.getText().toString())? "":mPhone.getText().toString());
			return toAccount(true);
		case R.id.edit_cancel:
			return toAccount(false);
		}
		return super.onOptionsItemSelected(item);
	}

	
	/**
	 * Called to pop the login window from the navigation stack
	 */
	@Override 
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
	
	/**
	 * Returns to the account window either as a result of saving or hitting back
	 * @param saved true to push account, false to pop
	 * @return true, always true. Why? Because.
	 */
	private boolean toAccount(boolean saved) {
		Intent back = new Intent(getApplicationContext(),AccountActivity.class);
		finish();
		startActivity(back);
	    if (saved) overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	    return true;
	}

}
