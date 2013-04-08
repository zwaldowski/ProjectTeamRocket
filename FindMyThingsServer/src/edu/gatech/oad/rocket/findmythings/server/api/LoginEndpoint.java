package edu.gatech.oad.rocket.findmythings.server.api;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

import edu.gatech.oad.rocket.findmythings.server.PageServlet;
import edu.gatech.oad.rocket.findmythings.server.util.HTTP;

@Singleton
public class LoginEndpoint extends PageServlet {
	static final Logger LOGGER = Logger.getLogger(LoginEndpoint.class.getName());

	private static final long serialVersionUID = 7162845201201231985L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// we only get here if the user is already logged in, so...
		HTTP.writeOKJSON(response);
	}

}
