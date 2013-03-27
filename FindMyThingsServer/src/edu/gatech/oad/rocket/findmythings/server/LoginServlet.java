package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.common.collect.Maps;

import edu.gatech.oad.rocket.findmythings.server.web.*;
import edu.gatech.oad.rocket.findmythings.server.util.*;

@Singleton
public class LoginServlet extends TemplateServlet {
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

	private static final long serialVersionUID = 2721131296252754856L;

	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}

    /*@Inject
    LoginServlet(Provider<UserDAO> daoProvider) {
        super(daoProvider);
    }*/
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeDocument(response, "login.ftl");
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	boolean webRequest = WebUtils.isTrue(request, Parameters.WEB_LOGIN);
        	
            String email = WebUtils.getCleanParam(request, Parameters.USERNAME);
            String password = WebUtils.getCleanParam(request, Parameters.PASSWORD);
            boolean rememberMe = WebUtils.isTrue(request, Parameters.REMEMBER_ME);
            String host = request.getRemoteHost();
            
            UsernamePasswordToken token = new UsernamePasswordToken(email, password, rememberMe, host);
            Subject subject = SecurityUtils.getSubject();
            Map<String, Object> info = new HashMap<>();
            
            if (newLogin(token, subject, info)) {
            	if (webRequest) {
            		LOGGER.log(Level.FINE, "Successful log in.");
            		WebUtils.redirectToSavedRequest(request, response, "index.html");
            	} else {
            		LOGGER.log(Level.FINE, "Successful log in.", info);
            		//info.put(Parameters.SESSION_TOKEN, null);
            		JSONObject json = new JSONObject(info);
            		writeJSON(response, HTTP.STATUS_OK, json);
            	}
            } else {
            	if (webRequest) {
            		LOGGER.log(Level.FINE, "Failed to log in, should redirect.");
            		// TODO return to login page
            	} else {
            		LOGGER.log(Level.FINE, "Failed to log in, throwing up.");
            		JSONObject json = new JSONObject(info);
            		writeJSON(response, HTTP.STATUS_FORBIDDEN, json);
            	}
            }
        } catch (Exception e) {
        	write(MimeTypes.PLAINTEXT, HTTP.STATUS_INTERNAL_ERROR, "Internal error: " + e.getMessage(), response);
        }
    }

}
