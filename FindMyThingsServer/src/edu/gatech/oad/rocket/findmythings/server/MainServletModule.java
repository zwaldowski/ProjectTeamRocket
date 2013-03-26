package edu.gatech.oad.rocket.findmythings.server;

import com.google.inject.servlet.ServletModule;

public class MainServletModule extends ServletModule {

	@Override protected void configureServlets() {
		serve("/*").with(HelloWorldServlet.class);
	}

}
