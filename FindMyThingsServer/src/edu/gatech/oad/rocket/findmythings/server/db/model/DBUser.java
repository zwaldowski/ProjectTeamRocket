package edu.gatech.oad.rocket.findmythings.server.db.model;

import java.util.Set;

import com.googlecode.objectify.annotation.EntitySubclass;

import edu.gatech.oad.rocket.findmythings.server.model.AppMutableUser;

/**
 * CS 2340 - FindMyStuff Android App
 * This class creates a new DBUser object
 *
 * @author TeamRocket
 * */
@EntitySubclass
public class DBUser extends DBMember implements AppMutableUser {

    /**
	 *
	 */
	private static final long serialVersionUID = 2321636178121300257L;

	private boolean isLocked;

	/** Constructors **/

    public DBUser(String email, Set<String> roles, Set<String> permissions) {
		super(email, roles, permissions);
	}

	public DBUser(String email, String password, Set<String> roles,
			Set<String> permissions, boolean isRegistered) {
		super(email, password, roles, permissions, isRegistered);
	}

	public DBUser(String email, String password, Set<String> roles,
			Set<String> permissions) {
		super(email, password, roles, permissions);
	}

	public DBUser(String email, String password) {
		super(email, password);
	}

	public DBUser(String email) {
		super(email);
	}

	protected DBUser() {
        super();
    }

	public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
	isLocked = locked;
    }

	public boolean isAdmin() {
		return false;
	}

}
