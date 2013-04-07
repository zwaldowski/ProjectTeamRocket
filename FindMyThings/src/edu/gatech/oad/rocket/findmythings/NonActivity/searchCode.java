package edu.gatech.oad.rocket.findmythings.NonActivity;

import java.util.ArrayList;

/* Simple code/logic for searching --> needs to be implemented into the actual app */


public class searchCode {

	// TODO: Is this the results?
	@SuppressWarnings("unused")
	private Item[] items;
	
	//Category(0), Status(1), Date(2)
	public searchCode(Item[] items, int criteria) {
		this.items = items;
		
		switch(criteria) {
			case 0: {
				break;
			}
			case 1: {
				break;
			}
			case 2: {
				break;
			}
			default: {
				break;
			}
		}
	}

	@SuppressWarnings("unused")
	private ArrayList<Item> searchCategory(){
		// TODO
		ArrayList<Item> byCat = new ArrayList<Item>();
		return null;
	}
	
	@SuppressWarnings("unused")
	private ArrayList<Item> searchStatus(){
		// TODO
		return null;
	}
	
	@SuppressWarnings("unused")
	private ArrayList<Item> searchDate(){
		// TODO
		return null;
	}
	
}
