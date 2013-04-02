package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

import edu.gatech.oad.rocket.findmythings.server.util.HTTP;
import edu.gatech.oad.rocket.findmythings.server.util.Messages;
import edu.gatech.oad.rocket.findmythings.server.util.Parameters;


@Singleton
public class LoginEndpoint extends BaseServlet {
    static final Logger LOGGER = Logger.getLogger(LoginEndpoint.class.getName());

	private static final long serialVersionUID = 7162845201201231985L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// we only get here if the user is already logged in, so...
		HTTP.writeAsJSON(response, HTTP.Status.OK, Parameters.STATUS, Messages.Status.OK.toString());
	}

}
