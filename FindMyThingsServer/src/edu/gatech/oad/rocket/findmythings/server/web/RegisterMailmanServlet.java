 package edu.gatech.oad.rocket.findmythings.server.web;

import edu.gatech.oad.rocket.findmythings.server.TemplateServlet;
import edu.gatech.oad.rocket.findmythings.server.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class RegisterMailmanServlet extends TemplateServlet {
	private static final Logger LOG = Logger.getLogger(RegisterMailmanServlet.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 7698678204988659041L;
	
	static class Envelope {
		private static final Logger LOG = Logger.getLogger(Envelope.class.getName());

		private static final String fromAddress = Config.APP_EMAIL;

		static void send(String toAddress, String subject, String htmlMessage) {
			LOG.info("sending message to " + toAddress);
			MailService service = MailServiceFactory.getMailService();
			MailService.Message message = new MailService.Message();
			message.setSender(fromAddress);
			message.setTo(toAddress);
			message.setSubject(subject);
			message.setHtmlBody(htmlMessage);
			try {
				service.send(message);
				LOG.info("message has been sent to " + toAddress);
			} catch (IOException e) {
				LOG.warning("Can't send email to " + toAddress + " about " + subject + ": " + e.getMessage());
			}
		}

	}

	RegisterMailmanServlet() {
		super();
	}

	private String urlFor(HttpServletRequest request, String code, String email, boolean forgot) {
		try {
			URI url = new URI(request.getScheme(), null, request.getServerName(), request.getServerPort(), "/activate",
					Config.TICKET_PARAM+"="+code+"&"+Config.USERNAME_PARAM+"="+email+"&"+Config.FORGOT_PASSWORD_PARAM +"="+Boolean.toString(forgot), null);
			return url.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter(Config.USERNAME_PARAM);
		try {
			String registrationString = request.getParameter(Config.TICKET_PARAM);
			String forgotString = request.getParameter(Config.FORGOT_PASSWORD_PARAM);
			boolean forgot = (forgotString == null) ? false : Boolean.parseBoolean(forgotString);
			String url = urlFor(request, registrationString, email, forgot);
			LOG.info("Link URL is " + url);

			String subject = (forgot ? "Password Information" : "Complete Registration") + " for Find My Things";
			String htmlMessage = createDocument("inc/email.ftl",
					"email", email,
					"href", url,
					Config.FORGOT_PASSWORD_PARAM, Boolean.toString(forgot));
			Envelope.send(email, subject, htmlMessage);

			LOG.info("Registration email sent to " + email + " with return url " + url);
		} catch (Exception e) {
			LOG.severe("Error sending mail to " + email + ": " + e.getMessage());
		}
	}

}
