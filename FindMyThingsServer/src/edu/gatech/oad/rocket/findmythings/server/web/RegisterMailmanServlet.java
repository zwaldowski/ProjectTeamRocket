package edu.gatech.oad.rocket.findmythings.server.web;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.gatech.oad.rocket.findmythings.server.TemplateServlet;
import edu.gatech.oad.rocket.findmythings.server.util.Config;
import edu.gatech.oad.rocket.findmythings.server.util.Envelope;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

@Singleton
public class RegisterMailmanServlet extends TemplateServlet {
	private static final Logger LOG = Logger.getLogger(RegisterMailmanServlet.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 7698678204988659041L;

	private Envelope emailWrapper = null;

	RegisterMailmanServlet() {
		super();
	}

	@Inject
	RegisterMailmanServlet(Envelope emailWrapper) {
		super();
		this.emailWrapper = emailWrapper;
	}

	Envelope getEmailWrapper() {
		return emailWrapper;
	}

	private String urlFor(HttpServletRequest request, String code, String userName, boolean forgot) {
		try {
			URI url = new URI(request.getScheme(), null, request.getServerName(), request.getServerPort(), "/activate",
					Config.TICKET_PARAM+"="+code+"&"+getUsernameParam()+"="+userName+"&"+Config.FORGOT_PASSWORD_PARAM +"="+Boolean.toString(forgot), null);
			return url.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter(getUsernameParam());
		try {
			String registrationString = request.getParameter(Config.TICKET_PARAM);
			boolean forgot = getBoolRequestParam(request, Config.FORGOT_PASSWORD_PARAM, false);
			String url = urlFor(request, registrationString, username, forgot);
			LOG.info("Link URL is " + url);

			String subject = (forgot ? "Password Information" : "Complete Registration") + " for Find My Things";
			String htmlMessage = createDocument("inc/email.ftl",
					"email", username,
					"href", url,
					Config.FORGOT_PASSWORD_PARAM, Boolean.toString(forgot));
			getEmailWrapper().send(username, subject, htmlMessage);

			LOG.info("Registration email sent to " + username + " with return url " + url);
		} catch (Exception e) {
			LOG.severe("Error sending mail to " + username + ": " + e.getMessage());
		}
	}

}
