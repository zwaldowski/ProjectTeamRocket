package edu.gatech.cs2340_sp13.teamrocket.findmythings;

public class User extends Member {

	private boolean locked = false;
	public User(String user, String pass, String phone) {
		super(user, pass, phone);
		// TODO Auto-generated constructor stub
	}

	public User(String user, String pass) {
		super(user, pass);
		// TODO Auto-generated constructor stub
	}
	/**
	 * returns true if account is locked, false otherwise
	 * @return locked
	 */
	public boolean locked(){
		return locked;
	}
	/**
	 * locks or unlocks user account
	 * @param b 
	 */
	public void setLock(boolean b) {
		locked = b;
	}
	@Override
	public boolean equals(Object m) {
		if(m instanceof User)
			if(this.user.equals(((User) m).getUser()) )
				return true;
		return false;
	}

}
