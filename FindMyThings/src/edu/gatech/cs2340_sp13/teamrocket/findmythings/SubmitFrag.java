package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SubmitFrag extends PreferenceFragment implements OnPreferenceChangeListener {
	
	public static String currValue;
	
	public static ListPreference mListPref;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_type);
        
        //Gets type from ListPreference
        mListPref = (ListPreference) findPreference("type_pref");
        

        CharSequence currText = mListPref.getEntry();
        currValue = mListPref.getValue();
        mListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	mListPref.setSummary(mListPref.getEntry().equals("Lost") ? "I'm looking for something that is mine.":"I'm looking for something I need");
                    return true;
                }
            });
	}
	


	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
