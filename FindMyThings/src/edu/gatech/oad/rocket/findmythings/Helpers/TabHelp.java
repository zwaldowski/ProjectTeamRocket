package edu.gatech.oad.rocket.findmythings.Helpers;

import edu.gatech.oad.rocket.findmythings.MainActivity;
import edu.gatech.oad.rocket.findmythings.NonActivity.Controller;
import edu.gatech.oad.rocket.findmythings.NonActivity.Type;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;

public class TabHelp implements TabListener{
	
	private String name;
	
	private Controller control = Controller.shared();
	
	public TabHelp(String name) {
		this.name = name;
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(name.equals("ALL")) {
			if(MainActivity.adapter!=null) {
				MainActivity.update(control.getAllItems(), null);
				MainActivity.mType = null;
			}
		}
		if(name.equals("LOST")) {
			if(MainActivity.adapter!=null) {
				MainActivity.update(control.getItem(Type.LOST), Type.LOST);
				MainActivity.mType = Type.LOST;
			}
		}
		else if(name.equals("FOUND")) {
			MainActivity.update(control.getItem(Type.FOUND), Type.FOUND);
			MainActivity.mType = Type.FOUND;			
		}
		else if(name.equals("DONATIONS")) {
			MainActivity.update(control.getItem(Type.DONATION), Type.DONATION);
			MainActivity.mType = Type.DONATION;
		}
		else if(name.equals("REQUESTS")) {
			MainActivity.update(control.getItem(Type.REQUEST), Type.REQUEST);
			MainActivity.mType = Type.REQUEST;
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
