package edu.gatech.oad.rocket.findmythings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;

import edu.gatech.oad.rocket.findmythings.model.Category;
import edu.gatech.oad.rocket.findmythings.model.Type;
import edu.gatech.oad.rocket.findmythings.util.*;
/**
 * CS 2340 - FindMyStuff Android App
 *
 * @author TeamRocket
 * */
public class SubmitFragment extends PreferenceFragment implements OnPreferenceChangeListener {

	/**
	 * UI References
	 */
	public static ListPreference TypeListPref, CatListPref;

	/**
	 * Updates the UI based on a given Item Type.
	 * @param value An Item Type enumerated value.
	 */
	public void syncTypePref(Type value) {
		SubmitActivity activity = (SubmitActivity)getActivity();
		
		String literal = EnumHelper.toIntString(value);
		String name = getString(R.string.pref_type) + " - " + EnumHelper.localizedFromArray(activity, R.array.item_type, value);
		String desc = EnumHelper.localizedFromArray(activity, R.array.item_type_descriptions, value);
		
		TypeListPref.setValue(literal);
		TypeListPref.setTitle(name);
		TypeListPref.setSummary(desc);
	}

	/**
	 * Updates the UI Based on a given Item Category.
	 * @param value An Item Category enumerated value.
	 */
	public void syncCatPref(Category value) {
		SubmitActivity activity = (SubmitActivity)getActivity();
		
		String literal = EnumHelper.toIntString(value);
		String name = getString(R.string.category_type) + " - " + EnumHelper.localizedFromArray(activity, R.array.item_category, value);
		String desc = EnumHelper.localizedFromArray(activity, R.array.item_category_descriptions, value);
		
		CatListPref.setValue(literal);
		CatListPref.setTitle(name);
		CatListPref.setSummary(desc);
	}

	/**
	 * starts to create the actual submit window
	 * @param Bundle savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_type);

        //Gets type from ListPreference
        TypeListPref = (ListPreference) findPreference("type_pref");
        CatListPref = (ListPreference) findPreference("cat_pref");

        syncTypePref(((SubmitActivity)getActivity()).getItemType());

        TypeListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            
        	/**
        	 * deals with the action to take once the a preference has been changed 
        	 * @param Preference preference
        	 * @param Object newValue
        	 * @return boolean true when done
        	 */
        	@Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	String value = (String)newValue;
            	Type newType = EnumHelper.forIntString(value, Type.class);
				syncTypePref(newType);
				
				((SubmitActivity)getActivity()).setItemType(newType);
                return true;
            }
        });

        CatListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
        	/**
        	 * deals with the action to take once the a preference has been changed 
        	 * @param Preference preference
        	 * @param Object newValue
        	 * @return boolean true when done
        	 */
        	@Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	String value = (String)newValue;
            	Category newCategory = EnumHelper.forIntString(value, Category.class);
            	syncCatPref(newCategory);
            	((SubmitActivity)getActivity()).setItemCategory(newCategory);
            	return true;
            }
       });
	}

	/**
	 * deals with the action to take once the a preference has been changed 
	 * @param Preference preference
	 * @param Object newValue
	 * @return boolean true when done
	 */
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
	
		return true;
	}

}
