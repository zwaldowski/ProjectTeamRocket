 package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

public class Item {
	
	public enum Type {
		LOST, FOUND, DONATION, REQUEST;
		
		public static final String ID = "item_type";
		
		public static Type forInt(int value) {
			return values()[value];
		}
		
		public String getLocalizedValue(Context context) {
			return context.getResources().getStringArray(R.array.item_type)[ordinal()];
		}
		
		public String getLocalizedDescription(Context context) {
			return context.getResources().getStringArray(R.array.item_type_descriptions)[ordinal()];
		}
		
		public String getListActivityTitle(Context context) {
			return context.getResources().getStringArray(R.array.item_list_titles)[ordinal()];
		}
	}

	public enum Category {
		HEIR, KEEPSAKE, MISC;

		public static final String ID = "item_category";
		
		public static Category forInt(int value) {
			return values()[value];
		}
		
		public String getLocalizedValue(Context context) {
			return context.getResources().getStringArray(R.array.item_category)[ordinal()];
		}

		public String getLocalizedDescription(Context context) {
			return context.getResources().getStringArray(R.array.item_category_descriptions)[ordinal()];
		}
	};
	
	private String name, loc;
	
	private String description = "";
	
	private boolean open;
	
	private Type mType;

	private Category cat;
	
	private Date date;
	
	private int reward;
	
	private SimpleDateFormat mDateFormat;
	
	public Item (String name, int reward) {
		open = true;
		mType = Type.FOUND;
		cat = Category.MISC;
				
		loc = "Harlem";
		
		//Gets todays date - maybe
		
		date = new Date();
		
		
		this.name = name;
		this.reward = reward;
	}
	
	/** ACCESSORS **/
	
	// Setters
	
	public void setDescription(String s) {
		if(s!=null)
			description = s;
	}
	
	public void setLoc(String s) {
		if(s!=null)
			loc=s;
	}
	
	public void setType(Item.Type c) {
		mType = c;
	}
	
	public void setCategory(Item.Category c) {
		cat = c;
	}
	
	public void setDate(Date d) {
		date = d;
	}
	
	public void setReward(int r) {
		reward = r;
	}
	
	// Getters
	
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
		return mType;
	}
	
	public Item.Category getCat() {
		return cat;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getDateString() {
		if (mDateFormat == null) {
			mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
		}
		return mDateFormat.format(date);
	}
	
	public int getReward() {
		return reward;
	}
	
	public String getLoc() {
		return loc;
	}
	
	@Override
	public String toString() {
		String desc = description.length()>30? description.substring(0,30):description.substring(0,description.length());
		return name + " - " + loc + "\n" + desc;
	}

}
