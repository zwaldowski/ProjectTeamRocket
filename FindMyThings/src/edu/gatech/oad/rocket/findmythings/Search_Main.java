package edu.gatech.oad.rocket.findmythings;

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
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		//addListenerOnButton();
	}

	//the method that decides which intent to show
	public void showResults(View view) {
		//Intent results = new Intent(getActivity(),results.class);
		 boolean isChecked = ((RadioButton) view).isChecked();
		 
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
	
	public void onClick(View view) {
		 switch (view.getId()) {
		 case R.id.categoryButton:
			 checked = 0;
			 break;
		 case R.id.statusButton:
			 checked = 1;
			 break;
		 case R.id.dateButton:
			 checked = 2;
			 break; 
		 default:
			 break;
		 }
	}
}
