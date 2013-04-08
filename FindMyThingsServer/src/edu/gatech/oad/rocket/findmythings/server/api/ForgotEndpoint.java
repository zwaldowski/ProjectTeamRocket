package edu.gatech.oad.rocket.findmythings.server.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;

import com.google.inject.Singleton;

import edu.gatech.oad.rocket.findmythings.server.util.Messages;

@Singleton
public class ForgotEndpoint extends RegisterEndpoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3131265945592171117L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String email = WebUtils.getCleanParam(request, getUsernameParam());

			if (!emailIsValid(email)) {
				sendError(request, response, Messages.Register.BADEMAILADDR);
				return;
			}

			if (!memberExistsWithEmail(email)) {
				sendError(request, response, Messages.Register.NOSUCHMEMBER);
				return;
			}

			mailAuthenticationTokenSendOK(request, response, email, true);
		} catch (Exception e) {
			sendError(request, response, Messages.Register.INVALID_DATA);
		}
	}

}
