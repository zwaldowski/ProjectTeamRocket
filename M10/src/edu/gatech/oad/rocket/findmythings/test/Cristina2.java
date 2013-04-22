package edu.gatech.oad.rocket.findmythings.test;

import edu.gatech.oad.rocket.findmythings.LoginActivity;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.control.LoginManager;
import edu.gatech.oad.rocket.findmythings.model.AppMember;
import edu.gatech.oad.rocket.findmythings.model.Member;
import edu.gatech.oad.rocket.findmythings.model.User;
import edu.gatech.oad.rocket.findmythings.service.EndpointUtils;
import edu.gatech.oad.rocket.findmythings.service.Fmthings;

/**
 * Test Case for LoginActivity
 * 
 * @author cristinachu
 */
public class Cristina2 extends ActivityInstrumentationTestCase2<LoginActivity> {

  public LoginActivity activity;
	public EditText email, password;
	public Button signIn;
	private boolean x;
	private LoginManager login = LoginManager.getLoginManager();
	
	/**
	 * constructor
	 */
	@SuppressWarnings("deprecation")
	public Cristina2() {
		super("andtest.threads.asynctask", LoginActivity.class);
	}
	
	/**
	 * setting up the activity
	 */
	protected void setUp() throws Exception {  
		super.setUp();  
		activity = getActivity();  
		
		//getting the EditTexts of the Login window
		email = (EditText)activity.findViewById(edu.gatech.oad.rocket.findmythings.R.id.email);
		password = (EditText)activity.findViewById(edu.gatech.oad.rocket.findmythings.R.id.password);
		signIn = (Button)activity.findViewById(edu.gatech.oad.rocket.findmythings.R.id.sign_in_button);
	}  

	/**
	 * actual testing of features of Login
	 */
	public void testLogin() {
		
		// e-mails and passwords that will be used for testing
		final String email1 = "Tyrion@Lannister";
		final String pass1 = "lion";
		final String name1 = "Tyrion";
		final String phone1 = "5555555555";
		
		/** Test Case 0 - checking for good user */
		
		
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						Editable emailField = email.getText();
						emailField.insert(email.getSelectionStart(), "a@a.com");
						Editable passwordField = password.getText();
						passwordField.insert(password.getSelectionStart(), "admin");
						boolean x = signIn.performClick();
						assertTrue(x);
					}
				}
		);
		
		
		/** Test Case 1 - checking for wrong user */
		activity.runOnUiThread(
	    		new Runnable() {
	    			public void run() {
						Editable emailField = email.getText();
						emailField.insert(email.getSelectionStart(), email1);
						Editable passwordField = password.getText();
						passwordField.insert(password.getSelectionStart(), pass1);
						boolean x = signIn.performClick();
						assertFalse(x); // x should be false because there is no user email1
	    			}
	    		}
		);
	    
	    
		//ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);

		//Activity LoginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		//assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		activity.finish();
	}
}










