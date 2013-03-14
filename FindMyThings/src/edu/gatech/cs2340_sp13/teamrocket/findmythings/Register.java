package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.text.TextUtils;

public class Register extends Activity {

	/**
	 * The Login manager backing this Registration window.
	 */
	private Login log = new Login();
	
	/**
	 * Form values.
	 */
	private String mEmail, mPassword, mCon, mPhone, mName, mAddress;
	
	/**
	 * Copied email address from the login window.
	 */
	public static String rEmail = "";
	
	/**
	 * A prefilled Member to register potentially.
	 */
	private Member toreg = null;
	
	/**
	 * UI references.
	 */
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mPhoneView;
	private EditText mAddressView;
	private EditText mNameView;
	private EditText mConfirmView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		// Hide the Up button in the action bar.
		setupActionBar();
		
		// Gets mEmail from LoginWindow
		Intent i = getIntent();
		String s = i.getExtras().getString("email");
		
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(s);
		rEmail = s; 
				
		mPasswordView = (EditText) findViewById(R.id.pass);
		mPhoneView = (EditText) findViewById(R.id.phone);
		mAddressView = (EditText) findViewById(R.id.address);
		mNameView = (EditText) findViewById(R.id.lookingfor);
		mConfirmView = (EditText) findViewById(R.id.confirmpass);
	}
	
	/**
	 * Animation helper. Jumps back to the login screen.
	 * @return true, always true. Why? Because.
	 */
	private boolean backToLogin() {
		Intent goToNextActivity = new Intent(getApplicationContext(), LoginWindow.class);
    	goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		finish();
		startActivity(goToNextActivity);
	    overridePendingTransition(R.anim.hold, R.anim.slide_down_modal);
	    return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		//Tells Activity what to do when back key is pressed
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return backToLogin();
	    }

	    return super.onKeyDown(keyCode, event);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
	}
	/**
	 * Register new user and return to login screen 
	 * or just move on to the main screen with the newly created user
	 * already logged in. 
	 */
	private void register() {
			
		View focusView = null;
		boolean cancel = false;
		
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mPhone = mPhoneView.getText().toString();
		mCon = mConfirmView.getText().toString();
		mName = mNameView.getText().toString();
		mAddress = mAddressView.getText().toString();
		
			//Copied and pasted from LoginWindow
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
		} else if(log.data.contains(new User(mEmail,""))) {
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
			toreg = new User(mEmail,mPassword,mPhone);
				if(mName!=null)
					toreg.setName(mName);
				if(mAddress!=null)
					toreg.setAddress(mAddress);
			
				if(mPassword.equals(mCon)) { 
					//User is registered, goes back to login screen.
					log.register(toreg);
					
					// Saves email so it can be passed to LoginWindow
					rEmail = mEmail;
					
					Intent goToNextActivity = new Intent(getApplicationContext(), LoginWindow.class);
			    	goToNextActivity.addFlags(
			                Intent.FLAG_ACTIVITY_CLEAR_TOP |
			                Intent.FLAG_ACTIVITY_NEW_TASK);
					finish();
					startActivity(goToNextActivity);
				    overridePendingTransition(R.anim.hold, R.anim.slide_down_modal);
					
					
				}
				else {
					mPasswordView
					.setError("Passwords do not match.");
					mPasswordView.requestFocus();
				}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	 	case R.id.register_ok:
	 		register();
	 		return true;
	 	case R.id.register_cancel:
		case android.R.id.home:
			return backToLogin();
		}
		return super.onOptionsItemSelected(item);
	}

}
