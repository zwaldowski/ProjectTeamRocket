package edu.gatech.oad.rocket.findmythings.util;

import edu.gatech.oad.rocket.findmythings.MainActivity;
import edu.gatech.oad.rocket.findmythings.control.Controller;
import edu.gatech.oad.rocket.findmythings.model.Type;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;

/**
 * CS 2340 - FindMyStuff Android App
 * deals with actions of the tabbed menu
 *
 * @author TeamRocket
 * */
public class MainTabListener implements TabListener{
	
	private String name;
	
	private Controller control = Controller.shared();
	
	/**
	 * constructor
	 * @param name
	 */
	public MainTabListener(String name) {
		this.name = name;
	}
	
	/**
	 * deals with action when a tab is reselected
	 * @param Tab tab
	 * @param FragmentTransaction ft
	 */
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub	
	}

	/**
	 * deals with action to take when a tab is selected
	 * @param Tab tab
	 * @param FragmentTransaction ft
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(name.equals("ALL")) {
			if(MainActivity.adapter!=null) {
				MainActivity.update(control.getAllItems(), null);
			}
		}
		if(name.equals("LOST")) {
			if(MainActivity.adapter!=null) {
				MainActivity.update(control.getItem(Type.LOST), Type.LOST);
			}
		}
		else if(name.equals("FOUND")) {
			MainActivity.update(control.getItem(Type.FOUND), Type.FOUND);			
		}
		else if(name.equals("DONATIONS")) {
			MainActivity.update(control.getItem(Type.DONATION), Type.DONATION);
		}
		else if(name.equals("REQUESTS")) {
			MainActivity.update(control.getItem(Type.REQUEST), Type.REQUEST);
		}
		
	}

	/**
	 * deals with action to take when a tab is not selected
	 * @param Tab tab
	 * @param FragmentTransaction ft
	 */
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
