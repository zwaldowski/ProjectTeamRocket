package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.common.base.Preconditions;
import com.google.inject.Provides;

import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.util.MimeTypes;
import edu.gatech.oad.rocket.findmythings.server.web.PageGenerator;

public abstract class BaseServlet extends HttpServlet {

    @SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(TemplateServlet.class.getName());

    /**
	 *
	 */
	private static final long serialVersionUID = -3019345766517575273L;

	protected PageGenerator generator;

	public BaseServlet() {
		super();
	}

	@Inject
	protected void setGenerator(PageGenerator nGenerator) {
	    generator = nGenerator;
	}

	protected void write(String mimeType, int returnCode, String output, HttpServletResponse response)
			throws IOException {
			    response.setContentType(mimeType);
			    response.setStatus(returnCode);
			    response.getWriter().println(output);
			}

	protected void writeJSON(HttpServletResponse response, int status, JSONObject obj)
			throws IOException {
			    write(MimeTypes.JSON, status, obj.toString(), response);
			}

	protected void writeAsJSON(HttpServletResponse response, int status, Object... args)
			throws IOException {
			    Preconditions.checkArgument(args.length % 2 == 0, "There must be an even number of argument strings");
			        try {
			        JSONObject obj = new JSONObject();
			        for (int i = 0; i < args.length; i += 2) {
			            obj.put((String)args[i], args[i+1]);
			        }
			        writeJSON(response, status, obj);
			    } catch (JSONException e) {
			        throw new RuntimeException(e);
			    }
			}

	protected int getIntRequestParam(HttpServletRequest request, String paramName, int defaultValue) {
	    String s = request.getParameter(paramName);
	    return (s == null) ? defaultValue : Integer.parseInt(s);
	}

	protected boolean getBoolRequestParam(HttpServletRequest request, String paramName, boolean defaultValue) {
	    String s = request.getParameter(paramName);
	    return (s == null) ? defaultValue : Boolean.parseBoolean(s);
	}

	protected AppMember getCurrentUser() {
	    Subject subject = SecurityUtils.getSubject();
	    String email = (String)subject.getPrincipal();
	    return email == null ? null : DatabaseService.ofy().load().memberWithEmail(email);
	}

	protected String getCurrentUserEmail() {
	    return (String)SecurityUtils.getSubject().getPrincipal();
	}

	@Provides
	protected final Map<String,Object> getParameterMap(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        addParametersToMap(request, map);
        return map;
    }

	protected void addParametersToMap(HttpServletRequest request, Map<String, Object> params) {
		String email = getCurrentUserEmail();
		if (email != null) params.put("userEmail", email);
	}

}