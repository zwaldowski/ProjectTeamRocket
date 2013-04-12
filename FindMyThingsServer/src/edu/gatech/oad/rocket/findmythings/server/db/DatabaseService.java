package edu.gatech.oad.rocket.findmythings.server.db;

import com.googlecode.objectify.*;
import com.googlecode.objectify.util.cmd.ObjectifyWrapper;
import edu.gatech.oad.rocket.findmythings.server.db.model.*;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class DatabaseService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(DatabaseService.class.getName());

	private static final long REGISTRATION_VALID_DAYS = 1;

	static {
		ObjectifyService.setFactory(new DatabaseFactory());
	}

	public static class DatabaseFactory extends ObjectifyFactory {

		/** Register our entity types*/
		public DatabaseFactory() {
			super();
			register(DBItem.class);
			register(DBMember.class);
			register(DBUserCounter.class);
			register(DBRegistrationTicket.class);
			register(DBAuthenticationToken.class);
		}

		@Override
		public Database begin() {
			return new Database(super.begin());
		}

	}

	/**
	 * @return our extension to Objectify
	 */
	public static Database ofy() {
		return (Database)ObjectifyService.ofy();
	}

	public static class Database extends ObjectifyWrapper<Database, DatabaseFactory>
	{
		public Database(Objectify base) {
			super(base);
		}

		private void changeUserCount(final long delta) {
			transact(new VoidWork() {
				@Override
				public void vrun() {
					DBUserCounter th = userCounter();
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
		 * @param email the user name for the code
		 */
		public void register(final String code, final String email) {
			register(memberWithEmail(email), code);
		}

		public void register(final DBMember user, final String code) {
			if (user != null) {
				boolean wasRegistered = user.isRegistered();
				user.register();
				updateMember(user, !wasRegistered);
			}
			deleteRegistrationTicket(code);
		}

		/**
		 * Save member with authorization information
		 * @param user  Member instance (required)
		 * @param password New password for the user (optional, null not allowed)
		 */
		public void updateMember(DBMember user, String password) {
			if (user == null) return;
			if (password != null) user.setPassword(password);
			ofy().save().entity(user);
		}

		/**
		 * Save user with authorization information
		 * @param user  User
		 * @param changeCount should the user count be incremented
		 */
		public void updateMember(DBMember user, boolean changeCount) {
			ofy().save().entity(user);
			if (changeCount) {
				changeUserCount(1);
			}
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

		public String emailFromRegistrationCode(String code) {
			DBRegistrationTicket reg = load().type(DBRegistrationTicket.class).id(code).get();
			return (reg == null) ?  null : (reg.isValid() ? reg.getEmail() : null);
		}

		public String emailFromAuthenticationToken(String token) {
			DBAuthenticationToken auth = load().type(DBAuthenticationToken.class).id(token).get();
			return (auth == null) ? null : auth.getEmail();
		}

		public DBMember memberFromAuthenticationToken(String token) {
			DBAuthenticationToken auth = load().type(DBAuthenticationToken.class).id(token).get();
			return (auth == null) ? null : load().type(DBMember.class).id(auth.getEmail()).get();
		}

		public DBMember memberWithEmail(String email) {
			return load().type(DBMember.class).id(email).get();
		}

		DBUserCounter userCounter() {
			return load().type(DBUserCounter.class).id(DBUserCounter.COUNTER_ID).get();
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

		/** Creation methods **/

		private static final RandomNumberGenerator magic = new SecureRandomNumberGenerator();

		public String createRegistrationTicket(String email) {
			ByteSource salt = magic.nextBytes();
			String ticket = new Sha256Hash(email, new SimpleByteSource(salt), 63).toHex().substring(0,10);
			DBRegistrationTicket reg = new DBRegistrationTicket(ticket, email, REGISTRATION_VALID_DAYS, TimeUnit.DAYS);
			save().entity(reg);
			return ticket;
		}

		public String createAuthenticationToken(String email) {
			DBAuthenticationToken auth = new DBAuthenticationToken(email);
			save().entity(auth);
			return auth.getIdentifierString();
		}

		public void deleteRegistrationTicket(String ticket) {
			delete().type(DBRegistrationTicket.class).id(ticket);
		}

		public void deleteAuthenticationToken(String token) {
			delete().type(DBAuthenticationToken.class).id(token);
		}

		public void deleteAuthenticationTokens(String... tokens) {
			delete().type(DBAuthenticationToken.class).ids(tokens);
		}

		public void deleteAuthenticationTokens(Collection<String> tokens) {
			delete().type(DBAuthenticationToken.class).ids(tokens);
		}

	}
}
