package edu.gatech.oad.rocket.findmythings.server.db;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.cmd.Deleter;
import com.googlecode.objectify.cmd.Loader;
import com.googlecode.objectify.cmd.Saver;
import com.googlecode.objectify.util.cmd.DeleterWrapper;
import com.googlecode.objectify.util.cmd.LoaderWrapper;
import com.googlecode.objectify.util.cmd.ObjectifyWrapper;
import com.googlecode.objectify.util.cmd.SaverWrapper;

import edu.gatech.oad.rocket.findmythings.server.db.model.AppAdmin;
import edu.gatech.oad.rocket.findmythings.server.db.model.AppAuthenticationToken;
import edu.gatech.oad.rocket.findmythings.server.db.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.db.model.AppUser;
import edu.gatech.oad.rocket.findmythings.server.db.model.AppUserCounter;
import edu.gatech.oad.rocket.findmythings.server.db.model.RegistrationTicket;

public abstract class DatabaseService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(DatabaseService.class.getName());

	private static final long REGISTRATION_VALID_DAYS = 1;

	static {
		ObjectifyService.setFactory(new DatabaseFactory());
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
			register(AppMember.class);
			register(AppUser.class);
			register(AppAdmin.class);
			register(AppAdmin.class);
			register(RegistrationTicket.class);
			register(AppAuthenticationToken.class);
		}

		/** Use guice to make instances instead! */
		@Override
		public <T> T construct(Class<T> type) {
			return injector.getInstance(type);
		}

		@Override
		public Database begin() {
			return new Database(super.begin());
		}

	}

	/**
	 * @return our extension to Objectify
	 */
	public static final Database ofy() {
		return (Database)ObjectifyService.ofy();
	}

	public static class Database extends ObjectifyWrapper<Database, DatabaseFactory>
	{
		public Database(Objectify base) {
			super(base);
		}

		/** More wrappers, fun */
		@Override
		public DatabaseLoader load() {
			return new DatabaseLoader(super.load());
		}
		@Override
		public DatabaseSaver save() {
			return new DatabaseSaver(super.save());
		}
		@Override
		public DatabaseDeleter delete() {
			return new DatabaseDeleter(super.delete());
		}

		private void changeUserCount(final long delta) {
			transact(new VoidWork() {
				@Override
				public void vrun() {
					AppUserCounter th = load().userCounter();
					if (th == null) {
						th = new AppUserCounter();
					}
					th.delta(delta);
					save().entity(th);
				}
			});
		}

		/**
		 * Given a registration we have to retrieve it, and if its valid
		 * update the associated user and then delete the registration.  This isn't
		 * transactional and we may end up with a dangling RegistrationString, which
		 * I can't see as too much of a problem, although they will need to be cleaned up with
		 * a task on a regular basis (after they expire)..
		 * @param code  The registration code
		 * @param userName the user name for the code
		 */
		public void register(final String code, final String email) {
			AppMember user = load().memberWithEmail(email);
			if (user != null) {
				user.register();
				save().entity(user);
			}
			delete().registrationTicketWithCode(code);
		}

		/**
		 * Save user with authorization information
		 * @param user  User
		 * @param changeCount should the user count be incremented
		 * @return the user, after changes
		 */
		public AppMember updateMember(AppMember user, boolean changeCount) {
			ofy().save().entity(user);
			if (changeCount) {
				changeUserCount(1);
			}
			return user;
		}

		public AppMember deleteMember(AppMember user) {
			ofy().delete().entity(user);
			changeUserCount(-1L);
			return user;
		}

		public void deleteAuthenticationTokensForEmail(String userEmail) {
			if (userEmail == null) return;
			Iterable<Key<AppAuthenticationToken>> allKeys = ofy().load().type(AppAuthenticationToken.class).filter("email", userEmail).keys();
			ofy().delete().keys(allKeys);
		}
	}

	public static class DatabaseLoader extends LoaderWrapper<DatabaseLoader> {

		public DatabaseLoader(Loader base) {
			super(base);
		}

		public String emailFromRegistrationCode(String code) {
			RegistrationTicket reg = type(RegistrationTicket.class).id(code).get();
			return (reg == null) ?  null : (reg.isValid() ? reg.getEmail() : null);
		}

		public String emailFromAuthenticationToken(String token) {
			AppAuthenticationToken auth = type(AppAuthenticationToken.class).id(token).get();
			return (auth == null) ? null : auth.getEmail();
		}

		public AppMember memberFromAuthenticationToken(String token) {
			AppAuthenticationToken auth = type(AppAuthenticationToken.class).id(token).get();
			return (auth == null) ? null : type(AppMember.class).id(auth.getEmail()).get();
		}

		public AppMember memberWithEmail(String email) {
			return type(AppMember.class).id(email).get();
		}

		protected AppUserCounter userCounter() {
			return type(AppUserCounter.class).id(AppUserCounter.COUNTER_ID).get();
		}

		public long getUserCount() {
			AppUserCounter th = userCounter();
			if (th == null) return 0;
			return th.getCount();
		}

		public Date getCountLastModified() {
			AppUserCounter th = userCounter();
			if (th == null) return new Date(0L);
			return th.getLastModified();
		}

	}

	public static class DatabaseSaver extends SaverWrapper {

		public DatabaseSaver(Saver saver) {
			super(saver);
		}

		public RegistrationTicket registrationTicket(String ticket, String email) {
			RegistrationTicket reg = new RegistrationTicket(ticket, email, REGISTRATION_VALID_DAYS, TimeUnit.DAYS);
			ofy().save().entity(reg);
			return reg;
		}

		public String authenticationToken(String email) {
			AppAuthenticationToken auth = new AppAuthenticationToken(email);
			ofy().save().entity(auth);
			return auth.getIdentifierString();
		}

	}

	public static class DatabaseDeleter extends DeleterWrapper {

		public DatabaseDeleter(Deleter deleter) {
			super(deleter);
		}

		public void registrationTicketWithCode(String ticket) {
			type(RegistrationTicket.class).id(ticket);
		}

		public void authenticationTokenWithCode(String token) {
			type(AppAuthenticationToken.class).id(token);
		}

		public void authenticationTokenWithCodes(String... tokens) {
			type(AppAuthenticationToken.class).ids(tokens);
		}

	}
}
