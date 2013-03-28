package edu.gatech.oad.rocket.findmythings.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import com.google.common.base.Charsets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import edu.gatech.oad.rocket.findmythings.server.web.*;

public class MainContextListener extends GuiceServletContextListener {

	public static final Key<WebAuthenticationFilter> WEBAUTH = Key.get(WebAuthenticationFilter.class);

    private ServletContext servletContext = null;
	private ShiroWebModule securityModule = null;

	public MainContextListener() {
		// TODO Auto-generated constructor stub
	}
	
	private class MainServletModule extends ServletModule {
	    private void bindString(String key, String value) {
	        bind(String.class).annotatedWith(Names.named(key)).toInstance(value);
	    }

		@Override protected void configureServlets() {
	        bind(PageGenerator.class).toInstance(createPageGenerator());
	        bindString("email.from", Config.APP_EMAIL);
			serve("/index.html").with(HelloWorldServlet.class);
	        serve("/login.jsp").with(LoginServlet.class);
	        serve("/authtest.jsp").with(AuthTestServlet.class);
		}

	}

	private class MainSessionStorageEvaluator implements SessionStorageEvaluator {

		@Override
		public boolean isSessionStorageEnabled(Subject arg0) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	private class MainShiroWebModule extends ShiroWebModule {

		public MainShiroWebModule(ServletContext servletContext) {
			super(servletContext);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void configureShiroWeb() {
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

		@Provides
		Ini loadShiroIni() throws MalformedURLException {
			URL iniUrl = servletContext.getResource("/WEB-INF/shiro.ini");
			return Ini.fromResourcePath("url:" + iniUrl.toExternalForm());
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext = servletContextEvent.getServletContext();
		securityModule = new MainShiroWebModule(servletContext);

		super.contextInitialized(servletContextEvent);
	}

	@Override
	protected Injector getInjector() {
		Injector inj = Guice.createInjector(securityModule, ShiroWebModule.guiceFilterModule(), new MainServletModule());

		// assuming the Shiro defaults
	    DefaultWebSecurityManager securityManager = inj.getInstance(DefaultWebSecurityManager.class);
	    DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO)securityManager.getSubjectDAO();
	    subjectDAO.setSessionStorageEvaluator(new MainSessionStorageEvaluator());

	    SecurityUtils.setSecurityManager(securityManager);
		return inj;
	}
	
	protected PageGenerator createPageGenerator() {
		try {
			URL base = servletContext.getResource("/WEB-INF/templates/");
            return new PageGenerator(base, Locale.getDefault(), Charsets.UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	protected WebAuthenticationFilter createWebAuthFilter() {
		WebAuthenticationFilter out = new WebAuthenticationFilter();
		out.setLoginUrl("/login");
		return out;
	}

}
