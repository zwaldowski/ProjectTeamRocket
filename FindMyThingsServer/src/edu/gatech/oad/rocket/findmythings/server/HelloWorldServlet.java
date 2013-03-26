package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class HelloWorldServlet extends BaseServlet {

	private static final long serialVersionUID = -8762824359065213830L;

	public HelloWorldServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}

}
