package edu.gatech.oad.rocket.findmythings.control;

import com.google.api.client.util.Base64;
import com.google.api.services.fmthings.model.AppMember;

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
	private AppMember currentUser;
	
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
	
	public void setCurrentUser(AppMember currentUser) {
		this.currentUser = currentUser;
	}
	
	public AppMember getCurrentUser() {
		return currentUser;
	}
	
	public boolean isLoggedIn() {
		return email != null && token != null && email.length() > 0 && token.length() > 0;
	}
	
	public String getAuthorizationHeader() {
		if (!isLoggedIn()) return null;
		byte[] bytes = (email + ":" + token).getBytes();
		return "FMTTOKEN " + Base64.encodeBase64String(bytes);
	}
	
	public void logout() {
		setCurrentEmailAndToken(null, null);
		setCurrentUser(null);
	}

}
