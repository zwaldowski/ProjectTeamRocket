package edu.gatech.oad.rocket.findmythings.server.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;

import com.google.inject.Singleton;

import edu.gatech.oad.rocket.findmythings.server.TemplateServlet;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBMember;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.util.Config;
import edu.gatech.oad.rocket.findmythings.server.util.Messages;
import edu.gatech.oad.rocket.findmythings.server.util.Responses;

@Singleton
public class ActivateServlet extends TemplateServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7164782734338940628L;
	
	public static final String PASSWORD_CONFIRM = "password_alt";

    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String code = WebUtils.getCleanParam(request, Config.TICKET_PARAM);
    	String username = WebUtils.getCleanParam(request, getUsernameParam());
    	if (code == null || code.length() == 0 || username == null || username.length() == 0) {
    		WebUtils.issueRedirect(request, response, "/");
    	}
    	boolean forgot = getBoolRequestParam(request, Config.FORGOTPASSWORD_PARAM, false);
    	
    	request.setAttribute(Config.TICKET_PARAM, code);
    	request.setAttribute(getUsernameParam(), username);
    	request.setAttribute(Config.FORGOTPASSWORD_PARAM, forgot);
    	
    	if (forgot) {
    		// display password reset form
    		writeDocument(response, getDefaultTemplateURI(request), getParameterMap(request));
    	} else {
    		String userNameFromCode = DatabaseService.ofy().load().emailFromRegistrationCode(code);
    		if (userNameFromCode == null) {
    			request.setAttribute(Responses.FAILURE_REASON, Messages.Activate.CODE_EXPIRED.toString());
    		} else {
    			DatabaseService.ofy().register(code, userNameFromCode);
    		}
    	}
    	
    	writeDocument(response, getDefaultTemplateURI(request), getParameterMap(request));
	}

    // you really should override this in the subclass! :)
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
	    	String code = WebUtils.getCleanParam(request, Config.TICKET_PARAM);
	    	String email = WebUtils.getCleanParam(request, getUsernameParam());
	    	if (code == null || code.length() == 0 || email == null || email.length() == 0) {
	    		sendError(request, response, Messages.Activate.INVALID_DATA);
				return;
	    	}

			String password = WebUtils.getCleanParam(request, getPasswordParam());
			String passwordAlt = WebUtils.getCleanParam(request, PASSWORD_CONFIRM);
			
			if (password == null || password.length() < 3 || passwordAlt == null || passwordAlt.length() < 3) {
				sendError(request, response, Messages.Activate.BAD_PASSWORD);
				return;
			}
			
			if (!password.equals(passwordAlt)) {
				sendError(request, response, Messages.Activate.PASSNOTMATCH);
				return;
			}
			
			AppMember user = memberWithEmail(email);
			if (user == null) {
				sendError(request, response, Messages.Activate.NO_SUCH_USER);
				return;
			}
			
			DatabaseService.ofy().updateMember((DBMember)user, password);
			DatabaseService.ofy().register((DBMember)user, code);

			request.setAttribute(Config.FORGOTPASSWORD_PARAM, false);
			writeDocument(response, getDefaultTemplateURI(request), getParameterMap(request));
		} catch (Exception e) {
			sendError(request, response, Messages.Activate.INVALID_DATA);
		}
	}

	protected void sendError(HttpServletRequest request, HttpServletResponse response, Messages.Activate message) {
		request.setAttribute(Responses.FAILURE_REASON, message.toString());
		try {
			writeDocument(response, getDefaultTemplateURI(request), getParameterMap(request));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void addParametersToMap(HttpServletRequest request, Map<String, Object> params) {
		super.addParametersToMap(request, params);
		
		Object code = request.getAttribute(Config.TICKET_PARAM);
		if (code != null) params.put(Config.TICKET_PARAM, code);
		
		Object forgot = request.getAttribute(Config.FORGOTPASSWORD_PARAM);
		if (forgot != null) params.put(Config.FORGOTPASSWORD_PARAM, code);
		
		Object user = request.getAttribute(getUsernameParam());
		if (user != null) params.put(getUsernameParam(), code);
	}

}
