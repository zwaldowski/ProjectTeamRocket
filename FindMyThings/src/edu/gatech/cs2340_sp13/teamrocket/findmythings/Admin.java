package edu.gatech.cs2340_sp13.teamrocket.findmythings;

public class Admin extends Member {

	/**
	 * constructor of new Admin
	 * @param user
	 * @param pass
	 * @param phone
	 */
	public Admin(String user, String pass, String phone) {
		super(user, pass, phone);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor of new Admin (only given userID and password)
	 * @param user
	 * @param pass
	 */
	public Admin(String user, String pass) {
		super(user, pass);
		// TODO Auto-generated constructor stub
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
