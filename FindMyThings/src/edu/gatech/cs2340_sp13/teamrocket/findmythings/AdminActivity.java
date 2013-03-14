package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdminActivity extends ListActivity {

	/**
	 * Used to display the users
	 */
	private ArrayAdapter<Member> mUsers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_admin_layout);
		//Instantiate ArrayAdapter and tell it to display the list of Members from Login
		mUsers = new ArrayAdapter<Member>(this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, Login.getData());
		setListAdapter(mUsers);
	}
	
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		mUsers.remove(Login.getData().get((int) id));
	}
	

}
