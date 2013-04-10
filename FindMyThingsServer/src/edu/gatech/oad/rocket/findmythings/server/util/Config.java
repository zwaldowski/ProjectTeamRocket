package edu.gatech.oad.rocket.findmythings.server.util;

public final class Config {

	public static final String FORGOT_PASSWORD_PARAM = "forgot";
	public static final String TICKET_PARAM = "ticket";

	public static final class Keys {

		private Keys() {}

		public static final String USERNAME = "FMTUsernameParam";
		public static final String PASSWORD = "FMTPasswordParam";
		public static final String REMEMBER_ME = "FMTRememberMeParam";

		public static final String LOGIN_URL = "FMTLoginURL";
		public static final String LOGIN_API_URL = "FMTLoginAPIURL";
		public static final String LOGIN_SUCCESS_URL = "FMTLoginSuccessURL";

	}

	private Config() {}

	public static final String APP_EMAIL = "nataero@rocket-findmythings.appspot.com";

	public static final String LOGIN_URL = "/login";
	public static final String LOGIN_API_URL = "/_ah/api/fmthings/v1/account/login";
	public static final String SUCCESS_URL = "/";

	public static final String USERNAME_PARAM = "email";
	public static final String PASSWORD_PARAM = "password";
	public static final String REMEMBER_ME_PARAM = "rememberMe";

}
