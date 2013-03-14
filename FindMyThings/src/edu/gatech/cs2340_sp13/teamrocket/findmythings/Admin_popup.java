package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.CheckBox;

public class Admin_popup extends Activity {

	/**
	 * Checkbox reference
	 */
	private CheckBox checkLock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_popup);
		
		Intent i = getIntent();
		int id = i.getExtras().getInt("id");
		
		Login.getData().get(id);
		checkLock = (CheckBox)findViewById(R.id.isLocked);
		checkLock.setChecked(Login.getData().get(id).locked());
		
		setTitle("User Attributes");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_popup, menu);
		return true;
	}

}
