package edu.gatech.oad.rocket.findmythings.server.model;

import com.google.appengine.api.datastore.PhoneNumber;

public interface AppMutableMember extends AppMember {

	/**
	 * set name
	 * @param s The given name (first/last) of the member
	 */
	public abstract void setName(String s);

	/**
	 * set phone number
	 * @param p The primary phone number of the member
	 */
	public abstract void setPhone(PhoneNumber p);

	/**
	 * set address
	 * @param s The location/neighborhood of the member
	 */
	public abstract void setAddress(String s);

}
