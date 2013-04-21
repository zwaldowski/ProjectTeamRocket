package edu.gatech.oad.rocket.findmythings.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;

import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.RegisterActivity;
//import edu.gatech.oad.rocket.findmythings.control.Login;
import edu.gatech.oad.rocket.findmythings.model.Member;
import edu.gatech.oad.rocket.findmythings.model.User;

public class Tyler extends ActivityInstrumentationTestCase2<RegisterActivity> {
	
	RegisterActivity mActivity, mReg;
	
	@SuppressWarnings("deprecation")
	public Tyler() { 
//		   super("andtest.threads.asynctask", 
//		     RegisterActivity.class); 
		super("edu.gatech.oad.rocket.findmythings.test", RegisterActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
//        setActivityInitialTouchMode(false);
        Instrumentation mInstrumentation = getInstrumentation();
        mActivity = getActivity();
        
    }
	
	public void testMenu() throws Throwable {
		
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
		
//		runTestOnUiThread(new Runnable() {
		mActivity.runOnUiThread(new Runnable() {
		     public void run() {

		    	 EditText email = (EditText)mActivity.findViewById(R.id.email);
		    	 EditText pass = (EditText)mActivity.findViewById(R.id.pass);
		    	 EditText confpass = (EditText)mActivity.findViewById(R.id.confirmpass);
		    	 EditText phone = (EditText)mActivity.findViewById(R.id.phone);
		    	 EditText address = (EditText)mActivity.findViewById(R.id.address);
		    	 EditText lookfor = (EditText)mActivity.findViewById(R.id.lookingfor);
		    	 
		    	 email.setText(e1);
		    	 pass.setText(p1);
		    	 confpass.setText(p1);
		    	 phone.setText(phone1);
		    	 address.setText(ad1);
		     }
		});

		getInstrumentation().invokeMenuActionSync(mActivity, R.id.register_ok, 0);
		Activity toRegister = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		//assertTrue(Login.currUser != null);
		toRegister.finish();
		
		mActivity.runOnUiThread(new Runnable() {
		     public void run() {

		    	 EditText email = (EditText)mActivity.findViewById(R.id.email);
		    	 EditText pass = (EditText)mActivity.findViewById(R.id.pass);
		    	 EditText confpass = (EditText)mActivity.findViewById(R.id.confirmpass);
		    	 EditText phone = (EditText)mActivity.findViewById(R.id.phone);
		    	 EditText address = (EditText)mActivity.findViewById(R.id.address);
		    	 EditText lookfor = (EditText)mActivity.findViewById(R.id.lookingfor);
		    	 
		    	 email.setText(e2);
		    	 pass.setText(p2);
		    	 confpass.setText(p2);
		    	 phone.setText(phone2);
		    	 address.setText(ad2);
		     }
		});
		
		//assertTrue(Login.currUser == null);
		
		mActivity.runOnUiThread(new Runnable() {
		     public void run() {

		    	 EditText email = (EditText)mActivity.findViewById(R.id.email);
		    	 EditText pass = (EditText)mActivity.findViewById(R.id.pass);
		    	 EditText confpass = (EditText)mActivity.findViewById(R.id.confirmpass);
		    	 EditText phone = (EditText)mActivity.findViewById(R.id.phone);
		    	 EditText address = (EditText)mActivity.findViewById(R.id.address);
		    	 EditText lookfor = (EditText)mActivity.findViewById(R.id.lookingfor);
		    	 
		    	 email.setText(e3);
		    	 pass.setText(p3);
		    	 confpass.setText(p3);
		    	 phone.setText(phone3);
		    	 address.setText(ad3);
		     }
		});
		
		//assertTrue(Login.currUser == null);
	}
}
