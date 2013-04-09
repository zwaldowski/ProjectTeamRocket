package edu.gatech.oad.rocket.findmythings.server.security;

import com.google.common.base.Preconditions;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.MemcacheManager;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBMember;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.util.HashHelper;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;

import java.util.HashSet;
import java.util.logging.Logger;

public class DatabaseRealm extends AuthorizingRealm implements ProfileRealm {
	private static final Logger LOG = Logger.getLogger(DatabaseRealm.class.getName());

	public DatabaseRealm() {
		super(new MemcacheManager(), HashHelper.getCredentialsMatcher());
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		LOG.fine("Creating a new instance of DatabaseRealm");
	}

	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Preconditions.checkNotNull(principals, "You can't have a null collection of principals. No really, how did you do that");
		String userEmail = (String)getAvailablePrincipal(principals);
		if (userEmail == null) {
			throw new NullPointerException("Can't find a principal in the collection");
		}
		LOG.fine("Finding authorization info for " + userEmail + " in DB");
		DBMember member = DatabaseService.ofy().memberWithEmail(userEmail);
		if (memberCannotLogIn(member)) {
			LOG.info("Rejecting user " + userEmail);
			return null;
		}

		LOG.fine("Found " + userEmail + " in DB");

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(member.getRoles());
		info.addStringPermissions(member.getStringPermissions());
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if (token instanceof UsernamePasswordToken) {
			String userEmail = ((UsernamePasswordToken) token).getUsername();
			return doGetAuthenticationInfo(userEmail);
		}
		throw new UnsupportedOperationException("Implement another method of getting user email.");
	}

	AuthenticationInfo doGetAuthenticationInfo(String email) throws AuthenticationException {
		Preconditions.checkNotNull(email, "Email can't be null");
		LOG.info("Finding authentication info for " + email + " in DB");

		DBMember member = DatabaseService.ofy().memberWithEmail(email);
		if (memberCannotLogIn(member)) {
			LOG.info("Rejecting user " + email);
			return null;
		}

		SimpleAccount account = new SimpleAccount(member.getEmail(), member.getHashedPassword(), new SimpleByteSource(member.getSalt()), getName());
		account.setRoles(new HashSet<>(member.getRoles()));
		account.setStringPermissions(new HashSet<>(member.getStringPermissions()));
		return account;
	}

	private static boolean memberCannotLogIn(AppMember member) {
		return member == null || !member.isRegistered() || member.isLocked();
	}

	@Override
	public boolean accountExists(String email) {
		return email != null && DatabaseService.ofy().load().type(DBMember.class).id(email) != null;
	}

	@Override
	public AppMember getAccount(String email) {
		if (email == null) return null;
		return DatabaseService.ofy().load().type(DBMember.class).id(email).get();
	}

}
