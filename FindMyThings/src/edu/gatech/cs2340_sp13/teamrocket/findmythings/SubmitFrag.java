package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.widget.EditText;


public class SubmitFrag extends PreferenceFragment implements OnPreferenceChangeListener {
	
	
	//UI References
	public ListPreference TypeListPref, CatListPref;
	
	private Controller control = new Controller();
	
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_type);
        
        
        
        
        //Gets type from ListPreference
        TypeListPref = (ListPreference) findPreference("type_pref");
        CatListPref = (ListPreference) findPreference("cat_pref");
        
        //TODO: Create one listener class for all Preferences. Maybe.
        TypeListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	String s = newValue.toString();
            	TypeListPref.setSummary(s.equals("0") ?  "I'm looking for something that is mine.":"I'm looking for something I need");
            	TypeListPref.setTitle(s.equals("0") ? "Kind - Lost":"Kind - Donate");
                    return true;
                }
            });
        
        CatListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	
            	int i = Integer.parseInt(newValue.toString());
            	switch(i) {
            	case 0:
            		CatListPref.setSummary("A valuable object that has belonged to a family for several generations.");
            		CatListPref.setTitle("Category - Heirloom");
            		return true;
            	case 1:
            		CatListPref.setSummary("A small item kept in memory of the person who gave it or originally owned it.");
            		CatListPref.setTitle("Category - Keepsake");
            		return true;
            	case 2:
            		CatListPref.setSummary("Items not fitting into another category");
            		CatListPref.setTitle("Category - Misc");
            		return true;
            		

            	}
            	
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
