package edu.gatech.oad.rocket.findmythings.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.mgt.RealmSecurityManager;

import com.google.inject.Provides;

import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.security.ProfileRealm;
import edu.gatech.oad.rocket.findmythings.server.web.PageGenerator;

public abstract class BaseServlet extends HttpServlet {

    @SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(TemplateServlet.class.getName());

    /**
	 *
	 */
	private static final long serialVersionUID = -3019345766517575273L;

	protected PageGenerator generator;

	public BaseServlet() {
		super();
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

	protected AppMember getCurrentUser() {
		RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
	    if (principals == null || principals.isEmpty()) return null;
	    String email = (String)principals.getPrimaryPrincipal();
	    if (email == null || email.length() == 0) return null;

	    for (Realm realm : manager.getRealms()) {
			if (realm instanceof ProfileRealm) {
				AppMember potential = ((ProfileRealm) realm).getAccount(email);
				if (potential != null && potential.getEmail().equals(email)) return potential;
			}
		}

	    return null;
	}

	protected String getCurrentUserEmail() {
	    return (String)SecurityUtils.getSubject().getPrincipal();
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
	}

}