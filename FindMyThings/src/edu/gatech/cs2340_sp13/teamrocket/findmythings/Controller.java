package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.ArrayAdapter;

import edu.gatech.cs2340_sp13.teamrocket.findmythings.dummy.DummyContent.DummyItem;

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
		
		Item cat = new Item("Cat",0);
		Item dog = new Item("Dog",0);
		Item catdog = new Item("CatDog",100);
		cat.setDescription("moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo ");
		dog.setDescription("oom oom oom oom oom oom oom oom oom oom oom oom ");
		catdog.setDescription("omo omo omo omo omo omo omo omo omo omo omo ");
		
		for (Item.Class kind : Item.Class.values()) {
			ItemsList container = new ItemsList();
			allItems.put(kind, container);
			
			addItem(kind, cat);
			addItem(kind, dog);
			addItem(kind, catdog);
		}
    }
	
	private Map<Item.Class, ItemsList> allItems = new HashMap<Item.Class, ItemsList>();
	
	private ItemsList getContainer(Item.Class kind) {
		return allItems.get(kind);
	}
	
	/**
	 * Adds an item to the arraylist
	 * @param i
	 */
	public void addItem(Item.Class kind, Item i) {
		ItemsList container = getContainer(kind);
		container.addItem(i);
	}
	
	/**
	 * Gets an indexed item from our arraylist
	 * @param key The key of the Item to return
	 */
	public Item getItem(Item.Class kind, Integer key) {
		ItemsList container = getContainer(kind);
		return container.getItem(key);
	}
	
	/**
	 * Gets an item from our arraylist
	 * @param key The key of the Item to return
	 */
	public Item getItem(Item.Class kind, String key) {
		ItemsList container = getContainer(kind);
		return container.getItem(key);
	}
	
	public ArrayAdapter<Item> newItemsAdapter(Context context, int resource, int textViewResourceId, Item.Class kind) {
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
