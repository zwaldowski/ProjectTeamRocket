package edu.gatech.oad.rocket.findmythings;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import edu.gatech.oad.rocket.findmythings.control.*;
import edu.gatech.oad.rocket.findmythings.model.Member;


/**
 * CS 2340 - FindMyStuff Android App
 * Activity that deals with Admin window
 *
 * @author TeamRocket
 * */
public class AdminActivity extends ListActivity {

	public static final int REQUEST_ADMIN = 1111;

	/**
	 * Used to display the users
	 */
	private ArrayAdapter<Member> mUsers;

	/**
	 * Reference to the view holding the ArrayAdapter
	 * 
	 */
	private ListView mList;

	/**
	 * Reference to search bar
	 */
	private EditText mSearch;

	/**
	 * creates new window with correct layout
	 * @param Bundle savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_admin_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mSearch = (EditText)findViewById(R.id.search_bar);
		mList = (ListView)findViewById(android.R.id.list);

		mList.requestFocus();
		//Instantiate ArrayAdapter and tell it to display the list of Members from Login
		// TODO: REPLACE THIS
		//mUsers = new ArrayAdapter<Member>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, Login.data);
		//TODO: add checkbox next to every item in the list for quicker deletion of users

		mList.setAdapter(mUsers);

		//Allow user to filter by text
		mList.setTextFilterEnabled(true);

		mSearch.addTextChangedListener(new TextWatcher()
	    {
	        @Override
	        public void onTextChanged( CharSequence s, int arg1, int arg2, int arg3) {
			mUsers.getFilter().filter(s);
			mUsers.notifyDataSetChanged();
	        }
	        @Override
	        public void beforeTextChanged( CharSequence arg0, int arg1, int arg2, int arg3) {}

	        @Override
	        public void afterTextChanged(Editable arg0) {}

	    });
	}

	/**
	 * deals with action to take once an item is selected form the list
	 * @param ListView l
	 * @param View v
	 * @param int position
	 * @param long id
	 */
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
		Intent next = new Intent(getApplicationContext(), AdminPopupActivity.class);
		next.putExtra("id",(int)id);
		finish();
	    startActivity(next);
	}
	
	/**
	 * deals with action to do once a key is pressed down
	 * @param int keyCode - key pressed
	 * @param KeyEvent event - event to do in case of pressed
	 * @return boolean true when done
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		//Tells Activity what to do when back key is pressed
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	super.onBackPressed();
			return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * deals with action when an options button is selected
	 * @param MenuItem item
	 * @return boolean  
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish();
	            return true;
	        case R.id.admin_create:
	        	return createAdmin();
	    }
	    return super.onOptionsItemSelected(item);
	}

	/**
	 * creates the options menu 
	 * @param Menu menu
	 * @return boolean true when done
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin_layout, menu);
	    return true;
	}

	/**
	 * Goes to the activity to create a new admin account
	 * @return true
	 */
	public boolean createAdmin() {
		Intent next = new Intent(AdminActivity.this, AdminCreateActivity.class);
		startActivity(next);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		return true;
	}
	
	/**
	 * Called to pop the login window from the navigation stack
	 */
	@Override 
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_down_modal);
    }


}
