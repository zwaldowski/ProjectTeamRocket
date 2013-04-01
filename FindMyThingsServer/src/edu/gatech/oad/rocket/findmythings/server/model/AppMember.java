package edu.gatech.oad.rocket.findmythings.server.model;

import java.io.Serializable;
import java.util.Set;

import com.google.appengine.api.datastore.PhoneNumber;

public interface AppMember extends Serializable {

	/** Definitions **/

	/**
	 *
	 * @return admin
	 */
	public abstract boolean isAdmin();


	/**
	 * returns true if account is locked, false otherwise
	 * @return locked
	 */
	public abstract boolean isLocked();

	/**
	 *
	 * @return registered
	 */
	public abstract boolean isRegistered();

	/** Read-only profile accessors **/

	/**
	 *
	 * @return email
	 */
	public abstract String getEmail();

	/**
	 *
	 * @return name
	 */
	public abstract String getName();

	/**
	 *
	 * @return phone
	 */
	public abstract PhoneNumber getPhone();

	/**
	 *
	 * @return address
	 */
	public abstract String getAddress();

	/** Permissions-related accessors **/

	/**
	 * @return the roles
	 */
	public abstract Set<String> getRoles();

	/**
	 * @return the permissions
	 */
	public abstract Set<String> getPermissions();

}