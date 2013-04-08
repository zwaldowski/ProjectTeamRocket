package edu.gatech.oad.rocket.findmythings.server.spi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.inject.Singleton;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBMember;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.model.MessageBean;
import edu.gatech.oad.rocket.findmythings.server.util.Config;
import edu.gatech.oad.rocket.findmythings.server.util.HTTP;
import edu.gatech.oad.rocket.findmythings.server.util.Messages;
import edu.gatech.oad.rocket.findmythings.server.util.validation.EmailValidator;
import edu.gatech.oad.rocket.findmythings.server.util.validation.RegexValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

@Api(name = "fmthings", version = "v1")
public class AccountV1 extends BaseEndpoint {

	protected boolean emailIsValid(String email) {
		return email != null && email.length() > 0 && EmailValidator.getInstance().isValid(email);
	}

	@Singleton
	private static RegexValidator getPhoneNumberValidator() {
		return new RegexValidator("[0-9]-([0-9]{3})-[0-9]{3}-[0-9]{4}", false);
	}

	protected MessageBean mailAuthenticationTokenSendOK(String email, boolean isForgot) {
		String registrationToken = DatabaseService.ofy().createRegistrationTicket(email);

		// send email with registrationToken
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/sendMail")
				.param(Config.USERNAME_PARAM, email)
				.param(Config.FORGOT_PASSWORD_PARAM, Boolean.toString(isForgot))
				.param(Config.TICKET_PARAM, registrationToken));

		return new MessageBean(HTTP.Status.OK, Messages.Status.OK.toString());
	}

	@ApiMethod(name = "fa", path = "register")
	public MessageBean createMember(@Named("username") String email, @Named("password") String password,
			@Named("password_alt") String passwordAlt, @Named("phone") @Nullable String phone,
			@Named("name") @Nullable String name, @Named("address") @Nullable String address) {
		try {
			if (!emailIsValid(email)) {
				return new MessageBean(HTTP.Status.BAD_REQUEST, Messages.Status.FAILED.toString(), Messages.Register.BAD_EMAIL_ADDRESS.toString());
			}

			AppMember user = getMemberWithEmail(email);

			if (user != null && user.isRegistered()) {
				return new MessageBean(HTTP.Status.BAD_REQUEST, Messages.Status.FAILED.toString(), Messages.Register.ALREADY_USER.toString());
			}

			if (password == null || password.length() < 3 || passwordAlt == null || passwordAlt.length() < 3) {
				return new MessageBean(HTTP.Status.BAD_REQUEST, Messages.Status.FAILED.toString(), Messages.Register.BAD_PASSWORD.toString());
			}

			if (!password.equals(passwordAlt)) {
				return new MessageBean(HTTP.Status.BAD_REQUEST, Messages.Status.FAILED.toString(), Messages.Register.PASSWORDS_MATCH.toString());
			}

			if (!getPhoneNumberValidator().isValid(phone)) {
				return new MessageBean(HTTP.Status.BAD_REQUEST, Messages.Status.FAILED.toString(), Messages.Register.INVALID_PHONE.toString());
			}

			PhoneNumber phoneNum = new PhoneNumber(phone);

			if (user == null) {
				Set<String> roles = new HashSet<String>();
				roles.add("user");
				Set<String> permissions = new HashSet<String>();
				permissions.add("browse");
				permissions.add("submit");

				DBMember newUser = new DBMember(email, password, roles, permissions, true);
				newUser.setPhone(phoneNum);
				newUser.setName(name);
				newUser.setAddress(address);
				DatabaseService.ofy().save().entity(newUser);
			} else {
				DatabaseService.ofy().updateMember((DBMember)user, password, name, phoneNum, address);
			}

			return mailAuthenticationTokenSendOK(email, false);
		} catch (Exception e) {
			return new MessageBean(HTTP.Status.BAD_REQUEST, Messages.Status.FAILED.toString(), Messages.Register.INVALID_DATA.toString());
		}
	}

	@ApiMethod(name = "account.forgot", path = "forgot")
	public MessageBean memberForgotPassword(@Named("username") String email) {
		try {
			if (!emailIsValid(email)) {
				return new MessageBean(HTTP.Status.BAD_REQUEST, Messages.Status.FAILED.toString(), Messages.Register.BAD_EMAIL_ADDRESS.toString());
			}

			if (!memberExistsWithEmail(email)) {
				return new MessageBean(HTTP.Status.BAD_REQUEST, Messages.Status.FAILED.toString(), Messages.Register.NO_SUCH_MEMBER.toString());
			}

			return mailAuthenticationTokenSendOK(email, true);
		} catch (Exception e) {
			return new MessageBean(HTTP.Status.BAD_REQUEST, Messages.Status.FAILED.toString(), Messages.Register.INVALID_DATA.toString());
		}
	}

	@ApiMethod(name = "account.get", path = "account")
	public AppMember getCurrentMember() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		if (principals == null || principals.isEmpty()) return null;
		String email = (String)principals.getPrimaryPrincipal();
		return getMemberWithEmail(email);
	}

	@ApiMethod(name = "account.update", path = "account/update")
	public void updateMember(AppMember member) {
		if (member.isEditable()) {
			DatabaseService.ofy().save().entity((DBMember)member);
		}
	}

	@ApiMethod(name = "account.login", path = "account/login")
	public MessageBean getLoginToken() {
		return new MessageBean(HTTP.Status.OK, "Hi.");
	}

	@ApiMethod(name = "account.logout", path = "account/logout")
	public MessageBean deleteLoginToken() {
		// this was caught by BearerTokenRevokeFilter
		return new MessageBean(HTTP.Status.OK, Messages.Status.OK.toString());
	}

}
