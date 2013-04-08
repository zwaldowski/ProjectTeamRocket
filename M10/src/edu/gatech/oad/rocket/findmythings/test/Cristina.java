package edu.gatech.oad.rocket.findmythings.test;

import edu.gatech.oad.rocket.findmythings.RegisterActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import edu.gatech.oad.rocket.findmythings.R;

public class Cristina extends ActivityInstrumentationTestCase2<RegisterActivity> {

	//private TextView result;
	
	public Cristina() {
		super(RegisterActivity.class);
	}
	
	protected void setUp() throws Exception {  
		super.setUp();  
		RegisterActivity registerAct = getActivity();  
		//result = (TextView) registerAct.findViewById(R.id.result);  
	}  
	
	
	
}
