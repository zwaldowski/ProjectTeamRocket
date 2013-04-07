package edu.gatech.oad.rocket.findmythings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import edu.gatech.oad.rocket.findmythings.control.*;

public class AccountEditActivity extends Activity {

	/**
	 * References to the layout 
	 */
	private EditText mName, mEmail, mPhone, mAddy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account_edit);
		
		setTitle("Edit Account");
		
		mName = (EditText) findViewById(R.id.personnameedit);
		mEmail = (EditText) findViewById(R.id.emailedit);
		mPhone = (EditText) findViewById(R.id.phoneedit);
		mAddy = (EditText) findViewById(R.id.addressedit);
		
		if(Login.currUser!=null) {
			// Display user info
			mEmail.setText(Login.currUser.getUser());
			if(Login.currUser.getName()!=null)
				mName.setText(Login.currUser.getName());
			if(Login.currUser.getAddress()!=null)
				mAddy.setText(Login.currUser.getAddress());
			if(Login.currUser.getPhone()!=null)
				mPhone.setText(Login.currUser.getPhone());
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_account_edit, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.edit_ok:
			Login.currUser.setName(mName.getText().toString());
			Login.currUser.setAddress(mAddy.getText().toString());
			Login.currUser.setPhone(mPhone.getText().toString());
			toMyAccount();
			return true;
		case R.id.edit_cancel:
			toMyAccount();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Goes to MyAccount activity
	 */
	public void toMyAccount() {
		Intent goToNextActivity = new Intent(getApplicationContext(), AccountActivity.class);
		goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			finish();
			startActivity(goToNextActivity);
	}

}
