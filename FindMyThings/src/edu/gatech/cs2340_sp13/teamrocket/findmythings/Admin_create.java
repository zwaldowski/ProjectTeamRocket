package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Admin_create extends Activity {
	
	/**
	 * reference to login
	 */
	private Login log = new Login();
	
	/**
	 * References
	 */
	private EditText mEmailView, mPasswordView;
	
	/**
	 * Hold email/pass for creating a new admin
	 */
	private String mEmail, mPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_create);
		
		mEmailView = (EditText)findViewById(R.id.admin_email);
		mPasswordView = (EditText)findViewById(R.id.admin_pass);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin_create, menu);
		return true;
	}
	
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
	
	/**
	 * Goes back to the main activity
	 */
	public boolean goToParentActivity() {
		Intent next = new Intent(Admin_create.this, AdminActivity.class);
		finish();
		startActivity(next);
		return true;
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
				} else if(Login.data.contains(new Admin(mEmail,""))) {
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
					Intent next = new Intent(Admin_create.this, AdminActivity.class);
					log.createAdmin(mEmail, mPassword);
					finish();
					startActivity(next);
					return true;
				}
			return false;
		
		
		
	}
	

}
