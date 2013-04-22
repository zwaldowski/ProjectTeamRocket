package edu.gatech.oad.rocket.findmythings.test;

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
		
		// Make sure there is no currently logged in user
		LoginManager loginMan = LoginManager.getLoginManager();
		loginMan.logout();	
		assertFalse(LoginManager.getLoginManager().isLoggedIn());
		
		// Attempt to submit an item anonymously and check for failure
		ActivityMonitor monitor = getInstrumentation()
				.addMonitor(MainActivity.class.getName(), null, false);
		assertFalse(getInstrumentation().checkMonitorHit(monitor, 1));
		getInstrumentation().invokeMenuActionSync
				(mActivity, R.id.item_list_submit, 0);
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
	    getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_CENTER);
		
	    // Log in a user and check to make sure it succeeded
	    AppMember temp = new AppMember();
		temp.setName("Spongebob");
		temp.setEmail("valid@not.com");
		loginMan.setCurrentUser(temp);
		loginMan.setCurrentEmailAndToken("valid@not.com", "Spongebob");
		assertTrue(LoginManager.getLoginManager().isLoggedIn());
		
		// Attempt once more to submit an item and check for success
		ActivityMonitor monitor2 = getInstrumentation()
				.addMonitor(SubmitActivity.class.getName(), null, false);
		getInstrumentation().invokeMenuActionSync(mActivity, R.id.item_list_submit, 0);
		assertTrue(getInstrumentation().checkMonitorHit(monitor2, 1));
		loginMan.logout();
	}
}
