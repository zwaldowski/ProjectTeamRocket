package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.gatech.oad.rocket.findmythings.server.util.Parameters;

@Singleton
public class RegisterServlet extends RegisterEndpoint {
	static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7032016092040490069L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeDocument(response, getDefaultTemplateURI(request), getParameterMap(request));
	}
	
	// register workflow
	// GET register.jsp -> POST register.jsp -> validate for errors
	//  - if errors, set attr so addParametersToMap picks it up, call doGet
	//  - if no errors, create user in DB, create auth ticket
	// email link with token to user
	// user clicks link, GET on activate.jsp with URL param, activates user
	
	// fun fact: password recovery is almost identical
	
	// Messages.Register.ALREADYAUSER
	// Messages.Register.BAD_PASSWORD
	// Messages.Register.PASSNOTMATCH
	// Messages.Register.INVALIDPHONE
	// or any message (generic box)
	
	// real name = name
	// email = username
	// pass = password
	// pass confirm = password_alt
	// phone num = phone in format 1-555-555-5555
	// address = address

	@Override
	protected void addParametersToMap(HttpServletRequest request, Map<String, Object> params) {
		super.addParametersToMap(request, params);
		Object failureReason = request.getAttribute(Parameters.FAILURE_REASON);
		if (failureReason != null) {
			params.put(Parameters.FAILURE_REASON, failureReason);
		}
	}
}
