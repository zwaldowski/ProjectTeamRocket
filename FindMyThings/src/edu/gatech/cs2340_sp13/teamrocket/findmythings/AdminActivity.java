package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AdminActivity extends ListActivity {

	/**
	 * Used to display the users
	 */
	private ArrayAdapter<Member> mUsers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//make our presenter
		mUsers = new ArrayAdapter<Member>(this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, Login.getData());
		setListAdapter(mUsers);
	}
	

}
