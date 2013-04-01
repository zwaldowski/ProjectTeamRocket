package edu.gatech.oad.rocket.findmythings.server.security;

import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import edu.gatech.oad.rocket.findmythings.server.util.*;

public class BearerTokenAuthenticatingFilter extends AuthenticatingFilter {
	
	protected static final String AUTHORIZATION_HEADER = "Authorization";
	protected static final String AUTHORIZATION_SCHEME = "FMTTOKEN";
	protected static final String AUTHORIZATION_SCHEME_ALT = "Basic";

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(BearerTokenAuthenticatingFilter.class.getName());

    private String usernameParam = Parameters.USERNAME;
    private String passwordParam = Parameters.PASSWORD;

	public BearerTokenAuthenticatingFilter() {
		setLoginUrl("/api/login.jsp");
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			String username = getUsername(request);
	        String password = getPassword(request);
	        return createToken(username, password, request, response);
		} else if (hasAuthorizationToken(request, response)) {
			String authorizationHeader = getAuthorizationHeader(request);
	        if (authorizationHeader == null || authorizationHeader.length() == 0) {
	            return null;
	        }
	        
	        String[] prinCred = getPrincipalsAndCredentials(authorizationHeader, request);
	        if (prinCred == null || prinCred.length < 2) {
	            return null;
	        }

	        String username = prinCred[0];
	        String token = prinCred[1];

	        return new BearerToken(username, token);
		}
		return null;
	}
	
	private boolean stopWithNoToken(ServletResponse response) {
		HTTP.writeAsJSON(response, HTTP.Status.UNAUTHORIZED,
				Parameters.STATUS, Messages.Status.UNAUTHORIZED.toString(),
				Parameters.FAILURE_REASON, Messages.Permissions.REQUIRES_LOGIN.toString());
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		boolean authTokenized = hasAuthorizationToken(request, response); 
		boolean isLogin = isLoginRequest(request, response);
		if (authTokenized || isLogin) {
			return executeLogin(request, response);
		} else {
			return stopWithNoToken(response);
		}
	}
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			String email = (String)subject.getPrincipal();
			String newToken = BearerTokenAuthenticatingRealm.createNewToken(email);
			HTTP.writeAsJSON(response, HTTP.Status.OK,
					Parameters.STATUS, Messages.Status.OK.toString(),
					Parameters.USERNAME, email,
					Parameters.TOKEN, newToken);
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
            ServletRequest request, ServletResponse response) {
		if (isLoginRequest(request, response)) {
			HTTP.writeAsJSON(response, HTTP.Status.UNAUTHORIZED,
					Parameters.STATUS, Messages.Status.UNAUTHORIZED.toString(),
					Parameters.FAILURE_REASON, Messages.Login.getMessage(e));			
		} else {
			return stopWithNoToken(response);
		}
		return false;
	}
	
	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		if (!WebUtils.toHttp(request).getMethod().equals(POST_METHOD)) {
			WebUtils.toHttp(response).sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method must be POST");
		} else if (isLoginRequest(request, response) && hasAuthorizationToken(request, response)) {
			String authorizationHeader = getAuthorizationHeader(request);
			String[] prinCred = getPrincipalsAndCredentials(authorizationHeader, request);
	        if (prinCred != null && prinCred.length == 2) {
		        String email = prinCred[0];
		        String token = prinCred[1];
		        
				HTTP.writeAsJSON(response, HTTP.Status.OK,
						Parameters.STATUS, Messages.Status.OK.toString(),
						Parameters.USERNAME, email,
						Parameters.TOKEN, token);
	        } else {
	        	HTTP.writeAsJSON(response, HTTP.Status.OK, Parameters.STATUS, Messages.Status.OK.toString());
	        }
			return false;
		}
		return super.onPreHandle(request, response, mappedValue);
	}
	
	protected boolean hasAuthorizationToken(ServletRequest request, ServletResponse response) {
		String authzHeader = getAuthorizationHeader(request);
		return authzHeader != null && isLoginAttempt(authzHeader);
	}
	
	protected String getAuthorizationHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(AUTHORIZATION_HEADER);
    }
	
	protected boolean isLoginAttempt(String authzHeader) {
        String authzScheme = AUTHORIZATION_SCHEME.toLowerCase(Locale.ENGLISH);
        String authzSchemeAlt = AUTHORIZATION_SCHEME_ALT.toLowerCase(Locale.ENGLISH);
        String test = authzHeader.toLowerCase(Locale.ENGLISH);
        return test.startsWith(authzScheme) || test.startsWith(authzSchemeAlt);
    }
	
	protected String[] getPrincipalsAndCredentials(String authorizationHeader, ServletRequest request) {
        if (authorizationHeader == null) {
            return null;
        }
        String[] authTokens = authorizationHeader.split(" ");
        if (authTokens == null || authTokens.length < 2) {
            return null;
        }
        return getPrincipalsAndCredentials(authTokens[0], authTokens[1]);
    }
	
	protected String[] getPrincipalsAndCredentials(String scheme, String encoded) {
        String decoded = Base64.decodeToString(encoded);
        return decoded.split(":", 2);
    }

    protected String getUsername(ServletRequest request) {
        return WebUtils.getCleanParam(request, usernameParam);
    }

    protected String getPassword(ServletRequest request) {
        return WebUtils.getCleanParam(request, passwordParam);
    }
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {    	
    	if (!isLoginRequest(request, response) && isPermissive(mappedValue) && hasAuthorizationToken(request, response)) {
    		return false;
    	}
        return super.isAccessAllowed(request, response, mappedValue) || (!isLoginRequest(request, response) && isPermissive(mappedValue) && !hasAuthorizationToken(request, response));
    }

}
