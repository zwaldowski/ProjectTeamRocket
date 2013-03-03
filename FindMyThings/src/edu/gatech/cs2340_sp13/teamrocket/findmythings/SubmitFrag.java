package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

public class SubmitFrag extends PreferenceFragment {
	
	public static String currValue;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_type);
        
        //Gets type from ListPreference
        ListPreference listPreference = (ListPreference) findPreference("type_pref");
        CharSequence currText = listPreference.getEntry();
        currValue = listPreference.getValue();

	}
}
