package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class AuthTestServlet extends TemplateServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1241873390341248439L;

	public AuthTestServlet() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeDocument(response, "authtest.ftl");
	}

}
