package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;


public class SubmitFrag extends PreferenceFragment implements OnPreferenceChangeListener {
	
		
	public ListPreference mListPref;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_type);
        
        //Gets type from ListPreference
        mListPref = (ListPreference) findPreference("type_pref");
        
        
        
        mListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
	String ss = mListPref.getValue();
            	mListPref.setSummary(mListPref.getValue().equals("0") ? "I'm looking for something I need": "I'm looking for something that is mine.");
            	mListPref.setTitle(mListPref.getValue().equals("0") ? "Kind - Donate": "Kind - Lost");
                    return true;
                }
            });
	}
	


	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
