package edu.gatech.oad.rocket.findmythings.server;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

public class BearerTokenAuthenticationFilter extends AuthenticatingFilter {

	public BearerTokenAuthenticationFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest arg0,
			ServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
