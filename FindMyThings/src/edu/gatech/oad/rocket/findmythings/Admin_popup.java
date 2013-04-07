package edu.gatech.oad.rocket.findmythings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import edu.gatech.oad.rocket.findmythings.control.*;
import edu.gatech.oad.rocket.findmythings.model.Admin;
import edu.gatech.oad.rocket.findmythings.model.Member;
import edu.gatech.oad.rocket.findmythings.model.User;


public class Admin_popup extends Activity implements OnPreferenceChangeListener {

	/**
	 * Switch reference
	 */
	private Switch checkLock, checkAdmin;

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

		//Get id from AdminActivity
		Intent i = getIntent();
		id = i.getExtras().getInt("id");

		delete = (Button)findViewById(R.id.delete);

		checkAdmin = (Switch)findViewById(R.id.isAdmin);
		checkLock = (Switch)findViewById(R.id.isLocked);

		checkAdmin.setChecked((Login.data.get(id).isAdmin()));
		checkLock.setChecked((Login.data.get(id).locked()));

		checkLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			   @Override
			   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				   ((User)Login.data.get(id)).setLock(isChecked);
			   }
		});

		checkAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			   @Override
			   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				   Member temp = Login.data.get(id);
				   Login.data.remove(id);
				   if(isChecked)
					   Login.data.add(new Admin(temp.getUser(),temp.getPassword()));
				   else Login.data.add(new User(temp.getUser(), temp.getPassword()));
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
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		//Tells Activity what to do when back key is pressed
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		Intent next = new Intent(Admin_popup.this,AdminActivity.class);
		finish();
		startActivity(next);
		return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_popup, menu);
		return true;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		
		return false;
	}

}
