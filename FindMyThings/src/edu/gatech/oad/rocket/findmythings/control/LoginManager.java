package edu.gatech.oad.rocket.findmythings.control;

import android.content.SharedPreferences;

public class LoginManager {
	
	private static final String LOGIN_PREFS_DOMAIN = "fmtauth";
	private final SharedPreferences prefs; 

	private LoginManager() {
		prefs = SharedApplication.getInstance().getSharedPreferences(LOGIN_PREFS_DOMAIN, 0);
		email = prefs.getString("email", null);
		token = prefs.getString("token", null);
	}
	
	private static final class LoginManagerSingleton {
		static final LoginManager mgr = new LoginManager();
	}

	public static LoginManager getLoginManager() {
		return LoginManagerSingleton.mgr;
	}
	
	private String email;
	private String token;
	
	public void setCurrentEmailAndToken(String email, String token) {
		this.email = email;
		this.token = token;
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("email", this.email);
		edit.putString("token", this.token);
		edit.commit();
	}
	
	public String getCurrentEmail() {
		return email;
	}
	
	public String getCurrentToken() {
		return token;
	}
	
	public boolean isLoggedIn() {
		return email != null && token != null && email.length() > 0 && token.length() > 0;
	}

}
