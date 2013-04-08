package edu.gatech.oad.rocket.findmythings.server.model;

import com.google.appengine.api.datastore.PhoneNumber;

import java.io.Serializable;
import java.util.Collection;

public interface AppMember extends Serializable {

	/**
	 *
	 * @return admin
	 */
	public abstract boolean isAdmin();

	/**
	 *
	 * @return editable
	 */
	public abstract boolean isEditable();

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

	/**
	 *
	 * @return roles
	 */
	public abstract Collection<String> getRoles();

	/**
	 *
	 * @return permissions
	 */
	public abstract Collection<String> getStringPermissions();

}