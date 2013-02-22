package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import java.util.ArrayList;




public class Login {
	ArrayList<Member> data = new ArrayList<Member>();
	public Login() {
		
	}
	/**
	 * verify a user
	 * @param m Member to be verified
	 * @return whether or not the member has valid credentials
	 */
	public boolean verifyUser(Member m) {
		boolean found = false;
		if(data.contains(m))
			found = true;
		return found;
	}
	/**
	 * In the future, will create a new member, I'm lazy so as of now it just adds the user to a text file to test registration
	 * @param name user name
	 * @param password user pass
	 */
	public Member register(String name, String password) {
		Member temp = new Member(name, password);
		data.add(temp);

		return temp;		
	}

}
