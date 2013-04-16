package edu.gatech.oad.rocket.findmythings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import java.util.Date;

/**
 * CS 2340 - FindMyStuff Android App
 * Activity that takes care of filtering items according to certain criteria
 *
 * @author TeamRocket
 * */
public class FilterActivity extends Activity implements OnItemSelectedListener {

	public static final int FILTER_REQUEST = 9231;

	/**
	 * References to layout
	 */
	private Spinner mStatus, mCat, mDate;
	
	/**
	 * Stores spinner information as int
	 */
	private Date compareTo;
	private int status, cat, date;

	/**
	 * creates new window with correct layout
	 * @param savedInstanceState Initializing bundle
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
	}

	/**
	 * creates the options menu
	 * @param menu System action bar menu
	 * @return true when done
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}

	/**
	 * deals with action when an options button is selected
	 * @param item selected menu item
	 * @return boolean
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.filter_ok:
				Intent output = new Intent();
				//setFilter(status, cat, date);
				setResult(RESULT_OK, output);
				return true;
	        case R.id.filter_cancel:
				setResult(RESULT_CANCELED);
				finish();
			return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

	public void setFilter(int status, int category, int date) {
		/*ArrayList<Item> filtered = new ArrayList<Item>();
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
		toReturn = filtered;*/
	}
	

	/**
	 * deals with action when an item is selected
	 * @param parent The layout XML spinner causing this result
	 * @param view The view that actually represents the spinner
	 * @param pos The selected value
	 * @param id row ID of selected item
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	switch(parent.getId()) {
		case R.id.status_spinner: //status
			status = pos;
			break;
		case R.id.cat_spinner: //category
			cat = pos; //misc = 3
			break;
		case R.id.date_spinner: //date
			date = pos;
			break;
		}
	}

	/**
	 * deals with what to do when nothing is selected
	 * @param arg0 The layout XML spinner causing this result
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}

	/**
	 * Called to pop the login window from the navigation stack
	 */
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.hold, R.anim.slide_down_modal);
	}

}
