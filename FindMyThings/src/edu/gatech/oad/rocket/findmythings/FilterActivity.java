package edu.gatech.oad.rocket.findmythings;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import edu.gatech.oad.rocket.findmythings.NonActivity.Controller;
import edu.gatech.oad.rocket.findmythings.NonActivity.Item;
import android.widget.Button;
import edu.gatech.oad.rocket.findmythings.NonActivity.Type;
import android.content.Intent;
import android.content.Context;


public class FilterActivity extends Activity implements OnItemSelectedListener, TabListener {
	
	/**
	 * References to layout
	 */
	private Spinner mStatus, mCat, mDate;
	private Button filter;
	private Controller cnt = Controller.shared();
	private Type kind = Type.LOST;
	
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
			
		addListenerOnButton(); //filter button
		
		setTitle("Filtering " + "whatevs"/*placeholder*/);
	}
	
	
	/**
	 * listener for button FILTER --> should filter the current list of items by the selected criteria
	 */
	public void addListenerOnButton() {
		mStatus = (Spinner) findViewById(R.id.status_spinner);
		mCat = (Spinner) findViewById(R.id.cat_spinner);
		mDate = (Spinner) findViewById(R.id.date_spinner);
		filter = (Button) findViewById(R.id.filter_button);

		filter.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ArrayList<Item> results = cnt.filter(kind, mCat.getSelectedItemPosition(), mStatus.getSelectedItemPosition(), mDate.getSelectedItemPosition());	
				
				/**trying to create new ItemList with results of filter*/
				//Intent i = new Intent(getActivity(),R.layout.activity_item_list);
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		parent.getItemAtPosition(pos);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
