package edu.gatech.oad.rocket.findmythings.test;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import edu.gatech.oad.rocket.findmythings.MainActivity;
import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.SubmitActivity;
import edu.gatech.oad.rocket.findmythings.control.LoginManager;
import edu.gatech.oad.rocket.findmythings.model.AppMember;

public class Tyler2 extends ActivityInstrumentationTestCase2<MainActivity> {
  
	MainActivity mActivity, mReg;
	
	@SuppressWarnings("deprecation")
	public Tyler2() { 
		super("edu.gatech.oad.rocket.findmythings", MainActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }
	
	public void testMenu() throws Throwable {
		
		LoginManager loginMan = LoginManager.getLoginManager();
		loginMan.logout();
		
		assertFalse(LoginManager.getLoginManager().isLoggedIn());
		ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
		getInstrumentation().invokeMenuActionSync(mActivity, R.id.item_list_submit, 0);

		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
	  getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_CENTER);
		Activity toMain = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		
		AppMember temp = new AppMember();
		temp.setName("Spongebob");
		temp.setEmail("valid@not.com");
		loginMan.setCurrentUser(temp);
		loginMan.setCurrentEmailAndToken("valid@not.com", "Spongebob");
		assertTrue(LoginManager.getLoginManager().isLoggedIn());
		getInstrumentation().invokeMenuActionSync(mActivity, R.id.item_list_submit, 1000);
		assertEquals(true, getInstrumentation().waitForMonitorWithTimeout(monitor, 10) instanceof SubmitActivity);
		loginMan.logout();
		toMain.finish();
		
		
		mActivity.runOnUiThread(new Runnable() {
		     public void run() {
		    	 
		    	 
		     }
		});		
	}
}
