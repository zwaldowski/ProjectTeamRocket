package edu.gatech.cs2340_sp13.teamrocket.findmythings;

public class Member {
	private String user, name, password;
	private String phone = "";
	//private Location address; 
	public Member(String user, String pass, String phone) {
		this(user,pass);
		if(phone!=null)
		this.phone=phone;
		
	}
	public Member(String user, String pass) {
		this.user = user;
		password = pass;
		
		
	}
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
	 * Sets phone number
	 * @param s phone number
	 */
	public void setPhone(String s) {
		phone = s;
	}
	@Override
	public boolean equals(Object m) {
		if(m instanceof Member)
			if(this.user.equals(((Member) m).getUser()) )
				return true;
		return false;
	}
	
}
