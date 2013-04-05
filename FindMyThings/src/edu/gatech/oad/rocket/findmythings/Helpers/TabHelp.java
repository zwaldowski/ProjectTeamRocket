package edu.gatech.oad.rocket.findmythings.Helpers;

import edu.gatech.oad.rocket.findmythings.ItemListFragment;
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
		if(name.equals("Lost")) {
			if(ItemListFragment.adapter!=null) {
				ItemListFragment.update(control.getItem(Type.LOST));
				MainActivity.mType = Type.LOST;
			}
		}
		if(name.equals("Found")) {
			ItemListFragment.update(control.getItem(Type.FOUND));
			MainActivity.mType = Type.FOUND;			
		}
		if(name.equals("Donations")) {
			ItemListFragment.update(control.getItem(Type.DONATION));
			MainActivity.mType = Type.DONATION;
		}
		if(name.equals("Requests")) {
			ItemListFragment.update(control.getItem(Type.REQUEST));
			MainActivity.mType = Type.REQUEST;
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
