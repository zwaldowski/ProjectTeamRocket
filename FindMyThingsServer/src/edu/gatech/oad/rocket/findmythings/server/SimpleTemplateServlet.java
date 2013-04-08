package edu.gatech.oad.rocket.findmythings.server;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class SimpleTemplateServlet extends TemplateServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7000937810672592138L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeDocument(response, getDefaultTemplateURI(request), getParameterMap(request));
	}

	// you really should override this in the subclass! :)
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// we only get here if the login has failed.
		writeDocument(response, getDefaultTemplateURI(request), getParameterMap(request));
	}
}
