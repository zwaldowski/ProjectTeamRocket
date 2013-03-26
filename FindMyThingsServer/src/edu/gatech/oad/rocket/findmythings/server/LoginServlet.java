package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.util.Enumeration;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class LoginServlet extends TemplateServlet {

	private static final long serialVersionUID = 2721131296252754856L;

	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		write("text/plain", HTTP_STATUS_OK, "Login", resp);

		@SuppressWarnings("unchecked")
		Enumeration<String> allHeaderNames = req.getHeaderNames();
		while (allHeaderNames.hasMoreElements()) {
			String headerName = allHeaderNames.nextElement();
			resp.getWriter().println("Key: " + headerName + "; Value: " + req.getHeader(headerName));
		}
	}

}
