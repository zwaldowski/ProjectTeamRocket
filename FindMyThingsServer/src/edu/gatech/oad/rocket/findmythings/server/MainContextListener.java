package edu.gatech.oad.rocket.findmythings.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import com.google.common.base.Charsets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

import edu.gatech.oad.rocket.findmythings.server.security.*;
import edu.gatech.oad.rocket.findmythings.server.util.*;
import edu.gatech.oad.rocket.findmythings.server.web.PageGenerator;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService.DatabaseFactory;
import edu.gatech.oad.rocket.findmythings.server.db.MemcacheManager;

public class MainContextListener extends GuiceServletContextListener {

	public static final Key<WebAuthenticationFilter> WEBAUTH = Key.get(WebAuthenticationFilter.class);

    private ServletContext servletContext = null;

	public MainContextListener() {}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
	}

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new MainShiroWebModule(servletContext), ShiroWebModule.guiceFilterModule(), new MainServletModule(), new MainModule());
	}

	private class MainServletModule extends ServletModule {
	    private void bindString(String key, String value) {
	        bind(String.class).annotatedWith(Names.named(key)).toInstance(value);
	    }
	    
	    private <T> void bindNamed(String key, Class<T> clazz, T value) {
	        bind(clazz).annotatedWith(Names.named(key)).toInstance(value);
	    }

		@Override protected void configureServlets() {
			filter("/*").through(ObjectifyFilter.class);
			
			try {
				bindNamed(PageGenerator.TEMPLATES, URL.class, servletContext.getResource("/WEB-INF/templates/"));
				bindNamed(PageGenerator.LOCALE, Locale.class, Locale.getDefault());
				bindNamed(PageGenerator.CHARSET, Charset.class, Charsets.UTF_8);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			
	        bindString("email.from", Config.APP_EMAIL);
			serve("/index.html").with(TemplateServlet.class);
	        serve("/authtest.jsp").with(TemplateServlet.class);
	        serve("/login.jsp").with(LoginServlet.class);
		}

	}

	private static class MainModule extends AbstractModule {
		@Override
		protected void configure() {
			requestStaticInjection(DatabaseFactory.class);

			// External things that don't have Guice annotations
			bind(ObjectifyFilter.class).in(Singleton.class);
		}
	}

	private class MainShiroWebModule extends ShiroWebModule {

		public MainShiroWebModule(ServletContext servletContext) {
			super(servletContext);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void configureShiroWeb() {
            bind(SessionDAO.class).to(EnterpriseCacheSessionDAO.class);
            bind(CacheManager.class).to(MemcacheManager.class);

			bindRealm().to(BearerTokenAuthenticatingRealm.class);
			bindRealm().to(DatabaseRealm.class);

			// binds the built-in users from shiro.ini
			try {
                bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
            } catch (NoSuchMethodException e) {
                addError(e);
            }

			bind(AuthenticationFilter.class).to(WebAuthenticationFilter.class);
			Key<WebAuthenticationFilter> loginFormAuth = Key.get(WebAuthenticationFilter.class);

			// Always remember to define your filter chains based on a FIRST MATCH WINS policy!
			addFilterChain("/authtest.jsp", ANON);
			addFilterChain("/register.jsp", ANON);
			addFilterChain("/login.jsp", loginFormAuth);
			addFilterChain("/logout.jsp", LOGOUT);
			addFilterChain("/account/**", loginFormAuth);
			//addFilterChain("/api/login.jsp", NO_SESSION_CREATION);
			//addFilterChain("/api/logout.jsp", NO_SESSION_CREATION);
			//addFilterChain("/api/user/**", NO_SESSION_CREATION);
			addFilterChain("/api/**", NO_SESSION_CREATION, ANON);

			// set the login redirect URL
			bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/login.jsp");
			bindConstant().annotatedWith(Names.named("shiro.successUrl")).to("/index.html");

			// bind all password matching to the secure password hash
		    bind(CredentialsMatcher.class).to(PasswordMatcher.class);
		    bind(PasswordMatcher.class);
		}

		@Provides
		Ini loadShiroIni() throws MalformedURLException {
			URL iniUrl = servletContext.getResource("/WEB-INF/shiro.ini");
			return Ini.fromResourcePath("url:" + iniUrl.toExternalForm());
		}

	}

}
