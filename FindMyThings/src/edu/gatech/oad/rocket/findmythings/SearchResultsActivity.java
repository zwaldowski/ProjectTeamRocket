package edu.gatech.oad.rocket.findmythings;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Basic wrapper activity to show a new item list.
 * User: zw
 * Date: 4/16/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultsActivity extends Activity {

	/**
	 * Initializes the item list fragment using the given intent parameters.
	 * @param savedInstanceState Initialized bundle
	 */
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

	/**
	 * takes care of action when key is pressed down
	 * @param keyCode key that is pressed
	 * @param event - event that is to happen when the key is pressed
	 * @return boolean
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
	 * creates the menu with all the options
	 * @param menu Action bar menu
	 * @return boolean true when done
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_search_results, menu);
		return true;
	}

	/**
	 * Handles menu actions
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.search_results_done:
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Called to pop the search results window from the stack
	 */
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.hold, R.anim.slide_down_modal);
	}
}