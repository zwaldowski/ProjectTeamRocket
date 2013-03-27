package edu.gatech.oad.rocket.findmythings.server.util;

public final class Errors {
	
	private Errors() {}
	
	public static final class Login {
		
		private Login() {}
		
		// UnknownAccountAcception
		public final static String OK = "ok";
		
		// UnknownAccountAcception
		public final static String NO_SUCH_USER = "noSuchUser";
		
		// IncorrectCredentialsException
		public final static String BAD_PASSWORD = "badPassword";
		
		// LockedAccountException
		public final static String ACCNT_LOCKED = "accountLocked";
		
		// DisabledAccountException
		public final static String ACCT_DISABLE = "accountDisabled";
        
        // ExcessiveAttemptsException
		public final static String MANY_ATTEMPT = "tooManyAttempts";
        
		// misc. AuthenticationException
		public final static String INVALID_DATA = "invalidData";
        	
	}
	
}
