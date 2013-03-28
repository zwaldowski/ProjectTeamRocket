package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.gatech.oad.rocket.findmythings.server.web.*;

@Singleton
public class LoginServlet extends TemplateServlet {
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

	private static final long serialVersionUID = 2721131296252754856L;

	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}

    /*@Inject
    LoginServlet(Provider<UserDAO> daoProvider) {
        super(daoProvider);
    }*/
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeDocument(response, "login.ftl");
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// we only get here if the login has failed.
		Map<String, Object> extraInfo = new HashMap<>();
		Object failureReason = request.getAttribute(Parameters.FAILURE_REASON);
		if (failureReason != null) {
			extraInfo.put(Parameters.FAILURE_REASON, failureReason);
		}
		writeDocument(response, "login.ftl", extraInfo);
	}

}
