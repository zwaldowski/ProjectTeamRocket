package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

public class AdminActivity extends ListActivity {

	/**
	 * Used to display the users
	 */
	private ArrayAdapter<Member> mUsers;
	
	/**
	 * Reference to the list that the ArrayAdaper will generate
	 */
	private ListView mList;
	
	/**
	 * Reference to search bar
	 */
	private EditText mSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.activity_admin_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mSearch = (EditText)findViewById(R.id.search_bar);
		mList = (ListView)findViewById(android.R.id.list);
		
		//Instantiate ArrayAdapter and tell it to display the list of Members from Login
		mUsers = new ArrayAdapter<Member>(this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, Login.data);
		//TODO: add checkbox next to ever item in the list for quicker deletion of users
		
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
	
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id) {
		//TODO: make some kind of activity or view to allow admin to change user attributes
		super.onListItemClick(l, v, position, id);
		Intent next = new Intent(getApplicationContext(), Admin_popup.class);
		next.putExtra("id",(int)id);
		finish();
	    startActivity(next);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	goToParentActivity();
	            return true;
	        case R.id.admin_create:
	        	return createAdmin();
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin_layout, menu);
	    return true;
	}
	
	/**
	 * Goes back to the main activity
	 */
	public void goToParentActivity() {
		Intent next = new Intent(AdminActivity.this, MainActivity.class);
		finish();
		startActivity(next);
	}
	
	/**
	 * Goes to the activity to create a new admin account
	 * @return true
	 */
	public boolean createAdmin() {
		Intent next = new Intent(AdminActivity.this, Admin_create.class);
		finish();
		startActivity(next);
		return true;
	}
	

}
