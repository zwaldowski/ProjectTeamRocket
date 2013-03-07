 package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import java.util.Date;

public class Item {
	
	private String name, description;
	
	private boolean open;
	/**
	 * Using enum for type and category, can always change to 
	 * int later if you guys prefer.
	 */ 
	public enum Type {FOUND, DONATE};
	
	public enum Category {HEIR, KEEPSAKE, MISC};
	
	private Type typ;
	
	private Category cat;
	
	private Date date;
	
	private int reward;
	
	
	
	public Item (String name, int reward) {
		open = true;
		typ = Type.FOUND;
		cat = Category.MISC;
				
		description = "Testing";
		
		this.name = name;
		this.reward = reward;
	}
	
	// Setters
	public void setDescription(String s) {
		description = s;
	}
	
	public void setType(Item.Type i) {
		typ = i;
	}
	
	public void setCat(Item.Category i) {
		cat = i;
	}
	
	public void setDate(Date d) {
		date = d;
	}
	
	public void setReward(int r) {
		reward = r;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public Item.Type getType() {
		return typ;
	}
	
	public Item.Category getCat() {
		return cat;
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getReward() {
		return reward;
	}

}
