package edu.gatech.oad.rocket.findmythings;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

public class FilterActivity extends Activity implements OnItemSelectedListener {
	
	/**
	 * References to layout
	 */
	private Spinner mStatus, mCat, mDate;
	
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

}
