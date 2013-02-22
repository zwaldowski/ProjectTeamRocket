package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import java.util.ArrayList;




public class Login {
	ArrayList<Member> data = new ArrayList<Member>();
	
	public Login() {
		Member[] template = new Member[4]; //For testing login without the need for registration
			template[0] = new Member("cchu43@gatech.edu","admin","555-555-5555");
			template[1] = new Member("jcole44@gatech.edu","admin","555-555-5555");
			template[2] = new Member("tstowell3@gatech.edu","admin","555-555-5555");
			template[3] = new Member("zwaldowski@gatech.edu ","admin","555-555-5555");
			for(Member m : template)
				data.add(m);
		
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
