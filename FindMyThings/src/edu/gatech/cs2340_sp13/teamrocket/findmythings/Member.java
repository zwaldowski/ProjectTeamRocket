package edu.gatech.cs2340_sp13.teamrocket.findmythings;

public abstract class Member {
	protected String user;
	private String name;
	private String password;
	private String address;
	private String phone = "";
	
	// TODO hash password(s)

	public Member(String user, String pass, String phone) {
		this(user,pass);
		if(phone!=null)
		this.phone=phone;
		
	}

	public Member(String user, String pass) {
		this.user = user;
		password = pass;
	}
	
	// Design scaffolding
	
	
	public abstract boolean isAdmin();
	
	/**
	 * returns true if account is locked, false otherwise
	 * @return locked
	 */
	public boolean locked(){
		return false;
	}

	
	//Getters
	
	/**
	 * 
	 * @return user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 
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
		phone = s;
	}

	/**
	 * set address
	 * @param s
	 */
	public void setAddress(String s) {
		address=s;
	}

	/**
	 * set name
	 * @param s
	 */
	public void setName(String s) {
		name = s;
	}

	/* Object methods */
	
	@Override
	public boolean equals(Object m) {
		if(m instanceof Member)
			if(this.user.trim().equals(((Member) m).getUser().trim()) )
				return true;
		return false;
	}
	
	@Override
	public String toString() {
		return user;
	}

	
}
