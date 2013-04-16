package edu.gatech.oad.rocket.findmythings.test;

import edu.gatech.oad.rocket.findmythings.LoginActivity;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.control.Login;
import edu.gatech.oad.rocket.findmythings.model.Member;
import edu.gatech.oad.rocket.findmythings.model.User;

/**
 * Test Case for LoginActivity
 * 
 * @author cristinachu
 */
public class Cristina extends ActivityInstrumentationTestCase2<LoginActivity> {

  public LoginActivity activity;
	public Login login = new Login();
	public EditText email, password;
	public Button signIn;
	private boolean x;
	
	/**
	 * constructor
	 */
	@SuppressWarnings("deprecation")
	public Cristina() {
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
		
		// emails and passwords that will be used for testing
		final String email1 = "Tyrion@Lannister";
		final String pass1 = "lion";
		final String email2 = "Daenerys@Targeryen";
		final String pass2 = "dragon";
		final String email3 = "Jon@Snow";
		final String pass3 = "crow";
		final String email4 = "Ned@Stark";
		final String pass4 = "winter";
		
		
		/** Test Case 0 - checking that registering a member is working*/
		Member m1 = new User(email1, pass1);
		login.register(m1);
		assertTrue(login.verifyUser(m1));
		
		/** Test Case 1 - checking for wrong user */
	    activity.runOnUiThread(
	    		new Runnable() {
	    			public void run() {
	    				email.setText(email2);
	    				password.setText(pass2);
	    				x = signIn.performClick();
	    			}
	    		});
		
	    // x should be false because there is no user email2
	    assertFalse(x);
	    
		/** Test Case 2 - checking for good user */
	    Member m3 = new User(email3, pass3);
	    login.register(m3);
	    
	    activity.runOnUiThread(
	    		new Runnable() {
	    			public void run() {
	    				email.setText(email3);
	    				password.setText(pass3);
	    				x = signIn.performClick();
	    			}
	    		});
	    
	    // x should be true because email3 just got registered.
	    assertTrue(x);
	    
	    
	    
	    
	    
		//ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);

		//Activity LoginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		//assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		activity.finish();
	}
}










