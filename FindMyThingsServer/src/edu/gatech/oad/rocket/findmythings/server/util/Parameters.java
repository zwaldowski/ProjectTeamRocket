package edu.gatech.oad.rocket.findmythings.server.util;

public final class Parameters {
	
	private Parameters() {}
	
	// in forms among others
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String REMEMBER_ME = "rememberMe";
	
	// in request responses
	public static final String STATUS = "status";
	public static final String TOKEN = "token";
	public static final String FAILURE_REASON = "failureReason";
	
	// registration/account editing
	public static final String REALNAME = "name";
	public static final String PASSWORD_CONFIRM = "password_alt";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";
	public static final String FORGOTPASSWORD = "iForgot";
	
	public static final String TICKET = "ticket";

}
