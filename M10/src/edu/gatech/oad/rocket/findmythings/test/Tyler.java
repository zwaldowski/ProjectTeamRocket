package edu.gatech.oad.rocket.findmythings.test;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import edu.gatech.oad.rocket.findmythings.AccountActivity;
import edu.gatech.oad.rocket.findmythings.AccountEditActivity;
import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.RegisterActivity;
import edu.gatech.oad.rocket.findmythings.control.Login;
import edu.gatech.oad.rocket.findmythings.model.Member;
import edu.gatech.oad.rocket.findmythings.model.User;

public class Tyler extends ActivityInstrumentationTestCase2<RegisterActivity> {
	
	Activity mActivity;
	
	@SuppressWarnings("deprecation")
	public Tyler() { 
		   super("andtest.threads.asynctask", 
		     RegisterActivity.class); 
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }
	
	public void testRegister() throws Throwable {
		
		final String e1 = "spongebob@square.pants";
		final String p1 = "test";
		final String phone1 = "77078978999";
		final String ad1 = "Some shit";
		final String e2 = "nope";
		final String p2 = "test";
		final String phone2 = "7707897899";
		final String ad2 = "1222344556";
		final String e3 = "yes@maybe";
		final String p3 = "test";
		final String phone3 = "770789789990";
		final String ad3 = "NO NUMBAHS";

//		Login.currUser = new User();

		ActivityMonitor monitor = getInstrumentation().addMonitor(RegisterActivity.class.getName(), null, false);
		
		runTestOnUiThread(new Runnable() {
		     public void run() {

		    	 EditText email = (EditText)mActivity.findViewById(R.id.email);
		    	 EditText pass = (EditText)mActivity.findViewById(R.id.pass);
		    	 EditText confpass = (EditText)mActivity.findViewById(R.id.confirmpass);
		    	 EditText phone = (EditText)mActivity.findViewById(R.id.phone);
		    	 EditText address = (EditText)mActivity.findViewById(R.id.address);
		    	 EditText lookfor = (EditText)mActivity.findViewById(R.id.lookingfor);
		    	 
		    	 email.setText(e1);
		     }
		});
		
		Activity toRegister = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		assertEquals("Funny", Login.currUser.getName());
		toRegister.finish();
		
	}
}
