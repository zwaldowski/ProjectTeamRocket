package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.widget.EditText;

public class SubmitFrag extends PreferenceFragment implements OnPreferenceChangeListener {
	
	//UI References
	public static ListPreference TypeListPref, CatListPref;
	
	private Controller control = Controller.shared();
	
	public static int cat = 2;
	
	public void syncTypePref(Item.Class value) {
    	Submit activity = (Submit)getActivity();
		TypeListPref.setValue(((Integer)value.ordinal()).toString());
    	TypeListPref.setTitle("Kind - " + value.getLocalizedValue(activity));
    	TypeListPref.setSummary(value.getLocalizedDescription(activity));
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_type);
        
        //Gets type from ListPreference
        TypeListPref = (ListPreference) findPreference("type_pref");
        CatListPref = (ListPreference) findPreference("cat_pref");
        
        syncTypePref(((Submit)getActivity()).getItemClass());
        
        //TODO: Create one listener class for all Preferences. Maybe.
        TypeListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	Item.Class newClass = Item.Class.forInt(Integer.parseInt((String)newValue));
            	syncTypePref(newClass);
            	((Submit)getActivity()).setItemClass(newClass);
                return true;
            }
        });
        
        CatListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	
            	int i = Integer.parseInt(newValue.toString());
            	cat = i;
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
