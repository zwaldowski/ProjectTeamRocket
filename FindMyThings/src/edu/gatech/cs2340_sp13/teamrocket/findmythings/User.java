package edu.gatech.cs2340_sp13.teamrocket.findmythings;

public class User extends Member {

	/**
	 * Whether or not a user has exceeded their login attempts.
	 */
	private boolean locked = false;
	
	/**
	 * The number of login attempts.
	 */
	private int attempts = 0;

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
	
	/**
	 * Increments the number of unsuccessful attempts a User has made to log in
	 */
	public void incrment() {
		attempts++;
		
		if(attempts==3)
			setLock(true);
	}
	
	/**
	 * 
	 * @param i
	 */
	public void setAttempts(int i) {
		attempts=i;
		if(attempts==3)
			setLock(true);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAttempts() {
		return attempts;
	}
	
}
