package edu.gatech.oad.rocket.findmythings;

import java.util.ArrayList;

import edu.gatech.oad.rocket.findmythings.NonActivity.Controller;
import edu.gatech.oad.rocket.findmythings.NonActivity.Item;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Activity that deals with the Search options window...
 * 
 * @author TeamRocket
 *
 */
public class Search_Main extends Activity{
	private int checked;
	private Controller cnt = Controller.shared();
	private Spinner types, status, category, date;
	private TextView name;
	private TextView reward;
	private Button search;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_new);
		//change to activity_search if it doesnt work
		
		
		//addListenerOnButton();
	}
	
	
	//the method that decides which intent to show
	public void addListenerOnButton(View view) {
		name = (TextView)findViewById(R.id.search_name);
		types = (Spinner) findViewById(R.id.search_type);
		status = (Spinner) findViewById(R.id.search_status);
		category = (Spinner) findViewById(R.id.search_category);
		date = (Spinner) findViewById(R.id.search_date_spinner);
		reward = (TextView)findViewById(R.id.search_reward);
		search = (Button) findViewById(R.id.search_button);
		
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int t = types.getSelectedItemPosition();
				int s = status.getSelectedItemPosition();
				int c = category.getSelectedItemPosition();
				int d = date.getSelectedItemPosition();
				
				ArrayList<Item> results = cnt.doSearch(name.getText().toString(), t, c, s, d, Integer.parseInt(reward.getText().toString()));
				// TODO use search results
			}

		});
		
		
		 //cnt
		 
		 switch(checked) {
		 case 0:
			 
			 //go by category
			 break;
		 case 1:
			 //go by status
			 break;
		 case 2:
			 //go by date
			 break;
		 default:
			 
		 }
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	//Tells Activity what to do when back key is pressed
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if(getCallingActivity()==null) { //Goes to Main
	          Intent main = new Intent(getApplicationContext(), MainActivity.class);
	          finish();
	            startActivity(main);
	          overridePendingTransition(R.anim.slide_up_modal, android.R.anim.fade_out);
	          return true;
	        } else { //Goes back to ItemListActivity
	            finish();
	          overridePendingTransition(R.anim.slide_up_modal, android.R.anim.fade_out);
	          return true;
	        }
	      }
	      return super.onKeyDown(keyCode, event);
	   }
}
