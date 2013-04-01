package edu.gatech.oad.rocket.findmythings.server;

import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import edu.gatech.oad.rocket.findmythings.server.util.Parameters;

@Singleton
public class LoginServlet extends TemplateServlet {
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

	private static final long serialVersionUID = 2721131296252754856L;

	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void addParametersToMap(HttpServletRequest request, Map<String, Object> params) {
		super.addParametersToMap(request, params);
		Object failureReason = request.getAttribute(Parameters.FAILURE_REASON);
		if (failureReason != null) {
			params.put(Parameters.FAILURE_REASON, failureReason);
		}
	}
}
