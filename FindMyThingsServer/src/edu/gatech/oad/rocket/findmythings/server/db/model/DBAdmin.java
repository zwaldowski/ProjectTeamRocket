package edu.gatech.oad.rocket.findmythings.server.db.model;

import java.util.Set;

import com.googlecode.objectify.annotation.EntitySubclass;

import edu.gatech.oad.rocket.findmythings.server.model.AppMutableAdmin;

/**
 * CS 2340 - FindMyStuff Android App
 * This class creates a new DBAdmin object
 *
 * @author TeamRocket
 * */
@EntitySubclass
public class DBAdmin extends DBMember implements AppMutableAdmin {

	/**
	 *
	 */
	private static final long serialVersionUID = 1381561058937653330L;

	/** Constructors **/

    public DBAdmin(String email, Set<String> roles, Set<String> permissions) {
		super(email, roles, permissions);
	}

	public DBAdmin(String email, String password, Set<String> roles,
			Set<String> permissions, boolean isRegistered) {
		super(email, password, roles, permissions, isRegistered);
	}

	public DBAdmin(String email, String password, Set<String> roles,
			Set<String> permissions) {
		super(email, password, roles, permissions);
	}

	public DBAdmin(String email, String password) {
		super(email, password);
	}

	public DBAdmin(String email) {
		super(email);
	}

	protected DBAdmin() {
		super();
	}

	/** Accessors **/

	public final boolean isLocked() {
		return false;
	}

	/**
	 * checks if a certain member is an DBAdmin
	 * @return True
	 */
	@Override
	public final boolean isAdmin() {
		return true;
	}

}
