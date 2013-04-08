package edu.gatech.oad.rocket.findmythings.server.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.google.inject.Singleton;

import edu.gatech.oad.rocket.findmythings.server.PageServlet;
import edu.gatech.oad.rocket.findmythings.server.util.HTTP;
import edu.gatech.oad.rocket.findmythings.server.util.Responses;

@Singleton
public class AuthTestEndpoint extends PageServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6058248835620387583L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {
			String message = "Howdy, " + subject.getPrincipal() + "!";
			HTTP.writeAsJSON(response, Responses.STATUS, HTTP.Status.OK.toInt(), Responses.MESSAGE, message);
		} else {
			HTTP.writeAsJSON(response, Responses.STATUS, HTTP.Status.OK.toInt(), Responses.MESSAGE, "You're not logged in.");
		}
	}

}
