package edu.gatech.oad.rocket.findmythings.test;


import edu.gatech.oad.rocket.findmythings.AccountActivity;
import edu.gatech.oad.rocket.findmythings.AccountEditActivity;
import edu.gatech.oad.rocket.findmythings.control.LoginManager;
import edu.gatech.oad.rocket.findmythings.model.AppMember;
import edu.gatech.oad.rocket.findmythings.R;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;

public class Justin extends ActivityInstrumentationTestCase2<AccountEditActivity> {
	
	/**
	 * AccountEditActivity
	 */
	private AccountEditActivity mActivity;
	
	/**
	 * AccountActivity text field displaying name
	 */
	private EditText actName;
	
	/**
	 * AccountActivity
	 */
	private Activity toAccountActivity;
	
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
		//Test user
		AppMember toTest = new AppMember();
		
		toTest.setName("Doug");
		toTest.setEmail(email);
		
		//Get LoginManager
		LoginManager currUser = LoginManager.getLoginManager();
		
		//Current user to Doug
		currUser.setCurrentUser(toTest);
				
		//Menu button should go to AccountEditActivity Activity
		ActivityMonitor monitor = getInstrumentation().addMonitor(AccountActivity.class.getName(), null, false);
		
		runTestOnUiThread(new Runnable() {
		     public void run() {
		    	//Get reference to TextField containing name
		    	 EditText name = (EditText)mActivity.findViewById(R.id.personnameedit);
		 		//Set new name
		 		name.setText("Funny");
		     }
		});
	
		//Select menu button
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(mActivity, R.id.edit_ok, 0);
		
		toAccountActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
		//Check if AccountActivity Activity was started
		assertEquals(true, getInstrumentation().checkMonitorHit(monitor, 1));
		//Check if name was successfully changes
		runTestOnUiThread(new Runnable() {
		     public void run() {
		    	//Get reference to TextField containing name
		    	actName = (EditText)toAccountActivity.findViewById(R.id.personname);
		     }
		});
		
		assertEquals("User name has been changed","Funny", currUser.getCurrentUser().getName());
		assertEquals("TextField displaying the user's name is correct","Funny",actName.getText().toString());
		//Patty Mayonnaise
		currUser.logout();
		toAccountActivity.finish();
		
	}
	
}
