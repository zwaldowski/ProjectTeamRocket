package edu.gatech.oad.rocket.findmythings;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import edu.gatech.oad.rocket.findmythings.control.*;
import edu.gatech.oad.rocket.findmythings.model.Admin;

/**
 * CS 2340 - FindMyStuff Android App
 * Activity that deals with creating a new administrator (Admin Member)
 *
 * @author TeamRocket
 * */
public class AdminCreateActivity extends Activity {


	/**
	 * References
	 */
	private EditText mEmailView, mPasswordView;

	/**
	 * Hold email/pass for creating a new admin
	 */
	private String mEmail, mPassword;

	/**
	 * creates the new window with correct layout
	 * @param Bundle savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_create);

		mEmailView = (EditText)findViewById(R.id.admin_email);
		mPasswordView = (EditText)findViewById(R.id.admin_pass);
	}

	/**
	 * creates the menu with all the items
	 * @param Menu menu
	 * @return boolean true when done
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin_create, menu);
		return true;
	}

	/**
	 * deals with action when an option from the options menu is selected (ok or cancel)
	 * @param MenuItem item
	 * @return boolean 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.admin_ok:
			return createAdmin();
		case R.id.admin_cancel:
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Goes to the activity to create a new admin account
	 * @return true
	 */
	public boolean createAdmin() {
		View focusView = null;
		boolean cancel = false;

		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		} else if (false) {
			// Login.data.contains(new Admin(mEmail,""))
			// TODO: Replace with backend error
			mEmailView .setError("Email has already been registered.");
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		}
		else {
			// Login.data.add(new Admin(mEmail, mPassword));
			finish();
			return true;
		}
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
