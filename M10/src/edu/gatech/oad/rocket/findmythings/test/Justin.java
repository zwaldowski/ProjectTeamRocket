package edu.gatech.oad.rocket.findmythings.test;

import edu.gatech.oad.rocket.findmythings.LoginWindow;
import edu.gatech.oad.rocket.findmythings.MyAccount;
import android.test.ActivityInstrumentationTestCase2;

public class Justin extends ActivityInstrumentationTestCase2<MyAccount> {
	
	private MyAccount mActivity;
	
	public Justin(Class<MyAccount> activityClass) {
		super(activityClass);
	}
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }
	
	
	

}
