package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.ArrayAdapter;

public final class Controller {
	
	private static Controller mSharedInstance;
	
	public synchronized static Controller shared() {
        if (mSharedInstance == null) {
        	mSharedInstance = new Controller();
        }
        return mSharedInstance;
    }
	
	private class ItemsList {
		
		private ArrayList<Item> mItems = new ArrayList<Item>();
		
		private Map<String, Item> mItemsMap = new HashMap<String, Item>();
		
		public void addItem(Item i) {
			mItems.add(i);
			mItemsMap.put(i.getName(),i);
		}
		
		/**
		 * Gets an indexed item from our arraylist
		 * @param key The key of the Item to return
		 */
		public Item getItem(Integer key) {
			return mItems.get(key);
		}
		
		/**
		 * Gets an item from our arraylist
		 * @param key The key of the Item to return
		 */
		private Item getItem(String key) {
			return mItemsMap.get(key);
		}
	}
	
	/**
	 * Adds a few generic items to the arraylist
	 */
	private Controller() {
		
		Item cat = new Item("Small bag of sugar",1000);
		Item dog = new Item("Dog",50);
		Item catdog = new Item("Lost Phone",20);
		cat.setDescription("Small ziplock bag of sugar, very dear to me. Will pay $1,000 upon its return.");
		dog.setDescription("Goes by the name of Snoopy, hates white people.");
		catdog.setDescription("Black Android phone, or maybe Iphone. Might actually be white, definitely a new phone though. $20 payed upon delivery");
		
		for (Item.Type kind : Item.Type.values()) {
			ItemsList container = new ItemsList();
			allItems.put(kind, container);
			
			addItem(kind, cat);
			addItem(kind, dog);
			addItem(kind, catdog);
		}
    }
	
	private Map<Item.Type, ItemsList> allItems = new HashMap<Item.Type, ItemsList>();
	
	private ItemsList getContainer(Item.Type kind) {
		return allItems.get(kind);
	}
	
	/**
	 * Adds an item to the arraylist
	 * @param i
	 */
	public void addItem(Item.Type kind, Item i) {
		ItemsList container = getContainer(kind);
		container.addItem(i);
	}
	
	/**
	 * Gets an indexed item from our arraylist
	 * @param key The key of the Item to return
	 */
	public Item getItem(Item.Type kind, Integer key) {
		ItemsList container = getContainer(kind);
		return container.getItem(key);
	}
	
	/**
	 * Gets an item from our arraylist
	 * @param key The key of the Item to return
	 */
	public Item getItem(Item.Type kind, String key) {
		ItemsList container = getContainer(kind);
		return container.getItem(key);
	}
	
	public ArrayAdapter<Item> newItemsAdapter(Context context, int resource, int textViewResourceId, Item.Type kind) {
		ItemsList container = getContainer(kind);
		return new ArrayAdapter<Item>(context, resource, textViewResourceId, container.mItems);
	}
	
	/**
	 * create a new item (lost, found, donated or requested) 
	 * @param m
	 * @return
	 */
	public Item createItem(Member m) {
		// TODO
		return null;
	}
	
	/**
	 * get info of item
	 * @param i
	 * @return
	 */
	@SuppressWarnings("unused")
	private ArrayList<Item> getInfo(Item i) {
		// TODO
		return null;
	}
	
	/**
	 * get an arrayList of all the items that have the require characteristics
	 * given in the parameter array
	 * @param searches
	 * @return
	 */
	public ArrayList<Item> doSearch(int[] searches) {
		// TODO
		return null;
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

