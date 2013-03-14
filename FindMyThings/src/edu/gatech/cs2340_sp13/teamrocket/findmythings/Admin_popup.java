package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Admin_popup extends Activity implements OnPreferenceChangeListener {

	/**
	 * Checkbox reference
	 */
	private Switch checkLock;
	
	/**
	 * Id from AdminActivity
	 */
	private int id;
	
	/**
	 * Button reference
	 */
	private Button delete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_popup);
		
		Intent i = getIntent();
		id = i.getExtras().getInt("id");
		
		Login.getData().get(id);
		delete = (Button)findViewById(R.id.delete);
		checkLock = (Switch)findViewById(R.id.isLocked);
		checkLock.setChecked((Login.data.get(id).locked()));
		
		checkLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				   ((User)Login.data.get(id)).setLock(isChecked);
			   }
		});
		
		delete.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Login.data.remove(id);
						finish();
					}
				});
		setTitle("User Attributes");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_popup, menu);
		return true;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		return false;
	}

}
