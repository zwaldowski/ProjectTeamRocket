package edu.gatech.oad.rocket.findmythings.NonActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.gatech.oad.rocket.findmythings.NonActivity.Item;
import edu.gatech.oad.rocket.findmythings.NonActivity.Member;
import edu.gatech.oad.rocket.findmythings.NonActivity.Type;

import android.content.Context;

/**
 * CS 2340 - FindMyStuff Android App
 *
 * Shared data source for Items in the app.
 *
 * Controller follows the Singleton data model. It cannot
 * be initialized more than once.
 *
 * For now, it's a complete dummy source, although in this
 * future it should take care of pulling/caching/updating Items
 * within the database and the server, potentially.
 *
 * @author TeamRocket
 */
public final class Controller {

	private static Controller mSharedInstance;

	/**
	 * Returns the shared singleton instance of
	 */
	public synchronized static Controller shared() {
        if (mSharedInstance == null) {
		mSharedInstance = new Controller();
        }
        return mSharedInstance;
    }

	/**
	 * An internal class used to wrap a representation of
	 * "all items of this type". This utility class
	 * will likely go away in the future.
	 * @author zwaldowski
	 */
	private class ItemsList {

		private ArrayList<Item> mItems = new ArrayList<Item>();

		private Map<String, Item> mItemsMap = new HashMap<String, Item>();

		public void addItem(Item i) {
			mItems.add(i);
			mItemsMap.put(i.getName(),i);
		}

		public Item getItem(Integer key) {
			return mItems.get(key);
		}

		private Item getItem(String key) {
			return mItemsMap.get(key);
		}
	}

	/**
	 * Initialize the shared controller.
	 *
	 * Add a few generic items to the list for each {@link Type}.
	 */
	private Controller() {

		Item cat = new Item("Small bag of sugar",1000);
		Item dog = new Item("Dog",50);
		Item catdog = new Item("Lost Phone",20);
		Item reqStuff = new Item("Food", 0);
		
		cat.setDescription("Small ziplock bag of sugar, very dear to me. Will pay $1,000 upon its return.");
		dog.setDescription("Goes by the name of Snoopy, hates white people.");
		catdog.setDescription("Black Android phone, or maybe Iphone. Might actually be white, definitely a new phone though. $20 payed upon delivery");
		reqStuff.setDescription("NEED FOOD");
		
		cat.setLoc("Colombia");
		dog.setLoc("Santa Rosa, California");
		catdog.setLoc("Detroit, Michigan");
		reqStuff.setLoc("Georgia Institute of Technology");
		
		reqStuff.setType(Type.REQUEST);
		cat.setType(Type.FOUND);
		dog.setType(Type.DONATION);
		catdog.setType(Type.LOST);
		for (Type kind : Type.values()) {
			ItemsList container = new ItemsList();
			allItems.put(kind, container);
		}

		addItem(cat);
		addItem(dog);
		addItem(catdog);
		addItem(reqStuff);
    }

	/**
	 * A map of all Items for all {@link Type}.
	 */
	private Map<Type, ItemsList> allItems = new HashMap<Type, ItemsList>();

	/**
	 * Gets the item list wrapper for a kind of item.
	 * @param kind
	 * @return an ItemsList object
	 */
	private ItemsList getContainer(Type kind) {
		return allItems.get(kind);
	}

	/**
	 * Adds an item to the arraylist
	 * @param i
	 */
	public void addItem(Type kind, Item i) {
		ItemsList container = getContainer(kind);
		container.addItem(i);
	}

	public void addItem(Item i) {
		Type kind = i.getType();
		addItem(kind, i);
	}

	/**
	 * Gets an indexed item from our arraylist
	 * @param key The key of the Item to return
	 */
	public Item getItem(Type kind, Integer key) {
		ItemsList container = getContainer(kind);
		return container.getItem(key);
	}

	/**
	 * Gets an item from our arraylist
	 * @param key The key of the Item to return
	 */
	public Item getItem(Type kind, String key) {
		ItemsList container = getContainer(kind);
		Item temp =  container.getItem(key);
		return temp;
	}

	/**
	 * Creates and returns an array adapter for all items of a certain type
	 * @param context The current context.
	 * @param resource The resource ID for a layout file containing a layout to use when instantiating views.
	 * @param textViewResourceId The id of the TextView within the layout resource to be populated
	 * @param kind The kind of item to query items for
	 * @return An array adapter for the objects of a certain type
	 */
	public Adapter newItemsAdapter(Context context, int resource, int textViewResourceId, Type kind) {
		ItemsList container = getContainer(kind);
		return new Adapter(context, resource, textViewResourceId, container.mItems);
	}
	
	/**
	 * Returns a List of all items of the same type
	 * @param kind
	 * @return
	 */
	public ArrayList<Item> getItem(Type kind) {
		return getContainer(kind).mItems;
	}

	/**
	 * create a new item (lost, found, donated or requested)
	 * @param m
	 * @return Nothing, so far.
	 */
	public Item createItem(Member m) {
		// TODO
		return null;
	}

	/**
	 * get info of item
	 * @param i
	 * @return Nothing so far
	 */
	@SuppressWarnings("unused")
	private ArrayList<Item> getInfo(Item i) {
		// TODO
		return null;
	}

	/**
	 * get an arrayList of all the items that have the require characteristics
	 * given in the parameter array
	 * @param int Category --> 0=all, 1=heirloom, 2=keepsakes, 3=misc
	 * @param int Status --> 0=all, 1=open, 2=closed
	 * @param int Date --> 0=all, 1=yesterday, 2=14days, 3=30days 
	 * @return Nothing, so far.
	 */
	public ArrayList<Item> doSearch(Type kind, int category, int status, int date) {
		ArrayList<Item> all = getItem(kind);
		ArrayList<Item> cat = new ArrayList<Item>();
		ArrayList<Item> stat = new ArrayList<Item>();
		ArrayList<Item> dt = new ArrayList<Item>();
		ArrayList<Item> results = new ArrayList<Item>();
		
		if (category == 0 && status == 0 && date == 0)
			return all;
		
		switch (category) {
		case 0: //all items
			cat = all;
			break;
		case 1: //items that are heirlooms
			for (Item i: all) {
				if (i.getCat() == Category.HEIR) 
					stat.add(i);
			}
			break;
		case 2: //items that are keepsakes
			for (Item i: all) {
				if (i.getCat() == Category.KEEPSAKE) 
					stat.add(i);
			}
			break;
		case 3: //items that are misc
			for (Item i: all) {
				if (i.getCat() == Category.MISC) 
					stat.add(i);
			}
			break;
		}
		
		switch (status) {
		case 0: //all items
			stat = all;
		case 1: //get items that are open
			for (Item i:all) {
				if (i.isOpen())
					stat.add(i);
			}	
			break;
		case 2: //get items that are closed
			for (Item i: all) {
				if (!i.isOpen()) 
					stat.add(i);
			}
			break;
		}
		
		Calendar today = new GregorianCalendar(); //get current date
		Calendar date2 = (Calendar)today.clone(); //will be change to yesterday, 14dayago or 30daysago
		
		switch (date) {
		case 0: //all items
			dt = all;
			break;
		case 1: //items from yesterday onwards
			date2.add(Calendar.DATE, -2); 
			for (Item i: all) {
				if (i.getDate().after(date2.getTime())) 
					stat.add(i);
			}
			break;
		case 2: //items from 14 days ago
			date2.add(Calendar.DATE, -15);
			for (Item i: all) {
				if (i.getDate().after(date2.getTime())) 
					stat.add(i);
			}
			break;
		case 3: //items from 30 days ago
			date2.add(Calendar.DATE, -31);
			for (Item i: all) {
				if (i.getDate().after(date2.getTime()))
					stat.add(i);
			}
			break;
		}
		
		for (Item i : all) {
			if (cat.contains(i) && dt.contains(i) && stat.contains(i))
				results.add(i);
		}
	
		return results;
	}


	/**
	 * get contact information of a member and give it back to the requester
	 * @param m
	 * @param requester
	 */
	public void getContact (Member m, Member requester) {
		// TODO
	}

}
