package edu.gatech.oad.rocket.findmythings.server.security;

import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import edu.gatech.oad.rocket.findmythings.server.util.Config;
import edu.gatech.oad.rocket.findmythings.server.util.HTTP;
import edu.gatech.oad.rocket.findmythings.server.util.Messages;
import edu.gatech.oad.rocket.findmythings.server.util.Responses;

public class BearerTokenAuthenticatingFilter extends AuthenticatingFilter {
		
	protected static final String AUTHORIZATION_HEADER = "Authorization";
	protected static final String AUTHORIZATION_PARAM = "fmthings_auth";
	protected static final String AUTHORIZATION_SCHEME = "FMTTOKEN";
	protected static final String AUTHORIZATION_SCHEME_ALT = "Basic";

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(BearerTokenAuthenticatingFilter.class.getName());

    private String usernameParam;
    private String passwordParam;

	@Override @Inject
    public void setLoginUrl(@Named(Config.Keys.LOGIN_API_URL) String loginUrl) {
    	super.setLoginUrl(loginUrl);
    }

    @Inject
    public void setUsernameParam(@Named(Config.Keys.USERNAME) String usernameParam) {
    	this.usernameParam = usernameParam;
    }
    
    @Inject
    public void setPasswordParam(@Named(Config.Keys.PASSWORD) String passwordParam) {
    	this.passwordParam = passwordParam;
    }
    
    public String getUsernameParam() {
    	return usernameParam;
    }
    
    public String getPasswordParam() {
    	return passwordParam;
    }

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			String username = getUsername(request);
	        String password = getPassword(request);
	        return createToken(username, password, request, response);
		} else {
			String authzHeader = getAuthorizationHeader(request);
			String authzParam = getAuthorizationParameter(request);
			String[] prinCred = null;
			
			if (isHeaderLoginAttempt(authzHeader)) {
				prinCred = this.getHeaderPrincipalsAndCredentials(authzHeader, request);
			} else if (isParameterLoginAttempt(authzParam)) {
				prinCred = this.getParameterPrincipalsAndCredentials(authzHeader, request);
			} else {
				return null;
			}
			
	        if (prinCred == null || prinCred.length != 2) {
	            return null;
	        }

	        String username = prinCred[0];
	        String token = prinCred[1];

	        return new BearerToken(username, token);
		}
	}
	
	private void sendError(ServletResponse response, boolean unauthorizedOrForbidden, String failureReason) {
		HTTP.writeAsJSON(response,
				Responses.STATUS, (unauthorizedOrForbidden ? HTTP.Status.UNAUTHORIZED : HTTP.Status.FORBIDDEN).toInt(),
				Responses.MESSAGE, Messages.Status.UNAUTHORIZED.toString(),
				Responses.FAILURE_REASON, failureReason == null ? Messages.Permissions.REQUIRES_LOGIN.toString() : failureReason);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		boolean authTokenized = hasAuthorizationToken(request, response); 
		boolean isLogin = isLoginRequest(request, response);
		if (authTokenized || isLogin) {
			return executeLogin(request, response);
		} else {
			sendError(response, true, null);
			return false;
		}
	}
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			String email = (String)subject.getPrincipal();
			String newToken = DatabaseService.ofy().createAuthenticationToken(email);
			HTTP.writeAsJSON(response,
					Responses.STATUS, HTTP.Status.OK.toInt(),
					Responses.MESSAGE, Messages.Status.OK.toString(),
					Responses.TOKEN, newToken,
					getUsernameParam(), email);
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		if (isLoginRequest(request, response)) {
			sendError(response, true, Messages.Login.getMessage(e));
		} else {
			sendError(response, false, null);
		}
		return false;
	}
	
	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		if (isLoginRequest(request, response) && hasAuthorizationToken(request, response)) {
			return true;
		}
		return super.onPreHandle(request, response, mappedValue);
	}

	protected boolean hasAuthorizationToken(ServletRequest request, ServletResponse response) {
		String authzHeader = getAuthorizationHeader(request);
		String authzParam = getAuthorizationParameter(request);
		return isHeaderLoginAttempt(authzHeader) || isParameterLoginAttempt(authzParam);
	}
	
	protected String getAuthorizationHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(AUTHORIZATION_HEADER);
    }
	
	protected String getAuthorizationParameter(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return WebUtils.getCleanParam(httpRequest, AUTHORIZATION_PARAM);
	}
	
	protected boolean isHeaderLoginAttempt(String authzHeader) {
		if (authzHeader == null) return false;
        String authzScheme = AUTHORIZATION_SCHEME.toLowerCase(Locale.ENGLISH);
        String authzSchemeAlt = AUTHORIZATION_SCHEME_ALT.toLowerCase(Locale.ENGLISH);
        String test = authzHeader.toLowerCase(Locale.ENGLISH);
        return test.startsWith(authzScheme) || test.startsWith(authzSchemeAlt);
    }
	
	protected boolean isParameterLoginAttempt(String authzParam) {
		return (authzParam != null) && Base64.isBase64(authzParam.getBytes());
	}
	
	protected String[] getHeaderPrincipalsAndCredentials(String authzHeader, ServletRequest request) {
        if (authzHeader == null) {
            return null;
        }
        String[] authTokens = authzHeader.split(" ");
        if (authTokens == null || authTokens.length < 2) {
            return null;
        }
        return getPrincipalsAndCredentials(authTokens[0], authTokens[1]);
    }

	protected String[] getParameterPrincipalsAndCredentials(String authzParam, ServletRequest request) {
        if (authzParam == null) {
            return null;
        }
        return getPrincipalsAndCredentials(AUTHORIZATION_SCHEME, authzParam);
    }

	protected String[] getPrincipalsAndCredentials(String scheme, String encoded) {
        String decoded = Base64.decodeToString(encoded);
        return decoded.split(":", 2);
    }

    protected String getUsername(ServletRequest request) {
        return WebUtils.getCleanParam(request, getUsernameParam());
    }

    protected String getPassword(ServletRequest request) {
        return WebUtils.getCleanParam(request, getPasswordParam());
    }
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return !(!isLoginRequest(request, response) && isPermissive(mappedValue) && hasAuthorizationToken(request, response)) && (super.isAccessAllowed(request, response, mappedValue) || (!isLoginRequest(request, response) && isPermissive(mappedValue) && !hasAuthorizationToken(request, response)));
	}

}
