package edu.gatech.oad.rocket.findmythings.test;

import edu.gatech.oad.rocket.findmythings.AccountActivity;
import edu.gatech.oad.rocket.findmythings.AccountEditActivity;
import edu.gatech.oad.rocket.findmythings.control.Login;
import edu.gatech.oad.rocket.findmythings.R;
import edu.gatech.oad.rocket.findmythings.model.Member;
import edu.gatech.oad.rocket.findmythings.model.User;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;

public class Justin extends ActivityInstrumentationTestCase2<AccountEditActivity> {
	
	private AccountEditActivity mActivity;
	
	@SuppressWarnings("deprecation")
	public Justin() {
		 super("andtest.threads.asynctask", AccountEditActivity.class);
	}

	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }
	
	/**
	 * Sets the currently logged in User, goes to AccountEditActivy and changes the Users name
	 * then goes to AccountActivity and validates that the user's name was successfully changed
	 * @throws Throwable
	 */
	public void testMenu() throws Throwable {
		String email = "test@test.test";
		String pass = "test";
		//Test user
		Member toTest = new User(email, pass);
		toTest.setName("Doug");
		
		Login.currUser = toTest;
				
		//Menu button should go to AccountEditActivity Activity
		ActivityMonitor monitor = getInstrumentation().addMonitor(AccountActivity.class.getName(), null, false);
		
		
		runTestOnUiThread(new Runnable() {
		     public void run() {
		    	//Get reference to Textfield containing name
		    	 EditText name = (EditText)mActivity.findViewById(R.id.personnameedit);
		 		//Set new name
		 		name.setText("Funny");
		     }
		});
	
		//Select menu button
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(mActivity, R.id.edit_ok, 0);
		
		Activity toAccountActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		//Check if AccountActivity Activity was started
		assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		//Check if name was successfully changes
		assertEquals("Funny", Login.currUser.getName());
		toAccountActivity.finish();
	}
	
}
