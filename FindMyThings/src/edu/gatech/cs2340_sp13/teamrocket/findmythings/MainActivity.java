package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

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
public class MainActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
		
	public static class MainFragment extends PreferenceFragment {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.main_lookingfor);

	        Intent lostIntent = findPreference(getString(R.string.main_key_lost)).getIntent();
	        lostIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        lostIntent.putExtra(Item.Class.ID, Item.Class.Lost.ordinal());
	        
	        Intent foundIntent = findPreference(getString(R.string.main_key_found)).getIntent();
	        foundIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        foundIntent.putExtra(Item.Class.ID, Item.Class.Found.ordinal());
	        
	        Intent donationIntent = findPreference(getString(R.string.main_key_donations)).getIntent();
	        donationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        donationIntent.putExtra(Item.Class.ID, Item.Class.Donation.ordinal());
	        
	        Intent requestIntent = findPreference(getString(R.string.main_key_requests)).getIntent();
	        requestIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        requestIntent.putExtra(Item.Class.ID, Item.Class.Request.ordinal());
	    
	        Preference myAccount = findPreference(getString(R.string.main_key_myaccount));
	        myAccount.setSummary(LoginWindow.Email);
	    }
	}

	private MainFragment settingsListFragment;

	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			addPreferencesFromResource(R.xml.main_lookingfor);
		} else {
			settingsListFragment = new MainFragment();
			getFragmentManager().beginTransaction().replace(android.R.id.content,
					settingsListFragment).commit();
		}
		
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    settingsListFragment.getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		settingsListFragment.getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    	if (key.equals(getString(R.string.main_key_signout))) {
    		boolean shouldLogout = sharedPreferences.getBoolean(key, false);
    		if (shouldLogout) {
    			Editor prefEdit = sharedPreferences.edit();
    			prefEdit.putBoolean(key, false);
    			prefEdit.commit();

    			// TODO logout logic
		
    			Intent goToNextActivity = new Intent(getApplicationContext(), LoginWindow.class);
    			goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			finish();
    			startActivity(goToNextActivity);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				return;
    		}
        }
    }

}
