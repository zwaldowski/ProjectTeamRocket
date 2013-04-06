package edu.gatech.oad.rocket.findmythings.test;

import edu.gatech.oad.rocket.findmythings.MyAccount;
import edu.gatech.oad.rocket.findmythings.MyAccountEdit;
import edu.gatech.oad.rocket.findmythings.R;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.Menu;

public class Justin extends ActivityInstrumentationTestCase2<MyAccount> {
	
	private MyAccount mActivity;
	
	private Menu mBut;
	
	public Justin() {
		 super("andtest.threads.asynctask", MyAccount.class);
	}

	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }
	public void testMenu() {
		//Menu button should go to MyAccountEdit Activity
		ActivityMonitor monitor = getInstrumentation().addMonitor(MyAccountEdit.class.getName(), null, false);

		//Select menu button
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(mActivity, R.id.toEdit, 0);
		
		Activity toEditActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		//If 1, then it went to the Activity
		assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		toEditActivity.finish();
	}
	
	
	

}
