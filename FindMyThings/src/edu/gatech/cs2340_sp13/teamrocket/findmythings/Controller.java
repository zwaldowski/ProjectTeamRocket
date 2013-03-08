package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.gatech.cs2340_sp13.teamrocket.findmythings.dummy.DummyContent.DummyItem;

public class Controller {
	
	public static ArrayList<Item> items = new ArrayList<Item>();
	
	/**
	 * Used to get the description of each item
	 */
	public static Map<String, Item> items_map = new HashMap<String, Item>();
	
	
	public Controller() {
				
	}
	
	/**
	 * Adds a few generic items to the arraylist
	 */
	static {
		Item cat = new Item("Cat",0);
		Item dog = new Item("Dog",0);
		Item catdog = new Item("CatDog",100);
		
		cat.setDescription("moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo ");
		dog.setDescription("oom oom oom oom oom oom oom oom oom oom oom oom ");
		catdog.setDescription("omo omo omo omo omo omo omo omo omo omo omo ");
		Controller c = new Controller();
		c.addItem(cat);
		c.addItem(dog);
		c.addItem(catdog);
	}
	
	

	

	/**
	 * Adds an item to the arraylist
	 * @param i
	 */
	public void addItem(Item i) {
		items.add(i);
		items_map.put(i.getName(),i);
	}
	/**
	 * create a new item (lost, found, donated or requested) 
	 * @param m
	 * @return
	 */
	public Item createItem(Member m) {
		return null;
	}
	
	/**
	 * get info of item
	 * @param i
	 * @return
	 */
	@SuppressWarnings("unused")
	private ArrayList<Item> getInfo(Item i) {
		return null;
	}
	
	/**
	 * get an arrayList of all the items that have the require characteristics
	 * given in the parameter array
	 * @param searches
	 * @return
	 */
	public ArrayList<Item> doSearch(int[] searches) {
		return null;
	}
	
	
	/**
	 * get contact information of a member and give it back to the requester
	 * @param m
	 * @param requester
	 */
	public void getContact (Member m, Member requester) {
		
	}

}

