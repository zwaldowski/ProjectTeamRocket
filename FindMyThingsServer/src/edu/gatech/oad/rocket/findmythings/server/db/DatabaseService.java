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

import edu.gatech.oad.rocket.findmythings.server.db.model.DBAdmin;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBAuthenticationToken;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBMember;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBUser;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBUserCounter;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBRegistrationTicket;

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
			register(DBMember.class);
			register(DBUser.class);
			register(DBAdmin.class);
			register(DBUserCounter.class);
			register(DBRegistrationTicket.class);
			register(DBAuthenticationToken.class);
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
					DBUserCounter th = load().userCounter();
					if (th == null) {
						th = new DBUserCounter();
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
			DBMember user = load().memberWithEmail(email);
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
		public DBMember updateMember(DBMember user, boolean changeCount) {
			ofy().save().entity(user);
			if (changeCount) {
				changeUserCount(1);
			}
			return user;
		}

		public DBMember deleteMember(DBMember user) {
			ofy().delete().entity(user);
			changeUserCount(-1L);
			return user;
		}

		public void deleteAuthenticationTokensForEmail(String userEmail) {
			if (userEmail == null) return;
			Iterable<Key<DBAuthenticationToken>> allKeys = ofy().load().type(DBAuthenticationToken.class).filter("email", userEmail).keys();
			ofy().delete().keys(allKeys);
		}
	}

	public static class DatabaseLoader extends LoaderWrapper<DatabaseLoader> {

		public DatabaseLoader(Loader base) {
			super(base);
		}

		public String emailFromRegistrationCode(String code) {
			DBRegistrationTicket reg = type(DBRegistrationTicket.class).id(code).get();
			return (reg == null) ?  null : (reg.isValid() ? reg.getEmail() : null);
		}

		public String emailFromAuthenticationToken(String token) {
			DBAuthenticationToken auth = type(DBAuthenticationToken.class).id(token).get();
			return (auth == null) ? null : auth.getEmail();
		}

		public DBMember memberFromAuthenticationToken(String token) {
			DBAuthenticationToken auth = type(DBAuthenticationToken.class).id(token).get();
			return (auth == null) ? null : type(DBMember.class).id(auth.getEmail()).get();
		}

		public DBMember memberWithEmail(String email) {
			return type(DBMember.class).id(email).get();
		}

		protected DBUserCounter userCounter() {
			return type(DBUserCounter.class).id(DBUserCounter.COUNTER_ID).get();
		}

		public long getUserCount() {
			DBUserCounter th = userCounter();
			if (th == null) return 0;
			return th.getCount();
		}

		public Date getCountLastModified() {
			DBUserCounter th = userCounter();
			if (th == null) return new Date(0L);
			return th.getLastModified();
		}

	}

	public static class DatabaseSaver extends SaverWrapper {

		public DatabaseSaver(Saver saver) {
			super(saver);
		}

		public DBRegistrationTicket registrationTicket(String ticket, String email) {
			DBRegistrationTicket reg = new DBRegistrationTicket(ticket, email, REGISTRATION_VALID_DAYS, TimeUnit.DAYS);
			ofy().save().entity(reg);
			return reg;
		}

		public String authenticationToken(String email) {
			DBAuthenticationToken auth = new DBAuthenticationToken(email);
			ofy().save().entity(auth);
			return auth.getIdentifierString();
		}

	}

	public static class DatabaseDeleter extends DeleterWrapper {

		public DatabaseDeleter(Deleter deleter) {
			super(deleter);
		}

		public void registrationTicketWithCode(String ticket) {
			type(DBRegistrationTicket.class).id(ticket);
		}

		public void authenticationTokenWithCode(String token) {
			type(DBAuthenticationToken.class).id(token);
		}

		public void authenticationTokenWithCodes(String... tokens) {
			type(DBAuthenticationToken.class).ids(tokens);
		}

	}
}
