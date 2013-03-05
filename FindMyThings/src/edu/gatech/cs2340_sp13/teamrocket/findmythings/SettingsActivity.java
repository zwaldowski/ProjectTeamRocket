package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.KeyEvent;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {
	/**
	 * Determines whether to always show the simplified settings UI, where
	 * settings are presented in a single list. When false, settings are shown
	 * as a master/detail two-pane view on tablets. When true, a single pane is
	 * shown on tablets.
	 */
	//private static final boolean ALWAYS_SIMPLE_PREFS = false;
	
	public static class SettingsFragment extends PreferenceFragment {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        String settings = getArguments().getString("settings");
	        if ("lookingfor".equals(settings)) {
	            addPreferencesFromResource(R.xml.main_lookingfor);
	        }
	    }
	}
	
	@Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.main_headers, target);
    }

	/*@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

        getFragmentManager().beginTransaction()
                .replace(R.layout.settingsfrag, new SettingsFragment())
                .commit();
		setTitle("Find My Things");
	}*/
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		//Tells Activity what to do when back key is pressed
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	Intent goToNextActivity = new Intent(getApplicationContext(), LoginWindow.class);
			finish();
			startActivity(goToNextActivity);
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	

	/** {@inheritDoc} */
	/*public boolean onIsMultiPane() {
		return isXLargeTablet(this) && !isSimplePreferences(this);
	}*/

	/**
	 * Helper method to determine if the device has an extra-large screen. For
	 * example, 10" tablets are extra-large.
	 */
	/*private static boolean isXLargeTablet(SettingsActivity settingsActivity) {
		return (settingsActivity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}*/

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if this is forced via {@link #ALWAYS_SIMPLE_PREFS}, or the device
	 * doesn't have newer APIs like {@link PreferenceFragment}, or the device
	 * doesn't have an extra-large screen. In these cases, a single-pane
	 * "simplified" settings UI should be shown.
	 */
	/*private static boolean isSimplePreferences(SettingsActivity settingsActivity) {
		return ALWAYS_SIMPLE_PREFS
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				|| !isXLargeTablet(settingsActivity);
	}*/


}
