package edu.gatech.oad.rocket.findmythings.control;

import android.content.SharedPreferences;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Base64;
import edu.gatech.oad.rocket.findmythings.model.AppMember;

import java.io.IOException;
import java.io.StringWriter;

public class LoginManager {
	
	private static final String LOGIN_PREFS_DOMAIN = "fmtauth";
	private final SharedPreferences prefs; 

	private LoginManager() {
		prefs = SharedApplication.getInstance().getSharedPreferences(LOGIN_PREFS_DOMAIN, 0);
		email = prefs.getString("email", null);
		token = prefs.getString("token", null);
		String appmemberString = prefs.getString("currentUser", null);
		if (appmemberString != null) {
			try {
				currentUser = new JacksonFactory().createJsonParser(appmemberString).parseAndClose(AppMember.class, null);
			} catch (IOException e) {
				currentUser = null;
			}
		}
		this.currentUserCouldBeOutOfDate = true;
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
	private boolean currentUserCouldBeOutOfDate;
	
	public void setCurrentEmailAndToken(String email, String token) {
		this.email = email;
		this.token = token;
		this.currentUserCouldBeOutOfDate = true;
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
		this.currentUserCouldBeOutOfDate = false;

		SharedPreferences.Editor edit = prefs.edit();
		if (this.currentUser != null) {
			try {
				StringWriter writer = new StringWriter();
				JsonGenerator gen = new JacksonFactory().createJsonGenerator(writer);
				gen.serialize(currentUser);
				gen.close();
				String stringValue = writer.toString();
				edit.putString("currentUser", stringValue);
			} catch (IOException e) {
				edit.remove("currentUser");
			}
		}

		edit.commit();
	}
	
	public AppMember getCurrentUser() {
		return currentUser;
	}
	
	public boolean currentUserCouldBeOutOfDate() {
		return currentUserCouldBeOutOfDate;
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
		this.currentUserCouldBeOutOfDate = false;
	}

}
