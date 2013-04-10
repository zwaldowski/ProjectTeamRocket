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

	/**
	 * Locks or unlocks the user account
	 * @param locked true if account is locked, false otherwise
	 */
	public abstract void setLocked(boolean locked);

	/**
	 * Escalates the privileges of the user account
	 * @param admin true if account should become admin, false otherwise
	 */
	public abstract void setAdmin(boolean admin);

}
