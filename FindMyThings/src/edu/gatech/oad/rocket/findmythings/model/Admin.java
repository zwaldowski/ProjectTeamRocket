package edu.gatech.oad.rocket.findmythings.model;

import edu.gatech.oad.rocket.findmythings.model.Member;

/**
 * CS 2340 - FindMyStuff Android App
 * This class creates a new Admin object
 *
 * @author TeamRocket
 * */
public class Admin extends Member {

	/**
	 * constructor of new Admin
	 * @param user
	 * @param pass
	 * @param phone
	 */
	public Admin(String user, String pass, String phone) {
		super(user, pass, phone);
		
	}

	/**
	 * constructor of new Admin (only given userID and password)
	 * @param user
	 * @param pass
	 */
	public Admin(String user, String pass) {
		super(user, pass);
		
	}

	/**
	 * checks if a certain member is an Admin
	 * @return True
	 */
	@Override
	public boolean isAdmin() {
		return true;
	}

}
