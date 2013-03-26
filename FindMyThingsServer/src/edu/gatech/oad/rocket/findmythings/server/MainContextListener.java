package edu.gatech.oad.rocket.findmythings.server;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.aop.ShiroAopModule;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class MainContextListener extends GuiceServletContextListener {

	private ServletContext servletContext = null;
	private ShiroWebModule securityModule = null;

	public MainContextListener() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext = servletContextEvent.getServletContext();
		securityModule = getSecurityModule(servletContext);

		super.contextInitialized(servletContextEvent);
	}

	@Override
	protected Injector getInjector() {
		//return Guice.createInjector(securityModule, new ShiroAopModule(), new MainServletModule());
		return Guice.createInjector(new ShiroAopModule(), securityModule, ShiroWebModule.guiceFilterModule(), new ServletModule(){
			@Override protected void configureServlets() {
				serve("/index.html").with(HelloWorldServlet.class);
		        serve("/login").with(LoginServlet.class);
			}
		});
		//securityModule, ShiroWebModule.guiceFilterModule(), new MainServletModule());
	}
	
	protected ShiroWebModule getSecurityModule(ServletContext sc) {
		return new ShiroWebModule(sc) {

			@SuppressWarnings("unchecked")
			@Override
			protected void configureShiroWeb() {
				//bindRealm().to(DatastoreRealm.class);
				
				// set the login redirect URL
				// -- is this only for web sites?
				bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/login");
				
				// binds the built-in users from shiro.ini
				try {
	                bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
	            } catch (NoSuchMethodException e) {
	                addError(e);
	            }
				
				// bind all password matching to the secure password hash
			    bind(CredentialsMatcher.class).to(PasswordMatcher.class);
			    bind(PasswordMatcher.class);
				
			    // require authentication on a couple of our test endpoints
				addFilterChain("/login", AUTHC);
				addFilterChain("/logout", LOGOUT);
				addFilterChain("/settings", AUTHC);
				addFilterChain("/account/**", AUTHC);
				addFilterChain("/listUsers", AUTHC, config(ROLES, "admin"));

			}
			
			@Provides
			@Singleton
			Ini loadShiroIni() throws MalformedURLException {
				URL iniUrl = servletContext.getResource("/WEB-INF/shiro.ini");
				return Ini.fromResourcePath("url:" + iniUrl.toExternalForm());
			}
			
		};
	}

}
