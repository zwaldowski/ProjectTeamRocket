package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.os.Bundle;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource

		// In the simplified UI, fragments are not used at all and we instead
		// use the older PreferenceActivity APIs.

		// Add 'general' preferences.
        
		addPreferencesFromResource(R.xml.pref_general);
        
		// Add 'notifications' preferences, and a corresponding header.
	
		
		addPreferencesFromResource(R.xml.pref_notification);

		// Add 'data and sync' preferences, and a corresponding header.
		
		
		addPreferencesFromResource(R.xml.pref_data_sync);
		
			
		}
	

}
