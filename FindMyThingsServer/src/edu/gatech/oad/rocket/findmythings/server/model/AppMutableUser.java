package edu.gatech.oad.rocket.findmythings.server.model;

public interface AppMutableUser extends AppUser, AppMutableMember {

	/**
	 * Locks or unlocks the user account
	 * @param locked true if account is locked, false otherwise
	 */
	public abstract void setLocked(boolean locked);
	
	public abstract void save();

}
