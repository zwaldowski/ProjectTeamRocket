package edu.gatech.oad.rocket.findmythings;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import edu.gatech.oad.rocket.findmythings.control.*;
import edu.gatech.oad.rocket.findmythings.model.Item;
import edu.gatech.oad.rocket.findmythings.model.Type;
import edu.gatech.oad.rocket.findmythings.util.*;
/**
 * CS 2340 - FindMyStuff Android App
 *
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ItemDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 *
 * @author TeamRocket
 */
public class MainActivity extends ListActivity  {

	/**
	 * The class of {@link Item} displayed in this list.
	 */
	public static Type mType = null;
	
	/**
	 * Reference to the actionbar (the thing at the top of every activity)
	 */
	private ActionBar actionBar;
	
	/**
	 * Used for the AlertDialog when user tries to sign out
	 */
	private boolean logOut = false;
	
	/**
	 * Reference to Singleton class
	 */
	private Controller control = Controller.shared();
	
	/**
	 * The ArrayAdapter to be displayed
	 */
	public static Adapter adapter;
	
	/**
	 * Current list the Adapter is displaying
	 */
	public static ArrayList<Item> currList;
	
	/**
	 * Reference to the view holding the ArrayAdapter
	 * 
	 */
	private ListView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		
		mView = (ListView)findViewById(android.R.id.list);
		mView.setTextFilterEnabled(true);
		EditText mSearch = (EditText)findViewById(R.id.main_search_bar);
		mSearch.addTextChangedListener(new TextWatcher()
	    { //TODO: Partial string searches
	        @Override
	        public void onTextChanged( CharSequence s, int arg1, int arg2, int arg3) {
			adapter.getFilter().filter(s);
			adapter.notifyDataSetChanged();
	        }
	        @Override
	        public void beforeTextChanged( CharSequence arg0, int arg1, int arg2, int arg3) {}

	        @Override
	        public void afterTextChanged(Editable arg0) {}

	    });
		
		//Create tabs and hide title
		actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(true);
	    createTabs();
	   
	    //Array to be represented by the adapter
	  	currList = control.getItem(mType);
	  	//Takes the array and creates individual views for each item
	  	adapter = new Adapter(this,
	  			android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, currList);
	  	
	  	mView.setAdapter(adapter);
	    	
		setTitle("Find My Things");
		getActionBar().setDisplayHomeAsUpEnabled(false);
		
	}
	
	/**
	 * Updates the ArrayList the adapter is displaying as well as the Type of items being displayed
	 * @param tempList
	 * @param kind
	 */
	public static void update(ArrayList<Item> tempList, Type kind) {
		currList = tempList;
		adapter.setList(tempList);		
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
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.item_list_submit:
			return toSubmit();
	        case R.id.menu_list_search:
	        	Intent i = new Intent(MainActivity.this, FilterActivity.class);
				startActivityForResult(i, 1);
			    overridePendingTransition(R.anim.slide_up_modal, android.R.anim.fade_out);
				return true;
	        case R.id.menu_search:
				Intent im = new Intent(MainActivity.this, Search_Main.class);
				finish();
				startActivity(im);
			    overridePendingTransition(R.anim.slide_up_modal, android.R.anim.fade_out);
				return true;
			case R.id.menu_login: 
				return Login.currUser==null? logIn():logOut(); 
			case R.id.menu_account: 
				return toAccount();
			case R.id.menu_admin:
				return toAdmin();
	    }
	    return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    String noOverKey = getString(R.string.key_nooverride_animation);
	    Bundle extraInfo = getIntent().getExtras();
		if (extraInfo == null || (extraInfo != null && !extraInfo.getBoolean(noOverKey))) {
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
	}

	@Override
	protected void onListItemClick (ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent next = new Intent(getApplicationContext(), ItemDetailActivity.class);
		next.putExtra("id",(int)id);
		finish();
	    startActivity(next);
	    overridePendingTransition(R.anim.slide_up_modal, R.anim.hold);
	}

	/**
	 * Opens a new Submit activity with the current type of item.
	 */
	public boolean toSubmit() {
		if(Login.currUser!=null) {
			Intent goToNextActivity = new Intent(MainActivity.this, Submit.class);
			if(mType!=null)
				goToNextActivity.putExtra(Type.ID, mType.ordinal());
			finish();
			startActivity(goToNextActivity);
			overridePendingTransition(R.anim.slide_up_modal, R.anim.hold);
		}
		else {
			ErrorDialog toLogin =  new ErrorDialog("Must Sign-in to submit an item.", "Sign-in", "Cancel");
			AlertDialog.Builder temp = toLogin.getDialog(this,
				new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
		            	Intent goToNextActivity = new Intent(getApplicationContext(), LoginWindow.class);
		            		goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		               	startActivityForResult(goToNextActivity, 1);
		            }	
				}, 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
			            	//cancel
					}    
			
				});
			temp.show();
		}
	    return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_item_list, menu);
		getMenuInflater().inflate(R.menu.activity_main_menu, menu);
		
		//Set Login Title
		MenuItem loginMenu = menu.findItem(R.id.menu_login);
		String title = Login.currUser==null? "Login":"Logout";
		loginMenu.setTitle(title);
						
		//Set Account Title
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
	
	/**
	 * Creates the tabs, duh.
	 */
	public void createTabs() {
		Tab tab;
	    String tabName = "";
	    for(int i = 0; i <5;i++) {
	    	switch(i) { //Create tabs
	    	case 0:
	    		tabName = "ALL";
	    		break;
	    	case 1:
	    		tabName = "LOST";
	    		break;
	    	case 2:
	    		tabName = "FOUND";
	    		break;
	    	case 3: 
	    		tabName = "DONATIONS";
	    		break;
	    	case 4:
	    		tabName = "REQUESTS";
	    		break;
	    	}
	    	tab = actionBar.newTab()
		            .setText(tabName)
		    		.setTabListener(new MainTabListener(tabName));
	    	 actionBar.addTab(tab);
	    }
	}

	/**
	 * Returns the kind of Item displayed in this list.
	 * @return An enumerated Type value
	 */
	public Type getItemType() {
		return mType;
	}
	
	   /**
     * Go to LoginWindow
     */
    public boolean logIn() {
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
    
    /**
     * Logout
     */
    public boolean logOut() {
    	ErrorDialog toLogin =  new ErrorDialog("Really log out?", "Sign out", "Cancel");
		AlertDialog.Builder temp = toLogin.getDialog(this,
			new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				//Clear current user
				Login.currUser=null;
				//Making sense is for squares
				finish(); 
				startActivity(getIntent());
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				logOut = true;
	            }	
			}, 
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					logOut = false;
				}    
		
			});
		temp.show();
    	return logOut;    	
    }
}
