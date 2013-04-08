package edu.gatech.oad.rocket.findmythings.model;

/**
 * CS 2340 - FindMyStuff Android App
 * This abstract class specifies features of a Member
 *
 * @author TeamRocket
 * */
public abstract class Member {
	protected String user;
	private String name;
	private String password;
	private String address;
	private String phone = "";

	// TODO hash password(s)

	/**
	 * constructor1 to make a new Member
	 * @param user
	 * @param pass
	 * @param phone
	 */
	public Member(String user, String pass, String phone) {
		this(user,pass);
		if(phone!=null)
			this.phone=phone;

	}

	/**
	 * constructor2 to make new Member
	 * @param user
	 * @param pass
	 */
	public Member(String user, String pass) {
		this.user = user.trim();
		password = pass.trim();
	}

	// Design scaffolding

	/**
	 * returns whether member is admin or not
	 * @return boolean
	 */
	public abstract boolean isAdmin();

	/**
	 * returns true if account is locked, false otherwise
	 * @return locked
	 */
	public boolean locked(){
		//TODO: Make this method abstract
		return false;
	}


	//Getters

	/**
	 * returns username
	 * @return user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * returns password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * retuner name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns phone number
	 * @return phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * return address
	 * @return address
	 */
	public String getAddress(){
		return address;
	}

	//Setters


	/**
	 * Sets phone number
	 * @param s phone number
	 */
	public void setPhone(String s) {
		phone = s.trim();
	}

	/**
	 * set address
	 * @param s
	 */
	public void setAddress(String s) {
		address = s.trim();
	}

	/**
	 * set name
	 * @param s
	 */
	public void setName(String s) {
		name = s.trim();
	}

	/* Object methods */

	/**
	 * compares two member Objects
	 * @param Object m
	 * @return true if equal, false otherwise
	 */
	@Override
	public boolean equals(Object m) {
		if(m instanceof Member)
			if(this.user.trim().equals(((Member) m).getUser().trim()) )
				return true;
		return false;
	}

	/**
	 * returns username
	 * @return user
	 */
	@Override
	public String toString() {
		return user;
	}

}
