package edu.gatech.oad.rocket.findmythings.test;

import edu.gatech.oad.rocket.findmythings.AccountActivity;
import edu.gatech.oad.rocket.findmythings.LoginActivity;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;
import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.control.Login;
import edu.gatech.oad.rocket.findmythings.model.Member;
import edu.gatech.oad.rocket.findmythings.model.User;

public class Cristina extends ActivityInstrumentationTestCase2<LoginActivity> {

	LoginActivity activity;
	Login x = new Login();
	
	public Cristina() {
		super("andtest.threads.asynctask", LoginActivity.class);
	}
	
	protected void setUp() throws Exception {  
		super.setUp();  
		activity = getActivity();  
	}  

	public void testLogin() {
		
		final String email1 = "Tyrion@Lannister";
		final String pass1 = "lion";
		final String email2 = "Daenerys@Targeryen";
		final String pass2 = "dragon";
		final String email3 = "Jon@Snow";
		final String pass3 = "crow";
		final String email4 = "Ned@Stark";
		final String pass4 = "winter";
		
		Member m1 = new User(email1, pass1);
		Member m2 = new User(email2, pass2);
		Member m3 = new User(email3, pass3);
		Member m4 = new User(email4, pass4);
		
		x.register(m1);
		x.register(m2);
		x.register(m3);
		
		ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);

		Activity LoginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		assertTrue(x.verifyUser(m1));
		assertTrue(x.verifyUser(m2));
		assertTrue(x.verifyUser(m3));
		assertFalse(x.verifyUser(m4));
		LoginActivity.finish();
	}
}










