package edu.gatech.oad.rocket.findmythings.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.gatech.oad.rocket.findmythings.model.Category;
import edu.gatech.oad.rocket.findmythings.model.Item;
import edu.gatech.oad.rocket.findmythings.model.Member;
import edu.gatech.oad.rocket.findmythings.model.Type;

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
		
		/**
		 * Adds an item to the List/HashMap
		 * @param i
		 */
		public void addItem(Item i) {
			mItems.add(i);
			mItemsMap.put(i.getName(),i);
		}
		
		/**
		 * Merges two itemlists
		 * @param c
		 */
		public void addAll(ItemsList c) {
			mItems.addAll(c.mItems);
			mItemsMap.putAll(c.mItemsMap);
			
		}
		
		/**
		 * Returns an item with the matching key
		 * @param key
		 * @return
		 */
		public Item getItem(Integer key) {
			return mItems.get(key);
		}
		
		/**
		 * Returns an item with the matching key
		 * @param key
		 * @return
		 */
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
		addDummyItems(); 
    }
	
	/**
	 * adding 
	 */
	public void addDummyItems() {
		Item[] dummy = new Item[8];
		dummy[0] = new Item("Shoes", Type.LOST);
			dummy[0].setCategory(Category.HEIR);
		dummy[1] = new Item("Will to live", Type.LOST);
			dummy[1].setCategory(Category.KEEPSAKE);
		dummy[2] = new Item("Box of gummy bears", Type.FOUND);
			dummy[2].setCategory(Category.KEEPSAKE);
		dummy[3] = new Item("Hot wheels", Type.FOUND);
			dummy[0].setCategory(Category.HEIR);
		dummy[4] = new Item("Socks", Type.DONATION);
			dummy[0].setCategory(Category.KEEPSAKE);
		dummy[5] = new Item("Hair", Type.DONATION);
		dummy[6] = new Item("Water", Type.REQUEST);
		dummy[7] = new Item("Ipad", Type.REQUEST);
		for(int i = 0; i<dummy.length;i++) {
			dummy[i].setLoc("Atlanta, Georgia");
			dummy[i].setDescription(dummy[i].getName()+ "description.");
			addItem(dummy[i]);
		}	
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
		if(kind == null)
			return getContainer();
		return allItems.get(kind);
	}
	
	/**
	 * gets all the existing items
	 * @return ItemList with all items
	 */
	private ItemsList getContainer() {
		ItemsList all = new ItemsList();
		all.addAll(allItems.get(Type.LOST));
		all.addAll(allItems.get(Type.FOUND));
		all.addAll(allItems.get(Type.DONATION));
		all.addAll(allItems.get(Type.REQUEST));
		return all;
	}

	/**
	 * Adds an item to the arraylist
	 * @param i
	 */
	public void addItem(Type kind, Item i) {
		ItemsList container = getContainer(kind);
		container.addItem(i);
	}

	/**
	 * adds an item to list of items
	 * @param Item i to be added
	 */
	public void addItem(Item i) {
		Type kind = i.getType();
		addItem(kind, i);
	}

	/**
	 * Gets an indexed item from our arraylist
	 * @param key The key of the Item to return
	 */
	public Item getItem(Type kind, Integer key) {
		ItemsList container = kind==null? getContainer():getContainer(kind);
		return container.getItem(key);
	}

	/**
	 * Returns all Items 
	 */
	public ArrayList<Item> getAllItems() {
	ArrayList<Item> all = new ArrayList<Item>();
	    all.addAll(getItem(Type.LOST));
	    all.addAll(getItem(Type.FOUND));
	    all.addAll(getItem(Type.DONATION));
	    all.addAll(getItem(Type.REQUEST));
	    return all;
	}
	  
	/**
	 * Gets an item from our arraylist
	 * @param key The key of the Item to return
	 */
	public Item getItem(Type kind, String key) {
		ItemsList container = kind==null? getContainer():getContainer(kind);
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
	public ArrayList<Item> filter(Type kind, int category, int status, int date) {
		ArrayList<Item> all = null;
		if (kind == null){
			all = getAllItems();}
		else {
			all = getItem(kind); }
		
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
	 * get an array of items with the given criteria (can be more specific)
	 * 
	 * @param name 
	 * @param type: All, Found, Lost, Requested, Donated (from 0 to 5)
	 * @param category: Any, Keep, Heir, Misc
	 * @param status: All, open, closed
	 * @param date: all time, yesterday, 14 days, 30 days
	 * @param reward 
	 * 
	 * @return
	 */
	public ArrayList<Item> doSearch(String name, int type, int category, int status, int date, int reward) {
		ArrayList<Item> results = null;
		ArrayList<Item> temp = new ArrayList<Item>();
		
		if (type == 0) { //no name --> create an arrayList with all the items
			results = getAllItems();
		}
		else { //if given a type, get the respective arraylist with that type
			switch(type){
			case 1: 
				results = getItem(Type.FOUND);
				break;
			case 2: 
				results = getItem(Type.LOST);
				break;
			case 3: 
				results = getItem(Type.REQUEST);
				break;
			case 4:
				results = getItem(Type.DONATION);
			}
		}
		
		//look for items with correct category, status, and date
		ArrayList<Item> items2 = filter(null, category, status, date);
		
		//cross check items2 and results from first thing
		for (Item i: items2) {
			if (i.getType() == (results.get(0)).getType())
				temp.add(i);
		}
		results = new ArrayList<Item>(temp);
		temp.clear();
		
		/* results now holds items by type, category, status and date*/
		
		//look for the ones with the correct name
		if (name != null) {
			for (Item i: results) {
				if (i.getName() == name)
					temp.add(i);
			}		
			results = new ArrayList<Item>(temp);
			temp.clear();
		}
		
		/* results now holds items by type, category, status, date and name*/
		if (reward != 0) {
			for (Item i: results) {
				if (i.getReward() == reward)
					temp.add(i);
			}
			results = new ArrayList<Item>(temp);
			temp.clear();
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
