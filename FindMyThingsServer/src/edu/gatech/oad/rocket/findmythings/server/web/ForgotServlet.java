package edu.gatech.oad.rocket.findmythings.server.web;

import edu.gatech.oad.rocket.findmythings.server.model.MessageBean;
import edu.gatech.oad.rocket.findmythings.server.util.Config;
import edu.gatech.oad.rocket.findmythings.server.util.Messages;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ForgotServlet extends RegisterServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6533847551106261631L;

	@Override
	protected void sendError(HttpServletRequest request, HttpServletResponse response, Messages.Register message) {
		request.setAttribute(MessageBean.FAILURE_REASON, message.toString());
		try {
			doGet(request, response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void sendOK(HttpServletRequest request, HttpServletResponse response) {
		try {
			writeDocument(response, "registerSubmitted.ftl", getParameterMap(request));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeDocument(response, "register.ftl", getParameterMap(request));
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String email = WebUtils.getCleanParam(request, Config.USERNAME_PARAM);

			if (emailIsInvalid(email)) {
				sendError(request, response, Messages.Register.BAD_EMAIL_ADDRESS);
				return;
			}

			if (memberExistsWithEmail(email)) {
				mailAuthenticationTokenSendOK(request, response, email, true);
			} else {
				sendError(request, response, Messages.Register.NO_SUCH_MEMBER);
			}
		} catch (Exception e) {
			sendError(request, response, Messages.Register.INVALID_DATA);
		}
	}

	@Override
	protected void addParametersToMap(HttpServletRequest request, Map<String, Object> params) {
		super.addParametersToMap(request, params);
		params.put(Config.FORGOT_PASSWORD_PARAM, true);
	}

}
