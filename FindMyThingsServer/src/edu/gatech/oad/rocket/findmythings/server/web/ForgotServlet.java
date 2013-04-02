package edu.gatech.oad.rocket.findmythings.server.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

import edu.gatech.oad.rocket.findmythings.server.api.ForgotEndpoint;
import edu.gatech.oad.rocket.findmythings.server.util.Messages;
import edu.gatech.oad.rocket.findmythings.server.util.Responses;

@Singleton
public class ForgotServlet extends ForgotEndpoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6533847551106261631L;

	@Override
	protected void sendError(HttpServletRequest request, HttpServletResponse response, Messages.Register message) {
		request.setAttribute(Responses.FAILURE_REASON, message.toString());
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
	protected void addParametersToMap(HttpServletRequest request, Map<String, Object> params) {
		super.addParametersToMap(request, params);
		params.put(FORGOTPASSWORD, true);
	}

}
