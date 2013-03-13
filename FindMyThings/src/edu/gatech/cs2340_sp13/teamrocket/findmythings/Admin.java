package edu.gatech.cs2340_sp13.teamrocket.findmythings;

public class Admin extends Member {

	public Admin(String user, String pass, String phone) {
		super(user, pass, phone);
		// TODO Auto-generated constructor stub
	}

	public Admin(String user, String pass) {
		super(user, pass);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
