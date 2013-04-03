package edu.gatech.oad.rocket.findmythings.NonActivity;

import java.util.ArrayList;

import edu.gatech.oad.rocket.findmythings.NonActivity.*;

/* Simple code/logic for searching --> needs to be implemented into the actual app */


public class searchCode {

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
	
	private ArrayList<Item> searchCategory(){
		ArrayList<Item> byCat = new ArrayList<Item>();
		

		
		return null;
	}
	
	private ArrayList<Item> searchStatus(){
		return null;
	}
	
	private ArrayList<Item> searchDate(){
		return null;
	}
	
}
