package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import edu.gatech.oad.rocket.findmythings.server.web.*;

public class TemplateServlet extends HttpServlet {

	private static final long serialVersionUID = 8526927539799303725L;
	
    static final Logger LOGGER = Logger.getLogger(TemplateServlet.class.getName());

    protected final String MESSAGE = "message";
    protected final String CODE = "code";

    protected final int HTTP_STATUS_OK = 200;
    protected final int HTTP_STATUS_NOT_FOUND = 404;
    protected final int HTTP_STATUS_FORBIDDEN = 403;
    protected final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;

    private PageGenerator generator;
    
	public TemplateServlet() {
		// TODO Auto-generated constructor stub
	}

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

}
