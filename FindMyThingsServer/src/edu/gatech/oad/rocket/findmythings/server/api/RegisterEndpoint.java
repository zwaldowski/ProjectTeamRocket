package edu.gatech.oad.rocket.findmythings.server.api;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;

import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.inject.Singleton;

import edu.gatech.oad.rocket.findmythings.server.TemplateServlet;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBMember;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.util.Config;
import edu.gatech.oad.rocket.findmythings.server.util.HTTP;
import edu.gatech.oad.rocket.findmythings.server.util.Messages;
import edu.gatech.oad.rocket.findmythings.server.util.Responses;
import edu.gatech.oad.rocket.findmythings.server.util.validation.EmailValidator;
import edu.gatech.oad.rocket.findmythings.server.util.validation.RegexValidator;

@Singleton
public class RegisterEndpoint extends TemplateServlet {
	static final Logger LOGGER = Logger.getLogger(RegisterEndpoint.class.getName());
	public static final String REALNAME = "name";
	public static final String PASSWORD_CONFIRM = "password_alt";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7637940962711953379L;

	@Singleton
	private static RegexValidator getPhoneNumberValidator() {
		return new RegexValidator("[0-9]-([0-9]{3})-[0-9]{3}-[0-9]{4}", false);
	}

	// register workflow
	// GET register -> POST register -> validate for errors
	//  - if errors, set attr so addParametersToMap picks it up, call doGet
	//  - if no errors, create user in DB, create auth ticket
	// email link with token to user
	// user clicks link, GET on activate with URL param, activates user

	// fun fact: password recovery is almost identical

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String email = WebUtils.getCleanParam(request, getUsernameParam());

			if (!emailIsValid(email)) {
				sendError(request, response, Messages.Register.BADEMAILADDR);
				return;
			}

			AppMember user = memberWithEmail(email);

			if (user != null && user.isRegistered()) {
				sendError(request, response, Messages.Register.ALREADYAUSER);
				return;
			}

			String password = WebUtils.getCleanParam(request, getPasswordParam());
			String passwordAlt = WebUtils.getCleanParam(request, PASSWORD_CONFIRM);

			if (password == null || password.length() < 3 || passwordAlt == null || passwordAlt.length() < 3) {
				sendError(request, response, Messages.Register.BAD_PASSWORD);
				return;
			}

			if (!password.equals(passwordAlt)) {
				sendError(request, response, Messages.Register.PASSNOTMATCH);
				return;
			}

			String phoneString = WebUtils.getCleanParam(request, PHONE);

			if (!getPhoneNumberValidator().isValid(phoneString)) {
				sendError(request, response, Messages.Register.INVALIDPHONE);
				return;
			}

			String name = WebUtils.getCleanParam(request, REALNAME);
			PhoneNumber phone = new PhoneNumber(phoneString);
			String address = WebUtils.getCleanParam(request, ADDRESS);

			if (user == null) {
				DatabaseService.ofy().createMember(email, password, name, phone, address);
			} else {
				DatabaseService.ofy().updateMember((DBMember)user, password, name, phone, address);
			} 

			mailAuthenticationTokenSendOK(request, response, email, false);
		} catch (Exception e) {
			sendError(request, response, Messages.Register.INVALID_DATA);
		}
	}

	protected boolean emailIsValid(String email) {
		return email != null && email.length() > 0 && EmailValidator.getInstance().isValid(email);
	}

	protected void mailAuthenticationTokenSendOK(HttpServletRequest request, HttpServletResponse response, String email, boolean isForgot) {
		String registrationToken = DatabaseService.ofy().createRegistrationTicket(email);

		// send email with registrationToken
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/sendMail")
				.param(getUsernameParam(), email)
				.param(Config.FORGOTPASSWORD_PARAM, Boolean.toString(isForgot))
				.param(Config.TICKET_PARAM, registrationToken));

		sendOK(request, response);
	}

	protected void sendError(HttpServletRequest request, HttpServletResponse response, Messages.Register message) {
		HTTP.writeAsJSON(response,
				Responses.STATUS, HTTP.Status.BAD_REQUEST.toInt(),
				Responses.MESSAGE, Messages.Status.FAILED.toString(),
				Responses.FAILURE_REASON, message.toString());
	}

	protected void sendOK(HttpServletRequest request, HttpServletResponse response) {
		HTTP.writeOKJSON(response);
	}

}
