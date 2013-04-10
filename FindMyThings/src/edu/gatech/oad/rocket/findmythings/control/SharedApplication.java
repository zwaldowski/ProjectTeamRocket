package edu.gatech.oad.rocket.findmythings.control;

import android.app.Application;

public class SharedApplication extends Application {

	private static SharedApplication mInstance;

	public static SharedApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();  
		mInstance = this;
	}

}
