package edu.gatech.oad.rocket.findmythings.server.model;

public interface AppMutableUser extends AppUser, AppMutableMember {

	/**
	 * set true if account is locked, false otherwise
	 * @param locked
	 */
	public abstract void setLocked(boolean locked);
	
	public abstract void save();

}
