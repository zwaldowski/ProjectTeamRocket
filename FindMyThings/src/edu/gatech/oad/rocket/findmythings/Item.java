package edu.gatech.oad.rocket.findmythings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * CS 2340 - FindMyStuff Android App
 * This class creates a new Item
 *
 * @author TeamRocket
 * */
public class Item {

	/**
	 * A key suitable for putting a Category into a {@link android.os.Bundle}, {@link android.content.Intent}, etc.
	 * For now, we use this with the name String - probably should be some kind of UUID later on.
	 */
	public static final String ID = "item_id";

	/**
	 * Basic string descriptors.
	 */
	private String name, loc = "", description = "";

	/**
	 * Whether the item is being displayed.
	 */
	private boolean open = true;

	/**
	 * The type of an Item. Defines which list it goes on.
	 */
	private Type mType = Type.FOUND;

	/**
	 * The category of an Item. Used for filtering.
	 */
	private Category cat = Category.MISC;

	/**
	 * The used-defined date.
	 */
	private Date date = new Date();

	/**
	 * The user-defined reward (used only for Lost Type).
	 */
	private int reward;

	/**
	 * Internal utility for formatting dates.
	 */
	private SimpleDateFormat mDateFormat;

	/**
	 * Initializes an Item with some given values.
	 * @param iName A short description of the Item
	 * @param iReward A reward for the Lost item.
	 */
	public Item (String iName, int iReward) {
		name = iName;
		reward = iReward;
	}

	/** ACCESSORS **/

	// Setters

	/**
	 * Sets the description of the Item, but only
	 * if it's not null
	 * @param s A non-null string
	 */
	public void setDescription(String s) {
		if(s!=null)
			description = s.trim();
	}

	/**
	 * Sets the location of the Item, but only
	 * if it's not null.
	 * @param s A non-null string
	 */
	public void setLoc(String s) {
		if(s!=null)
			loc=s.trim();
	}

	/**
	 * Sets the type of an Item
	 * @param c An enumerated Type value.
	 */
	public void setType(Type c) {
		mType = c;
	}

	/**
	 * Sets the category of an Item
	 * @param c An enumerated Category value.
	 */
	public void setCategory(Category c) {
		cat = c;
	}

	/**
	 * Sets the date of an Item
	 * @param d A date object
	 */
	public void setDate(Date d) {
		date = d;
	}

	/**
	 * Sets the reward of a Lost Item
	 * @param r An integer primitive.
	 */
	public void setReward(int r) {
		reward = r;
	}

	// Getters

	/**
	 * The name value of an Item. Read-only.
	 * @return A user-input string.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The short description value of an Item.
	 * @return A user-input string. Editable.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Very short description to be displayed on the ItemList
	 * @return A truncated description string
	 */
	public String getSummary() {
		String desc = description.length()>40? description.substring(0,40):description.substring(0,description.length());
		return "\n" + desc + "...";
	}

	/**
	 * Returns whether or not the item is being displayed.
	 * @return A boolean
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Returns the type of the item, i.e., which list it is displayed on.
	 * @return A Type enumerated value.
	 */
	public Type getType() {
		return mType;
	}

	/**
	 * Returns the category of the item.
	 * @return A Category enumerated value.
	 */
	public Category getCat() {
		return cat;
	}

	/**
	 * A user-input date for the Item.
	 * @return A date object.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * A formatted date string for the user-input date.
	 * @return An string formatted to represent the getDate();
	 */
	public String getDateString() {
		if (mDateFormat == null) {
			mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
		}
		return mDateFormat.format(date);
	}

	/**
	 * A monetary compensation for a lost Item.
	 * @return An integer representing the user-input reward
	 * of the receiving item.
	 */
	public int getReward() {
		return reward;
	}

	/**
	 * The location of an item, like an address or coordinates.
	 * @return A string representing the user-input location
	 * of the receiving item.
	 */
	public String getLoc() {
		return loc;
	}

	@Override
	public String toString() {

		return name ;
	}

}
