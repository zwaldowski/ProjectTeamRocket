package edu.gatech.oad.rocket.findmythings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

import edu.gatech.oad.rocket.findmythings.model.Category;
import edu.gatech.oad.rocket.findmythings.model.Item;
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
	
	/**
	 * Stores spinner information as int
	 */
	private int status, cat, date;
	
	/**
	 * Stores the filtered values because tablisteners are jerks
	 */
	public static ArrayList<Item> toReturn;
	
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
	        	setFilter(status, cat, date);
				return toParent();
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
		Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
			goToNextActivity.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
		    goToNextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		finish();
	    startActivity(goToNextActivity);
		overridePendingTransition(R.anim.hold, R.anim.slide_down_modal);
		   return true;
	}
	
	public void setFilter(int status, int category, int date) {
		ArrayList<Item> filtered = new ArrayList<Item>();
		//getting items by date
		Calendar today = new GregorianCalendar(); //get current date
		Calendar date2 = (Calendar)today.clone(); //will be change to yesterday, 14dayago or 30daysago
		
		Calendar toComp = Calendar.getInstance();
		
		boolean open = status==1? true:false;
		Category categor = null;
		
		switch(date) {
		case 1: 
			date2.add(Calendar.DATE, -2);
			break;
		case 2: 
			date2.add(Calendar.DATE, -15);
			break;
		case 3:
			date2.add(Calendar.DATE, -31);
			break;
		}
		
		switch(category) {
		case 1:
			categor = Category.HEIR;
			break;
		case 2: 
			categor = Category.KEEPSAKE;
			break;
		case 3:
			categor = Category.MISC;
			break;
			}
		ArrayList<Item> current = MainActivity.currList;
		for(Item temp : current) {
			toComp.setTime(temp.getDate());
			if(categor!=null && status!=0) {
				if(date!=0) {
					if(toComp.after(date2) && temp.getCat()==categor && temp.isOpen()==open)
						filtered.add(temp);
				}
				else {
					if(temp.getCat()==categor && temp.isOpen()==open)
						filtered.add(temp);
				}
			}
			else if(status==0 && categor!=null) {
				if(date!=0) {
					if(toComp.after(date2) && temp.getCat()==categor)
						filtered.add(temp);
				}
				else {
					if(temp.getCat()==categor)
						filtered.add(temp);
				}
			}
			else if(status==0 && categor==null) {
				if(date!=0) {
					if(toComp.after(date2))
						filtered.add(temp);
				} else {
						filtered.add(temp);
				}
			}
			
			
		} //end of for
		toReturn = filtered;
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
	//	int i  = parent.getId();
		switch(parent.getId()) {
		case 2131099663: //status
			status = pos;
			break;
		case 2131099665: //category
			cat = pos; //misc = 3
			break;
		case 2131099667: //date
			date = pos;
			break;
		}
		
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
