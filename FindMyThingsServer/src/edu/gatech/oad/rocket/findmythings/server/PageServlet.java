package edu.gatech.oad.rocket.findmythings.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.mgt.RealmSecurityManager;

import com.google.inject.Provides;
import com.google.inject.name.Named;

import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.security.ProfileRealm;
import edu.gatech.oad.rocket.findmythings.server.util.Config;
import edu.gatech.oad.rocket.findmythings.server.util.Responses;

public abstract class PageServlet extends HttpServlet {

    @SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(TemplateServlet.class.getName());

    /**
	 *
	 */
	private static final long serialVersionUID = -3019345766517575273L;

	protected PageGenerator generator;

	protected String usernameParam;
	protected String passwordParam;
	protected String rememberMeParam;
	
	public PageServlet() {
		super();
	}
	
	public String getUsernameParam() {
		return usernameParam;
	}
	
	@Inject
	public void setUsernameParam(@Named(Config.Keys.USERNAME) String usernameParam) {
		this.usernameParam = usernameParam;
	}
	
	public String getPasswordParam() {
		return passwordParam;
	}
	
	@Inject
	public void setPasswordParam(@Named(Config.Keys.PASSWORD) String passwordParam) {
		this.passwordParam = passwordParam;
	}
	
	public String getRememberMeParam() {
		return rememberMeParam;
	}
	
	@Inject
	public void setRememberMeParam(@Named(Config.Keys.REMEMBER_ME) String rememberMeParam) {
		this.rememberMeParam = rememberMeParam;
	}

	@Inject
	protected void setGenerator(PageGenerator nGenerator) {
	    generator = nGenerator;
	}

	protected int getIntRequestParam(HttpServletRequest request, String paramName, int defaultValue) {
	    String s = request.getParameter(paramName);
	    return (s == null) ? defaultValue : Integer.parseInt(s);
	}

	protected boolean getBoolRequestParam(HttpServletRequest request, String paramName, boolean defaultValue) {
	    String s = request.getParameter(paramName);
	    return (s == null) ? defaultValue : Boolean.parseBoolean(s);
	}
	
	protected boolean memberExistsWithEmail(String email) {
	    if (email == null || email.length() == 0) return false;
	    RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
	    for (Realm realm : manager.getRealms()) {
			if (realm instanceof ProfileRealm) {
				if (((ProfileRealm) realm).accountExists(email)) return true;
			}
		}
	    return false;
	}
	
	protected AppMember memberWithEmail(String email) {
		if (email == null || email.length() == 0) return null;
		RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
	    for (Realm realm : manager.getRealms()) {
			if (realm instanceof ProfileRealm) {
				AppMember potential = ((ProfileRealm) realm).getAccount(email);
				if (potential != null && potential.getEmail().equals(email)) return potential;
			}
		}

	    return null;
	}

	protected AppMember getCurrentUser() {
	    return memberWithEmail(getCurrentUserEmail());
	}

	protected String getCurrentUserEmail() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
	    if (principals == null || principals.isEmpty()) return null;
	    return (String)principals.getPrimaryPrincipal();
	}

	@Provides
	protected final Map<String,Object> getParameterMap(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        addParametersToMap(request, map);
        return map;
    }

	protected void addParametersToMap(HttpServletRequest request, Map<String, Object> params) {
		String email = getCurrentUserEmail();
		if (email != null) params.put("userEmail", email);
		
		Object failureReason = request.getAttribute(Responses.FAILURE_REASON);
		if (failureReason != null) params.put(Responses.FAILURE_REASON, failureReason);
		
		params.put("usernameParam", getUsernameParam());
		params.put("passwordParam", getPasswordParam());
		params.put("rememberMeParam", getRememberMeParam());
	}

}