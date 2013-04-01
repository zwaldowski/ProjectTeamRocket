package edu.gatech.oad.rocket.findmythings.server.model;

import com.google.appengine.api.datastore.PhoneNumber;

public interface AppMutableMember extends AppMember {

	/**
	 * set name
	 * @param s
	 */
	public abstract void setName(String s);

	public abstract void setPhone(PhoneNumber p);

	/**
	 * set address
	 * @param s
	 */
	public abstract void setAddress(String s);

}
