package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Submit extends Activity {
	
	//UI references
	private EditText description;
	private EditText location;
	private EditText reward;
	private EditText iName;
	
	//Hold strings from the UI
	private String desc, loc, name;
	private int rward;
	
	private Controller control = Controller.shared();
	
	private Item.Type mType = Item.Type.Lost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
		
		//References the layout in activity_submit
		iName = (EditText) findViewById(R.id.name);
		description = (EditText) findViewById(R.id.description);
		location = (EditText) findViewById(R.id.locationtext);
		reward = (EditText) findViewById(R.id.rewardtext);
		
		Bundle extraInfo = getIntent().getExtras();
		if (extraInfo != null && extraInfo.containsKey(Item.Type.ID)) {
			mType = Item.Type.forInt(extraInfo.getInt(Item.Type.ID));
		}
		
		// Hide the Up button in the action bar.
		setupActionBar();
		
		setTitle("Submit an Item");
		
		SubmitFrag frag = (SubmitFrag) getFragmentManager().findFragmentById(R.id.submit_fragment);
		frag.syncTypePref(mType);
	}
		
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		//Tells Activity what to do when back key is pressed
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	return false;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * Goes to ItemList Activity
	 */
	public void toItemList() {
		Intent goToNextActivity = new Intent(getApplicationContext(), ItemListActivity.class);
		goToNextActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		goToNextActivity.putExtra(getString(R.string.key_nooverride_animation), true);
		goToNextActivity.putExtra(Item.Type.ID, mType.ordinal());
		finish();
		startActivity(goToNextActivity);
		overridePendingTransition(R.anim.hold, R.anim.slide_down_modal);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		boolean cancel = false;
		View focusView = null;
		
		desc = description.getText().toString();
		name = iName.getText().toString();
		
		//Check to see if name is empty
		if (TextUtils.isEmpty(name.trim())) {
			iName.setError(getString(R.string.error_field_required));
			focusView = iName;
			cancel = true;
		}
		
		//Check to see if description is empty
		if (TextUtils.isEmpty(desc.trim())) {
			description.setError(getString(R.string.error_field_required));
			focusView = description;
			cancel = true;
		} 
		
		
		if (cancel) //There was an error
		focusView.requestFocus();
		
		else {
			switch (item.getItemId()) {
			case R.id.submit_ok:
	 		
				loc = location.getText().toString();
				rward = reward.getText().length() == 0 ? 0:Integer.parseInt(reward.getText().toString());
	 		
				Item temp = new Item(name,rward);
				temp.setCat(SubmitFrag.cat);
				temp.setDescription(desc);
				temp.setLoc(loc);
				
				control.addItem(mType, temp);
				toItemList();
				
				return true;
			case R.id.submit_cancel:
				toItemList();
				return true;
			case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			
			return true;	
			}	
		
		}	
			return super.onOptionsItemSelected(item);
		
	}
	
	public void setItemType(Item.Type type) {
		mType = type;
	}
	
	public Item.Type getItemType() {
		return mType;
	}

}
