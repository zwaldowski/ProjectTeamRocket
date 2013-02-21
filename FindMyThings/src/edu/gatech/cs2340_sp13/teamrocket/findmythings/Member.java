package edu.gatech.cs2340_sp13.teamrocket.findmythings;

public class Member {
	private String user, name, phone, password;
	//private Location address; 
	public Member(String u, String p) {
		user = u;
		password = p;
		
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
	
}
