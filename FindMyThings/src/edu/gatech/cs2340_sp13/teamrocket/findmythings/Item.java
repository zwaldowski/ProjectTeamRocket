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
		typ= typ.FOUND;
		cat = cat.MISC;
		
		this.name = name;
		this.reward = reward;
	}
	
	// Setters
	public void setDescription(String s) {
		description = s;
	}
	public void setType(int i) {
		if(i==1)
			typ=typ.FOUND;
		else typ = typ.DONATE;
	}
	public void setCat(int i) {
		if(i==1)
			cat = cat.MISC;
		if(i==2)
			cat = cat.HEIR;
		else cat = cat.KEEPSAKE;
	}
	
	
	

}
