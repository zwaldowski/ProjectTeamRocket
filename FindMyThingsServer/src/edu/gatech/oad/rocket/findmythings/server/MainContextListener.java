package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.aop.ShiroAopModule;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;

import com.google.common.base.Charsets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import edu.gatech.oad.rocket.findmythings.server.web.*;

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
		Injector inj = Guice.createInjector(new ShiroAopModule(), securityModule, ShiroWebModule.guiceFilterModule(), new ServletModule(){
		    private void bindString(String key, String value) {
		        bind(String.class).annotatedWith(Names.named(key)).toInstance(value);
		    }
		    
			@Override protected void configureServlets() {
		        bind(PageGenerator.class).toInstance(createPageGenerator());
		        bindString("email.from", Config.APP_EMAIL);
				serve("/index.html").with(HelloWorldServlet.class);
		        serve("/login").with(LoginServlet.class);
			}
		});
	    SecurityManager securityManager = inj.getInstance(SecurityManager.class);
	    SecurityUtils.setSecurityManager(securityManager);
		return inj;
	}
	
	private PageGenerator createPageGenerator() {
		try {
			URL base = servletContext.getResource("/WEB-INF/templates/");
            return new PageGenerator(base, Locale.getDefault(), Charsets.UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
				bindConstant().annotatedWith(Names.named("shiro.successUrl")).to("/index.html");
				
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
				addFilterChain("/account/**", AUTHC);
				addFilterChain("/admin/**", AUTHC, config(ROLES, "admin"));

			}
			
			@Provides @Singleton
			Ini loadShiroIni() throws MalformedURLException {
				URL iniUrl = servletContext.getResource("/WEB-INF/shiro.ini");
				return Ini.fromResourcePath("url:" + iniUrl.toExternalForm());
			}
			
		};
	}

}
