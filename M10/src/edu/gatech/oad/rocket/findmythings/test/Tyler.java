package edu.gatech.oad.rocket.findmythings.test;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.RegisterActivity;
import edu.gatech.oad.rocket.findmythings.control.LoginManager;

public class Tyler extends ActivityInstrumentationTestCase2<RegisterActivity> {
	
	RegisterActivity mActivity, mReg;
	
	@SuppressWarnings("deprecation")
	public Tyler() { 
		super("edu.gatech.oad.rocket.findmythings.test", RegisterActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }
	
	public void testMenu() throws Throwable {
		
		final String n1 = "Spongebob";
		final String n2 = "Virgil";
		final String e1 = "spongebob@square.pants";
		final String e2 = "nope";
		//final String e3 = "yes@almost";
		final String p = "testing";
		final String phone1 = "77078978990";
		final String phone2 = "7707897899";
		final String ad1 = "123 Georgia Fucking Tech";
		final String ad2 = "987654321";
		

		LoginManager loginMan = LoginManager.getLoginManager();

		ActivityMonitor monitor = getInstrumentation().addMonitor(RegisterActivity.class.getName(), null, false);
		
		mActivity.runOnUiThread(new Runnable() {
		     public void run() {

		    	 EditText email = (EditText)mActivity.findViewById(R.id.email);
		    	 EditText pass = (EditText)mActivity.findViewById(R.id.pass);
		    	 EditText confpass = (EditText)mActivity.findViewById(R.id.confirmpass);
		    	 EditText phone = (EditText)mActivity.findViewById(R.id.phone);
		    	 EditText address = (EditText)mActivity.findViewById(R.id.address);
		    	 EditText lookfor = (EditText)mActivity.findViewById(R.id.lookingfor);
		    	 
		    	 lookfor.setText(n1);
		    	 lookfor.setText(n1);
		    	 email.setText(e1);
		    	 pass.setText(p);
		    	 confpass.setText(p);
		    	 phone.setText(phone1);
		    	 address.setText(ad1);
		     }
		});

		getInstrumentation().invokeMenuActionSync(mActivity, R.id.register_ok, 0);
		Activity toRegister1 = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		assertTrue(loginMan.getCurrentUser() != null);
		loginMan.logout();
		toRegister1.finish();
		
		mActivity.runOnUiThread(new Runnable() {
		     public void run() {

		    	 EditText email = (EditText)mActivity.findViewById(R.id.email);
		    	 EditText pass = (EditText)mActivity.findViewById(R.id.pass);
		    	 EditText confpass = (EditText)mActivity.findViewById(R.id.confirmpass);
		    	 EditText phone = (EditText)mActivity.findViewById(R.id.phone);
		    	 EditText address = (EditText)mActivity.findViewById(R.id.address);
		    	 EditText lookfor = (EditText)mActivity.findViewById(R.id.lookingfor);
		    	 
		    	 lookfor.setText(n2);
		    	 email.setText(e2);
		    	 pass.setText(p);
		    	 confpass.setText(p);
		    	 phone.setText(phone2);
		    	 address.setText(ad2);
		     }
		});
		
		getInstrumentation().invokeMenuActionSync(mActivity, R.id.register_ok, 0);
		Activity toRegister2 = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		assertFalse(loginMan.getCurrentUser() != null);
		loginMan.logout();
		toRegister2.finish();
		
		mActivity.runOnUiThread(new Runnable() {
		     public void run() {

		    	 EditText email = (EditText)mActivity.findViewById(R.id.email);
		    	 EditText pass = (EditText)mActivity.findViewById(R.id.pass);
		    	 EditText confpass = (EditText)mActivity.findViewById(R.id.confirmpass);
		    	 
		    	 email.setText(e1);
		    	 pass.setText(p);
		    	 confpass.setText(p);
		     }
		});
		
		getInstrumentation().invokeMenuActionSync(mActivity, R.id.register_ok, 0);
		Activity toRegister3 = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		assertTrue(loginMan.getCurrentUser() != null);
		loginMan.logout();
		toRegister3.finish();
	}
}
