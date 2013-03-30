package edu.gatech.oad.rocket.findmythings.server;

import java.util.logging.Logger;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;

import com.google.common.base.Preconditions;

import edu.gatech.oad.rocket.findmythings.server.db.*;
import edu.gatech.oad.rocket.findmythings.server.model.*;

public class DatabaseRealm extends AuthorizingRealm {
	private static final Logger LOG = Logger.getLogger(DatabaseRealm.class.getName());

	public DatabaseRealm() {
		super(new MemcacheManager(), AppMember.getCredentialsMatcher());
		LOG.fine("Creating a new instance of DatabaseRealm");
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Preconditions.checkNotNull(principals, "You can't have a null collection of principals. No really, how did you do that");
		String userEmail = (String)getAvailablePrincipal(principals);
		if (userEmail == null) {
			throw new NullPointerException("Can't find a principal in the collection");
		}
		LOG.fine("Finding authorization info for " + userEmail + " in DB");
		AppMember member = DatabaseService.ofy().load().memberWithEmail(userEmail);
		if (member == null || !memberCanLogIn(member)) {
			LOG.info("Rejecting user " + member.getName());
			return null;
		}

		LOG.fine("Found " + userEmail + " in DB");

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(member.getRoles());
		info.setStringPermissions(member.getPermissions());
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

	protected AuthenticationInfo doGetAuthenticationInfo(String email) throws AuthenticationException {
		Preconditions.checkNotNull(email, "Email can't be null");
		LOG.info("Finding authentication info for " + email + " in DB");

		AppMember member = DatabaseService.ofy().load().memberWithEmail(email);
		if (member == null || !memberCanLogIn(member)) {
			LOG.info("Rejecting user " + member.getName());
			return null;
		}

		SimpleAccount account = new SimpleAccount(member.getEmail(), member.getHashedPassword(), new SimpleByteSource(member.getSalt()), getName());
		account.setRoles(member.getRoles());
		account.setStringPermissions(member.getPermissions());
		return account;
	}

	private static boolean memberCanLogIn(AppMember member) {
		return member.isRegistered() && !member.isLocked();
	}

}