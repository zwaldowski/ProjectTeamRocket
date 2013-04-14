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

/**
 * CS 2340 - FindMyStuff Android App
 * activity that deals with Admin features' window (lock, create new admin, etc)
 *
 * @author TeamRocket
 * */
public class AdminPopupActivity extends Activity implements OnPreferenceChangeListener {

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

	/**
	 * creates new window with correct layout
	 * @param Bundle savedInstanceState
	 */
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

		// TOOD: reimplement user editing on backend
		/*checkAdmin.setChecked((Login.data.get(id).isAdmin()));
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
				});*/

		setTitle("User Attributes");
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
	 * creates the options menu 
	 * @param Menu menu
	 * @return boolean true when done
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_popup, menu);
		return true;
	}

	/**
	 * change preference
	 * @param Preference preference
	 * @param Object newValue
	 * @return boolean
	 */
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		
		return false;
	}
	
	/**
	 * Called to pop the admin window from the navigation stack
	 */
	@Override 
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_down_modal);
    }

}
