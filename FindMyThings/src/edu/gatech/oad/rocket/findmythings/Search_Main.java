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
	private RadioGroup searches;
	private RadioButton categoryButton, statusButton, dateButton;
	private TextView textView1;
	private Button nextButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		addListenerOnButton();
	}

	private void addListenerOnButton() {
		searches = (RadioGroup)findViewById(R.id.searches);
		categoryButton = (RadioButton)findViewById(R.id.categoryButton);
		statusButton = (RadioButton)findViewById(R.id.statusButton);
		dateButton = (RadioButton)findViewById(R.id.dateButton);
		textView1 = (TextView)findViewById(R.id.textView1);
		nextButton = (Button)findViewById(R.id.nextButton); 
		categoryButton.setChecked(true);
		
		nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.categoryButton:
					//should search by category
					break;
				case R.id.statusButton:
					//should search by status
					break;
				case R.id.dateButton:
					//should search by date
					break; 
				default:
					break;
				} 
			}
		});
	}
}