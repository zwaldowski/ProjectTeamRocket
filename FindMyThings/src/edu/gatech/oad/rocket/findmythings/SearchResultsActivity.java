package edu.gatech.oad.rocket.findmythings;

import android.app.Activity;
import android.os.Bundle;

/**
 * Basic wrapper activity to show a new item list.
 * User: zw
 * Date: 4/16/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultsActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			// During initial setup, plug in the details fragment.
			ItemListFragment details = new ItemListFragment();
			details.setArguments(getIntent().getExtras());
			getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
		}

		setTitle(getString(R.string.app_name));
	}
}