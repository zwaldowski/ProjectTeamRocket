package edu.gatech.oad.rocket.findmythings.server.security;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import edu.gatech.oad.rocket.findmythings.server.util.Config;
import edu.gatech.oad.rocket.findmythings.server.util.Messages;
import edu.gatech.oad.rocket.findmythings.server.util.Responses;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

public class WebAuthenticationFilter extends FormAuthenticationFilter {
	private static final Logger LOGGER = Logger.getLogger(WebAuthenticationFilter.class.getName());

	@Override @Inject
	public void setUsernameParam(@Named(Config.Keys.USERNAME) String usernameParam) {
		super.setUsernameParam(usernameParam);
	}

	@Override @Inject
	public void setPasswordParam(@Named(Config.Keys.PASSWORD) String passwordParam) {
		super.setPasswordParam(passwordParam);
	}

	@Override @Inject
	public void setRememberMeParam(@Named(Config.Keys.REMEMBER_ME) String rememberMeParam) {
		super.setRememberMeParam(rememberMeParam);
	}

	@Override @Inject
	public void setLoginUrl(@Named(Config.Keys.LOGIN_URL) String loginUrl) {
		super.setLoginUrl(loginUrl);
	}

	@Override @Inject
	public void setSuccessUrl(@Named(Config.Keys.LOGIN_SUCCESS_URL) String successUrl) {
		super.setSuccessUrl(successUrl);
	}

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		request.setAttribute(Responses.FAILURE_REASON, Messages.Login.getMessage(ae));
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		Subject subject = SecurityUtils.getSubject();
		Session originalSession = subject.getSession();

		Map<Object, Object> attributes = Maps.newLinkedHashMap();
		Collection<Object> keys = originalSession.getAttributeKeys();
		for(Object key : keys) {
			Object value = originalSession.getAttribute(key);
			if (value != null) {
				attributes.put(key, value);
			}
		}
		originalSession.stop();

		try {
			subject.login(token);

			Session newSession = subject.getSession();
			for(Object key : attributes.keySet() ) {
				newSession.setAttribute(key, attributes.get(key));
			}

			LOGGER.fine("Creating a new instance of DatastoreRealm");

			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			LOGGER.fine("Failed log in.");
			setFailureAttribute(request, e);
			return onLoginFailure(token, e, request, response);
		}
	}

}
