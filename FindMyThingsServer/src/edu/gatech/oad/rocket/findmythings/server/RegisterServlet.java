package edu.gatech.oad.rocket.findmythings.server;

import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import edu.gatech.oad.rocket.findmythings.server.util.Parameters;

@Singleton
public class RegisterServlet extends TemplateServlet {
	static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7032016092040490069L;

	public RegisterServlet() {
		// TODO Auto-generated constructor stub
	}
	
	// Messages.Register.ALREADYAUSER
	// Messages.Register.BAD_PASSWORD
	// Messages.Register.PASSNOTMATCH
	// Messages.Register.INVALIDPHONE

	@Override
	protected void addParametersToMap(HttpServletRequest request, Map<String, Object> params) {
		super.addParametersToMap(request, params);
		Object failureReason = request.getAttribute(Parameters.FAILURE_REASON);
		if (failureReason != null) {
			params.put(Parameters.FAILURE_REASON, failureReason);
		}
	}
}
