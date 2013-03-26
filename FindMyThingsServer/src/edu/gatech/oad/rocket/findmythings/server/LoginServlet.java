package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.Enumeration;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class LoginServlet extends BaseServlet {

	private static final long serialVersionUID = 2721131296252754856L;

	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Login");
		@SuppressWarnings("unchecked")
		Enumeration<String> allHeaderNames = req.getHeaderNames();
		while (allHeaderNames.hasMoreElements()) {
			String headerName = allHeaderNames.nextElement();
			resp.getWriter().println(headerName + req.getHeader(headerName));
		}
	}

}
