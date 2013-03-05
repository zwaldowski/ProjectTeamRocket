package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

public class SubmitFrag extends PreferenceFragment {
	
	public static String currValue;
	
	private ListPreference mListPref;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_type);
        
        //Gets type from ListPreference
        mListPref = (ListPreference) findPreference("type_pref");
        CharSequence currText = mListPref.getEntry();
        currValue = mListPref.getValue();

	}
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Let's do something a preference value changes
        if (key.equals(R.xml.pref_type)) {
            mListPref.setSummary(mListPref.getEntry()=="0" ? "stuff" : "stuff");
        }
       
    }

}
