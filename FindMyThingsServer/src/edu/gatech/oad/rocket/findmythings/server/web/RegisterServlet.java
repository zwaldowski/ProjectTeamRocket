package edu.gatech.oad.rocket.findmythings.server.web;

import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import edu.gatech.oad.rocket.findmythings.server.TemplateServlet;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBMember;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.model.MessageBean;
import edu.gatech.oad.rocket.findmythings.server.util.Config;
import edu.gatech.oad.rocket.findmythings.server.util.Messages;
import edu.gatech.oad.rocket.findmythings.shared.util.validation.EmailValidator;
import edu.gatech.oad.rocket.findmythings.shared.util.validation.RegexValidator;

import org.apache.shiro.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class RegisterServlet extends TemplateServlet {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());

	private static final String REAL_NAME = "name";
	private static final String PASSWORD_CONFIRM = "password_alt";
	private static final String PHONE = "phone";
	private static final String ADDRESS = "address";
	private static final long serialVersionUID = -7032016092040490069L;

	// register workflow
	// GET register -> POST register -> validate for errors
	//  - if errors, set attr so addParametersToMap picks it up, call doGet
	//  - if no errors, create user in DB, create auth ticket
	// email link with token to user
	// user clicks link, GET on activate with URL param, activates user

	// fun fact: password recovery is almost identical
	
	private static final RegexValidator PHONE_VALIDATOR = new RegexValidator("[0-9]-([0-9]{3})-[0-9]{3}-[0-9]{4}", false);

	public static RegexValidator getPhoneNumberValidator() {
		return PHONE_VALIDATOR;
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String email = WebUtils.getCleanParam(request, Config.USERNAME_PARAM);

			if (emailIsInvalid(email)) {
				sendError(request, response, Messages.Register.BAD_EMAIL_ADDRESS);
				return;
			}

			AppMember user = memberWithEmail(email);

			if (user != null && user.isRegistered()) {
				sendError(request, response, Messages.Register.ALREADY_USER);
				return;
			}

			String password = WebUtils.getCleanParam(request, Config.PASSWORD_PARAM);
			String passwordAlt = WebUtils.getCleanParam(request, PASSWORD_CONFIRM);

			if (password == null || password.length() < 3 || passwordAlt == null || passwordAlt.length() < 3) {
				sendError(request, response, Messages.Register.BAD_PASSWORD);
				return;
			}

			if (!password.equals(passwordAlt)) {
				sendError(request, response, Messages.Register.PASSWORDS_MATCH);
				return;
			}

			String phoneString = WebUtils.getCleanParam(request, PHONE);

			if (!getPhoneNumberValidator().isValid(phoneString)) {
				sendError(request, response, Messages.Register.INVALID_PHONE);
				return;
			}

			String name = WebUtils.getCleanParam(request, REAL_NAME);
			PhoneNumber phone = new PhoneNumber(phoneString);
			String address = WebUtils.getCleanParam(request, ADDRESS);
			
			DBMember newUser = (DBMember)user;

			if (newUser == null) {
				newUser = new DBMember(email, password, null, null, true);
				newUser.setPhone(phone);
				newUser.setName(name);
				newUser.setAddress(address);
			} else {
				if (phone != null) newUser.setPhone(phone);
				if (name != null) newUser.setName(name);
				if (address != null) newUser.setAddress(address);
			}
			
			newUser.setAdmin(false);
			
			DatabaseService.ofy().save().entity(newUser);

			mailAuthenticationTokenSendOK(request, response, email, false);
		} catch (Exception e) {
			sendError(request, response, Messages.Register.INVALID_DATA);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeDocument(response, getDefaultTemplateURI(request), getParameterMap(request));
	}

	@Override
	protected void addParametersToMap(HttpServletRequest request, Map<String, Object> params) {
		super.addParametersToMap(request, params);
		params.put(Config.FORGOT_PASSWORD_PARAM, false);
	}

	void sendError(HttpServletRequest request, HttpServletResponse response, Messages.Register message) {
		request.setAttribute(MessageBean.FAILURE_REASON, message.toString());
		try {
			doGet(request, response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	void sendOK(HttpServletRequest request, HttpServletResponse response) {
		try {
			writeDocument(response, "registerSubmitted.ftl", getParameterMap(request));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	boolean emailIsInvalid(String email) {
		return email == null || email.length() == 0 || !EmailValidator.getInstance().isValid(email);
	}

	void mailAuthenticationTokenSendOK(HttpServletRequest request, HttpServletResponse response, String email, boolean isForgot) {
		String registrationToken = DatabaseService.ofy().createRegistrationTicket(email);

		// send email with registrationToken
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/sendMail")
				.param(Config.USERNAME_PARAM, email)
				.param(Config.FORGOT_PASSWORD_PARAM, Boolean.toString(isForgot))
				.param(Config.TICKET_PARAM, registrationToken));

		sendOK(request, response);
	}
}
