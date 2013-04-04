package edu.gatech.oad.rocket.findmythings;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import edu.gatech.oad.rocket.findmythings.Helpers.TabHelp;
import edu.gatech.oad.rocket.findmythings.NonActivity.*;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 *
 * @author TeamRocket
 */
public class MainActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static class MainFragment extends PreferenceFragment {
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.main_lookingfor);
	      	        	        
	        Intent lostIntent = findPreference(getString(R.string.main_key_lost)).getIntent();
	        lostIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        lostIntent.putExtra(Type.ID, Type.LOST.ordinal());

	        Intent foundIntent = findPreference(getString(R.string.main_key_found)).getIntent();
	        foundIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        foundIntent.putExtra(Type.ID, Type.FOUND.ordinal());

	        Intent donationIntent = findPreference(getString(R.string.main_key_donations)).getIntent();
	        donationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        donationIntent.putExtra(Type.ID, Type.DONATION.ordinal());

	        Intent requestIntent = findPreference(getString(R.string.main_key_requests)).getIntent();
	        requestIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        requestIntent.putExtra(Type.ID, Type.REQUEST.ordinal());
	        
	        //trying new stuff
	        //Intent searchIntent = findPreference(getString(R.string.main_key_searches)).getIntent();
	        //searchIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        Intent searchIntent =  new Intent(getActivity(), Search_Main.class);
	    
	        //Intent(Context packageContext, Class<?> cls)
	        
	        //MainFragment.this,Search_Main.class
	        //end

	    }	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		//Tells Activity what to do when back key is pressed
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_menu, menu);
		
		//Set Login Title
		MenuItem loginMenu = menu.findItem(R.id.menu_login);
        String title = Login.currUser==null? "Login":"Logout";
	    loginMenu.setTitle(title);
				
		//Set Login Title
		MenuItem accountMenu = menu.findItem(R.id.menu_account);
		if(Login.currUser!=null) {
			String account = LoginWindow.Email;
			accountMenu.setTitle(account);
		} else { 
			accountMenu.setVisible(false);
		}
		
		//Show/Hide admin button
		if(Login.currUser==null || !Login.currUser.isAdmin()) {
			MenuItem adminMenu = menu.findItem(R.id.menu_admin);
        	adminMenu.setVisible(false);
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_search:
			Intent i = new Intent(MainActivity.this, Search_Main.class);
			finish();
			startActivity(i);
		    overridePendingTransition(R.anim.slide_up_modal, android.R.anim.fade_out);
			return true;
		case R.id.menu_login: 
			return Login.currUser==null? toLogin():logOut(); 
		case R.id.menu_account: 
			return toAccount();
		case R.id.menu_admin:
			return toAdmin();
			
	}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A reference to the fragment this activity is displaying.
	 * Undefined on Android versions less than Honeycomb.
	 */
	private MainFragment settingsListFragment;

	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(true);

	    Tab tab;
	    String tabName = "";
	    for(int i = 0; i <4;i++) {
	    	switch(i) { //Create tabs
	    	case 0:
	    		tabName = "Lost";
	    		break;
	    	case 1:
	    		tabName = "Found";
	    		break;
	    	case 2: 
	    		tabName = "Donations";
	    		break;
	    	case 3:
	    		tabName = "Reqeusts";
	    		break;
	    	}
	    	tab = actionBar.newTab()
		            .setText(tabName)
		    		.setTabListener(new TabHelp());
		    actionBar.addTab(tab);
	    }
	    
	   
	    
	   
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

	/**
	 * Listener responder for the Sign Out button.
	 */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    	if (key.equals(getString(R.string.main_key_signout))) {
    		boolean shouldLogout = sharedPreferences.getBoolean(key, false);
    		if (shouldLogout) {
    			Editor prefEdit = sharedPreferences.edit();
    			prefEdit.putBoolean(key, false);
    			prefEdit.commit();
    			   			
    		}
     	}
    	
    }
    
    /**
     * Logout
     */
    public boolean logOut() {
    	//Clear current user
		Login.currUser=null;
		//Redraw the activity
		//TODO: Redraw the activity in a way that actually makes sense
		finish(); 
		startActivity(getIntent());
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		return true;
    }
    
    /**
     * Go to LoginWindow
     */
    public boolean toLogin() {
    	Intent toLogin = new Intent(MainActivity.this, LoginWindow.class);
		finish();
		startActivity(toLogin);
		overridePendingTransition(R.anim.slide_up_modal, android.R.anim.fade_out);
		return true;
    }
    
    /**
     * Go to MyAccount
     */
    public boolean toAccount() {
    	Intent toAccount = new Intent(MainActivity.this, MyAccount.class);
		finish();
		startActivity(toAccount);
		overridePendingTransition(R.anim.slide_up_modal, android.R.anim.fade_out);
		return true;
    }
    
    /**
     * Go to AdminActivity
     * @return
     */
    public boolean toAdmin() {
    	Intent toAccount = new Intent(MainActivity.this, AdminActivity.class);
		finish();
		startActivity(toAccount);
		overridePendingTransition(R.anim.slide_up_modal, android.R.anim.fade_out);
		return true;
    }
    
    
   

}
