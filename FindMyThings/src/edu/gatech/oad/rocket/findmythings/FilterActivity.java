package edu.gatech.oad.rocket.findmythings;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import edu.gatech.oad.rocket.findmythings.control.Controller;
import edu.gatech.oad.rocket.findmythings.model.Item;
import edu.gatech.oad.rocket.findmythings.model.Type;
import android.content.Intent;

/**
 * CS 2340 - FindMyStuff Android App
 * Activity that takes care of filtering items according to certain criteria
 *
 * @author TeamRocket
 * */
public class FilterActivity extends Activity implements OnItemSelectedListener, TabListener {
	
	/**
	 * References to layout
	 */
	private Spinner mStatus, mCat, mDate;
	private Controller cnt = Controller.shared();
	private Type kind = Type.LOST;
	
	/**
	 * creates new window with correct layout
	 * @param Bundle savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		
		mStatus = (Spinner)findViewById(R.id.status_spinner);
		mStatus.setOnItemSelectedListener(this);
		
		mCat = (Spinner)findViewById(R.id.cat_spinner);
		mCat.setOnItemSelectedListener(this);
		
		mDate = (Spinner)findViewById(R.id.date_spinner);
		mDate.setOnItemSelectedListener(this);
					
		setTitle("Apply Filter");
	}
	
	/**
	 * creates the options menu 
	 * @param Menu menu
	 * @return boolean true when done
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}
	
	/**
	 * deals with action when an options button is selected
	 * @param MenuItem item
	 * @return boolean  
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.filter_ok:
	        	@SuppressWarnings("unused")
				ArrayList<Item> results = cnt.filter(kind, mCat.getSelectedItemPosition(), mStatus.getSelectedItemPosition(), mDate.getSelectedItemPosition());
	        	// TODO: this
				/**trying to create new ItemList with results of filter*/
				//Intent i = new Intent(getActivity(),R.layout.activity_item_list);
	        return true;
			//dostuff
	        case R.id.filter_cancel:
	         	return toParent();
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Goes back to Main when user selects the cancel in the menu
	 * @return true
	 */
	public boolean toParent() {
		Intent toPar = new Intent(getApplicationContext(), MainActivity.class);
		finish();
		startActivity(toPar);
		return true;
	}
	
	/**
	 * deals with action when an item is selected
	 * @param AdapterView<?> parent
	 * @param View view
	 * @param int pos
	 * @param long id 
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		int i  = pos; // Any = 0 , Heirloom = 1, Keepsake = 2, Misc = 3
		System.out.close();
	}

	/**
	 * deals with what to do when nothing is selected
	 * @param AdapterView<?> arg0
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * deals with action when a tab is reselected
	 * @param Tab tab
	 * @param FragmentTransaction ft
	 */
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * deals with action to take when a tab is selected
	 * @param Tab tab
	 * @param FragmentTransaction ft
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * deals with action to take when a tab is not selected
	 * @param Tab tab
	 * @param FragmentTransaction ft
	 */
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
