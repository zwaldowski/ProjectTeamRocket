package edu.gatech.oad.rocket.findmythings.server.db;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import edu.gatech.oad.rocket.findmythings.server.model.AppAdmin;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.model.AppUser;
import edu.gatech.oad.rocket.findmythings.server.model.RegistrationTicket;

public abstract class DatabaseService {

	static {
		ObjectifyService.setFactory(new DatabaseFactory());
	}

	/**
	 * @return our extension to Objectify
	 */
	public static final Objectify ofy() {
		return ObjectifyService.ofy();
	}

	/**
	 * @return our extension to ObjectifyFactory
	 */
	public static DatabaseFactory factory() {
		return (DatabaseFactory)ObjectifyService.factory();
	}

	@Singleton
	public static class DatabaseFactory extends ObjectifyFactory {

		@Inject private static Injector injector;

		/** Register our entity types*/
		public DatabaseFactory() {
			this.register(AppMember.class);
			this.register(AppUser.class);
			this.register(AppAdmin.class);
			this.register(AppAdmin.class);
			this.register(RegistrationTicket.class);
		}

		/** Use guice to make instances instead! */
		@Override
		public <T> T construct(Class<T> type) {
			return injector.getInstance(type);
		}

	}
}
