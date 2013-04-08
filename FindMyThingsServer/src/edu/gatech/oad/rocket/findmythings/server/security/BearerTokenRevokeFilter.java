package edu.gatech.oad.rocket.findmythings.server.security;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BearerTokenRevokeFilter extends LogoutFilter {
	private static final Logger LOGGER = Logger.getLogger(BearerTokenRevokeFilter.class.getName());

	public BearerTokenRevokeFilter() {
		super();
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		try {
			subject.logout();
		} catch (SessionException ise) {
			LOGGER.log(Level.FINER, "Encountered session exception during logout.  This can generally safely be ignored.", ise);
		}
		return true;
	}

}
