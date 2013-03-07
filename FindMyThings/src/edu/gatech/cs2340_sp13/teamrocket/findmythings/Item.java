 package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.DateFormat;

public class Item {
	
	private String name, loc;
	
	private String description = "";
	
	
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
				
		loc = "The streets";
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
		dateFormat.format(date);
		
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
	
	public String getCatString() {
		if(cat== Category.HEIR)
			return "Heirloom";
		if(cat==Category.KEEPSAKE)
			return "Keepsake";
		return "Miscellaneous"; 
		
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getReward() {
		return reward;
	}
	
	public String getLoc() {
		return loc;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
