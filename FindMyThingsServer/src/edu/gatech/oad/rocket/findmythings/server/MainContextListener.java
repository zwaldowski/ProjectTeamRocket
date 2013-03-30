package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.mgt.WebSecurityManager;

import com.google.common.base.Charsets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

import edu.gatech.oad.rocket.findmythings.server.web.*;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService.DatabaseFactory;

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

	protected PageGenerator createPageGenerator() {
		try {
			URL base = servletContext.getResource("/WEB-INF/templates/");
            return new PageGenerator(base, Locale.getDefault(), Charsets.UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}

	private class MainServletModule extends ServletModule {
	    private void bindString(String key, String value) {
	        bind(String.class).annotatedWith(Names.named(key)).toInstance(value);
	    }

		@Override protected void configureServlets() {
			filter("/*").through(ObjectifyFilter.class);
	        bind(PageGenerator.class).toInstance(createPageGenerator());
	        bindString("email.from", Config.APP_EMAIL);
			serve("/index.html").with(HelloWorldServlet.class);
	        serve("/login.jsp").with(LoginServlet.class);
	        serve("/authtest.jsp").with(AuthTestServlet.class);
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
			bindMainWebSecurityManager(bind(MainWebSecurityManager.class));

			//bindRealm().to(DatastoreRealm.class);

			// binds the built-in users from shiro.ini
			try {
                bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
            } catch (NoSuchMethodException e) {
                addError(e);
            }

			bind(AuthenticationFilter.class).to(WebAuthenticationFilter.class);
			Key<WebAuthenticationFilter> loginFormAuth = Key.get(WebAuthenticationFilter.class);

			addFilterChain("/authtest.jsp", ANON);
			addFilterChain("/login.jsp", loginFormAuth);
			addFilterChain("/logout.jsp", LOGOUT);
			addFilterChain("/account/**", loginFormAuth);
			addFilterChain("/admin/**", loginFormAuth, config(ROLES, "admin"));

			// set the login redirect URL
			bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/login.jsp");
			bindConstant().annotatedWith(Names.named("shiro.successUrl")).to("/index.html");

			// bind all password matching to the secure password hash
		    bind(CredentialsMatcher.class).to(PasswordMatcher.class);
		    bind(PasswordMatcher.class);
		}

	    @Override
	    protected final void bindWebSecurityManager(AnnotatedBindingBuilder<? super WebSecurityManager> bind) {
		bindMainWebSecurityManager(bind);
	    }

	    protected void bindMainWebSecurityManager(AnnotatedBindingBuilder<? super MainWebSecurityManager> bind) {
	        try {
	            bind.toConstructor(MainWebSecurityManager.class.getConstructor(Collection.class)).asEagerSingleton();
	        } catch (NoSuchMethodException e) {
	            throw new ConfigurationException("This really shouldn't happen.  Either something has changed in Shiro, or there's a bug in ShiroModule.", e);
	        }
	    }

		@Provides
		Ini loadShiroIni() throws MalformedURLException {
			URL iniUrl = servletContext.getResource("/WEB-INF/shiro.ini");
			return Ini.fromResourcePath("url:" + iniUrl.toExternalForm());
		}

	}

}
