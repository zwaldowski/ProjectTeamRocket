package edu.gatech.oad.rocket.findmythings;

import edu.gatech.oad.rocket.findmythings.NonActivity.Controller;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_new);
		//change to activity_search if it doesnt work
		
		
		//addListenerOnButton();
	}
	
	
	//the method that decides which intent to show
	public void showResults(View view) {
		//Intent results = new Intent(getActivity(),results.class);
		 boolean isChecked = ((RadioButton) view).isChecked();
		 
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
