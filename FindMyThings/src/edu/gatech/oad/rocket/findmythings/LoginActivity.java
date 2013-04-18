package edu.gatech.oad.rocket.findmythings;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import edu.gatech.oad.rocket.findmythings.model.AppMember;
import edu.gatech.oad.rocket.findmythings.model.MessageBean;
import edu.gatech.oad.rocket.findmythings.control.LoginManager;
import edu.gatech.oad.rocket.findmythings.service.EndpointUtils;
import edu.gatech.oad.rocket.findmythings.util.Messages;
import edu.gatech.oad.rocket.findmythings.util.validation.EmailValidator;
import edu.gatech.oad.rocket.findmythings.util.EnumHelper;
import edu.gatech.oad.rocket.findmythings.util.ToastHelper;

import java.io.IOException;

/**
 * CS 2340 - FindMyStuff Android App
 * Activity which displays a login screen to the user, offering registration as well.
 *
 * @author TeamRocket
 * */
public class LoginActivity extends Activity {

	public static final int LOGIN_REQUEST = 42;
	
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	/**
	 * creates window with correct layout
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login_window);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mEmailView = (EditText) findViewById(R.id.email);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptToLogin(textView);
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// Set up the login form.
		String extraEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		if (extraEmail == null) extraEmail = "";
		mEmailView.setText(extraEmail);
	}
	
	/**
	 * takes care of action when key is pressed down
	 * @param keyCode key that is pressed
	 * @param event - event that is to happen when the key is pressed
	 * @return boolean
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		//Tells Activity what to do when back key is pressed
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			setResult(RESULT_CANCELED);
	    	super.onBackPressed();
			return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}

	/**
	 * creates the menu with all the options
	 * @param menu
	 * @return boolean true when done
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login_window, menu);
		return true;
	}
	
	/**
	 * Handles menu actions
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
				setResult(RESULT_CANCELED);
				finish();
				return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Goes to register screen
	 */
	public void goToRegister(View registerButton) {
		Intent goToNextActivity = new Intent(this, RegisterActivity.class);
		String email = mEmailView.getText().toString();
		goToNextActivity.putExtra("email", email); // Passes email to RegisterActivity
		startActivity(goToNextActivity);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptToLogin(View loginButton) {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();


		boolean continues = true;
		View focusView = null;
		
		//Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			continues = false;
		} else if (email.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			continues = false;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			continues = false;
		} else if (!EmailValidator.getInstance().isValid(email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			continues = false;
		}
		
		if (continues) {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			
			// Checks for valid user name
			mAuthTask = new UserLoginTask();
			mAuthTask.execute(email, password);
		} else {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<String, Void, MessageBean> {
		@Override
		protected MessageBean doInBackground(String... params) {
			try {
				return EndpointUtils.getEndpoint().account().login(params[0], params[1]).execute();
			} catch (IOException e) {
				Log.e("LoginActivity", "couldn't log in", e);
				return null;
			}
		}

		/**
		 * deals with action when user either log is sucessfully or not
		 * @param output - response from the login API method
		 */
		@Override
		protected void onPostExecute(final MessageBean output) {
			mAuthTask = null;

			String token = null, email = null, failureMessage = null;
			if (output != null) {
				token = output.getToken();
				email = output.getEmail();
				failureMessage = output.getFailureReason();
			}
			Messages.Login failureType = EnumHelper.forTextString(Messages.Login.class, failureMessage);
			
			if (token != null && email != null) {
				LoginManager.getLoginManager().setCurrentEmailAndToken(email, token);
				new AsyncTask<Void, Void, AppMember>(){

					@Override
					protected AppMember doInBackground(Void... arg0) {
						try {
							return EndpointUtils.getEndpoint().account().get().execute();
						} catch (IOException e) {
							return null;
						}
						
					}
					
					@Override
					protected void onPostExecute(final AppMember output) {
						showProgress(false);
						LoginManager.getLoginManager().setCurrentUser(output);
						setResult(RESULT_OK);
						finish();
					}
					
				}.execute();
				return;
			} else if (failureType != null) {
				switch (failureType) {
				case NO_SUCH_USER:
					mEmailView.setError(getString(R.string.error_no_such_user));
					mEmailView.requestFocus();
					break;
				case BAD_PASSWORD:
					mPasswordView.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
					break;
				case ACCOUNT_LOCKED:
				case ACCT_DISABLE:
					mEmailView.setError(getString(R.string.error_account_locked));
					mEmailView.requestFocus();
					break;
				case MANY_ATTEMPT:
					mEmailView.setError(getString(R.string.error_many_attempts));
					mEmailView.requestFocus();
					break;
				case INVALID_DATA:
					ToastHelper.showError(LoginActivity.this, getString(R.string.error_invalid_data));
					break;
				}
			} else {
				ToastHelper.showError(LoginActivity.this, getString(R.string.error_no_response));
			}
			
			showProgress(false);
		}

		/**
		 * deals with action when task cancelled
		 */
		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
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
