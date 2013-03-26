package edu.gatech.oad.rocket.findmythings.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MainServletContextListener extends GuiceServletContextListener {

	public MainServletContextListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new MainServletModule());
	}

}
