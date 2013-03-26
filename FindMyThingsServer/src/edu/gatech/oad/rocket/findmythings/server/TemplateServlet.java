package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.common.base.Preconditions;

import edu.gatech.oad.rocket.findmythings.server.web.*;
import edu.gatech.oad.rocket.findmythings.server.util.*;

public class TemplateServlet extends HttpServlet {

	private static final long serialVersionUID = 8526927539799303725L;
	
    static final Logger LOGGER = Logger.getLogger(TemplateServlet.class.getName());

    protected final String MESSAGE = "message";
    protected final String CODE = "code";

    private PageGenerator generator;
    
	public TemplateServlet() {
		// TODO Auto-generated constructor stub
	}

	// for once we do login/user stuff
    /*protected Provider<UserDAO> daoProvider;

    protected TemplateServlet(Provider<UserDAO> daoProvider) {
        this.daoProvider = daoProvider;
    }*/

    @Inject
    protected void setCreate(PageGenerator nGenerator) {
        generator = nGenerator;
    }

    protected void write(String mimeType, int returnCode, String output, HttpServletResponse response) throws IOException {
        response.setContentType(mimeType);
        response.setStatus(returnCode);
        response.getWriter().println(output);
    }

    protected void writeJSON(HttpServletResponse response, int status, JSONObject obj) throws IOException {
        write(MimeTypes.JSON, status, obj.toString(), response);
    }

    protected void writeAsJSON(HttpServletResponse response, int status, Object... args) throws IOException {
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
    
    protected void writeDocument(HttpServletResponse response, String templateName, Object... args) throws IOException {
    	writeDocument(response, templateName, PageGenerator.map(args));
    }
    
    protected void writeDocument(HttpServletResponse response, String templateName, Map<String,Object> args) throws IOException {
    	String html = createDocument(templateName, args);
    	write(MimeTypes.HTML, HTTP.STATUS_OK, html, response);
    }
    
    protected String createDocument(String templateName, Object... args) throws IOException {
    	return createDocument(templateName, PageGenerator.map(args));
    }
    
    protected String createDocument(String templateName, Map<String,Object> userArgs) throws IOException {
    	Map<String, Object> combinedArgs = new HashMap<>(userArgs);
    	combinedArgs.putAll(getCurrentPageVariables());
    	return generator.createPage(templateName, combinedArgs);
    }

    protected int getIntRequestParam(HttpServletRequest request, String paramName, int defaultValue) {
        String s = request.getParameter(paramName);
        return (s == null) ? defaultValue : Integer.parseInt(s);
    }

    protected boolean getBoolRequestParam(HttpServletRequest request, String paramName, boolean defaultValue) {
        String s = request.getParameter(paramName);
        return (s == null) ? defaultValue : Boolean.parseBoolean(s);
    }
    
    protected Map<String, Object> getCurrentPageVariables() {
    	// TODO
    	Map<String, Object> map = new HashMap<>();
    	map.put("firstname", "USER");
    	return map;
    }

}
