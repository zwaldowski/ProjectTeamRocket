package edu.gatech.oad.rocket.findmythings.server.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeMailmanServlet extends RegisterMailmanServlet {
	private static final Logger LOG = Logger.getLogger(WelcomeMailmanServlet.class.getName());
	private static final long serialVersionUID = 7711096400552379493L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter(getUsernameParam());
		try {
			String subject = "Welcome to Find My Things";
			String htmlMessage = createDocument("inc/emailWelcome.ftl");
			getEmailWrapper().send(email, subject, htmlMessage);

			LOG.info("Registration email sent to " + email);
		} catch (Exception e) {
			LOG.severe("Error sending mail to " + email + ": " + e.getMessage());
		}
	}
	
}
