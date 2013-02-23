package edu.gatech.cs2340_sp13.teamrocket.findmythings;


import java.util.ArrayList;


public class Login {
	
	/**
	 * Static ArrayList to keep user information consistent even after you leave the login screen 
	 * and come back. Probably a better way to accomplish this, but it's 1am right now so fuck it.
	 */
	private static ArrayList<Member> data = new ArrayList<Member>();
	
	
	public Login() {
		Member[] template = new Member[4]; //For testing login without the need for registration
			template[0] = new User("cchu43@gatech.edu","admin","555-555-5555");
			template[1] = new User("jcole44@gatech.edu","admin","555-555-5555");
			template[2] = new User("tstowell3@gatech.edu","admin","555-555-5555");
			template[3] = new User("zwaldowski@gatech.edu ","admin","555-555-5555");
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
		int index = data.indexOf(m);
		if(index!=-1) {
			if(m instanceof User && ((User)m).locked())
				((User)data.get(index)).setLock(true);
			else if(data.get(index).getPassword().equals(m.getPassword())) 
				found = true;
			
						
			
		}
		return found;
	}
	/**
	 * Checks whether or not the user account exists
	 * @param m Member 
	 * @return true is username has been registered
	 */
	public boolean exists(Member m) {
		return data.contains(m);
	}
	/**
	 * adds new member to arraylist
	 * @param name user name
	 * @param password user pass
	 */
	public Member register(String name, String password) {
		Member temp = new Member(name, password);
		data.add(temp);

		return temp;		
	}
	/**
	 * Updates users locked status
	 * @param m
	 */
	public User update(User m) {
		if(exists(m))
			m.setLock(((User) data.get(data.indexOf(m))).locked());
		return m;
	}
	
	/**
	 * Locks user account in the arraylist
	 * @param m
	 */
	public void lock(User m) {
		int justincase = data.indexOf(m);
		if(justincase!=-1)
			((User) data.get(justincase)).setLock(true);
	}

}
