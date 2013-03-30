package edu.gatech.oad.rocket.findmythings.server.db;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Loader;
import com.googlecode.objectify.util.cmd.LoaderWrapper;
import com.googlecode.objectify.util.cmd.ObjectifyWrapper;

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
	public static DatabaseManager ofy() {
		return (DatabaseManager)ObjectifyService.ofy();
	}

	/**
	 * @return our extension to ObjectifyFactory
	 */
	public static DatabaseFactory factory() {
		return (DatabaseFactory)ObjectifyService.factory();
	}

	public static class DatabaseManager extends ObjectifyWrapper<DatabaseManager, DatabaseFactory> {

		public DatabaseManager(Objectify ofy) {
			super(ofy);
		}

		@Override
		public DatabaseLoader load() {
			return new DatabaseLoader(super.load());
		}

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

		@Override
		public DatabaseManager begin() {
			return new DatabaseManager(super.begin());
		}

	}

	public static class DatabaseLoader extends LoaderWrapper<DatabaseLoader> {

		public DatabaseLoader(Loader base) {
			super(base);
		}

		// extend loader with logic, like finding certain kinds of stuff

	}
}
