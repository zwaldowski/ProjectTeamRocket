package edu.gatech.oad.rocket.findmythings.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Spinner;
import edu.gatech.oad.rocket.findmythings.FilterActivity;
import edu.gatech.oad.rocket.findmythings.LoginActivity;
import edu.gatech.oad.rocket.findmythings.control.Controller;
import edu.gatech.oad.rocket.findmythings.model.Category;
import edu.gatech.oad.rocket.findmythings.model.Item;
import junit.framework.TestCase;

public class testFilter extends ActivityInstrumentationTestCase2<FilterActivity> {

	private FilterActivity activity;
	private Spinner status, category, date;
	private Button ok; 
	
	private ArrayList<Item> results, listItems;
	private Controller cnt = Controller.shared();
	
	public static final int INITIAL_POS = 0;
	public static final int TEST_POS_ONE = 1;
	public static final int TEST_POS_TWO = 2;
	public static final int TEST_POS_THREE = 3;
	
	public testFilter() {
		super("andtest.threads.asynctask", FilterActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		
		status = (Spinner)activity.findViewById(edu.gatech.oad.rocket.findmythings.R.id.status_spinner);
		category = (Spinner)activity.findViewById(edu.gatech.oad.rocket.findmythings.R.id.cat_spinner);
		date = (Spinner)activity.findViewById(edu.gatech.oad.rocket.findmythings.R.id.date_spinner);
		ok = (Button)activity.findViewById(edu.gatech.oad.rocket.findmythings.R.id.filter_ok);
	}
	
	public void testCase() { 
	    // Spinners current position
	    int statusCurrentPos = status.getSelectedItemPosition();
	    int categoryCurrentPos = category.getSelectedItemPosition();
	    int dateCurrentPos = date.getSelectedItemPosition();
	    
	    
	    /** Test Case 0 = checking for restoring state of spinners*/ 
	    // Setting spinners to test pos 2
	    status.setSelection(TEST_POS_TWO);
	    category.setSelection(TEST_POS_TWO);
	    date.setSelection(TEST_POS_TWO);
	    
	    // Stop activity and restart
	    activity.finish();
	    activity = getActivity();
	    
	    // Get spinners positions
	    statusCurrentPos = status.getSelectedItemPosition();
	    categoryCurrentPos = category.getSelectedItemPosition();
	    dateCurrentPos = date.getSelectedItemPosition();
	    
	    // Check that current positions is again initial position
	    assertEquals(INITIAL_POS, statusCurrentPos);
	    assertEquals(INITIAL_POS, categoryCurrentPos);
	    assertEquals(INITIAL_POS, dateCurrentPos);
	    
	    
	    /** Checking for filter results */
	    /** Test Case 2 = Status=ALL, Category=KEEPSAKE, Date=ALL  */
	    
	    // results that should come up when filter is done
	    /*
	    listItems = cnt.getAllItems();
	    for (Item i: listItems) {
	    	if (i.getCat() != Category.KEEPSAKE) 
	    		((ArrayList<Item>)listItems).remove(i);
	    }*/
	    
	    activity.runOnUiThread(
	    		new Runnable() {
	    			public void run() {
	    				status.setSelection(INITIAL_POS);
	    				category.setSelection(TEST_POS_TWO);
	    				date.setSelection(INITIAL_POS);
	    				ok.performClick();
	    			}
	    		});
	    
	    //getting the results after selection and submiting
	    results = activity.toReturn;
	    
	    assertEquals(3, results.size());
	    
	    for (Item i: results) {
	    	assertEquals(Category.KEEPSAKE, i.getCat());
	    }
	   
	    
	}
}


