package edu.gatech.oad.rocket.findmythings.server.security;

import com.google.common.base.Preconditions;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.MemcacheManager;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBAuthenticationToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.Collection;
import java.util.logging.Logger;

public class BearerTokenAuthenticatingRealm extends AuthenticatingRealm {
	private static final Logger LOGGER = Logger.getLogger(BearerTokenAuthenticatingRealm.class.getName());

	private class BearerAuthenticationInfo implements AuthenticationInfo {
		private static final long serialVersionUID = 3470761774414912759L;
		private final DBAuthenticationToken token;

		BearerAuthenticationInfo(DBAuthenticationToken token) {
			this.token = token;
		}

		@Override
		public Object getCredentials() {
			return token.getIdentifierString();
		}

		@Override
		public PrincipalCollection getPrincipals() {
			RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
			SimplePrincipalCollection ret = new SimplePrincipalCollection();
			for (Realm realm : manager.getRealms()) {
				if (realm instanceof ProfileRealm) {
					String email = token.getEmail();
					if (((ProfileRealm) realm).accountExists(email)) {
						ret.add(email, realm.getName());
					}
				}
			}
			ret.add(token.getIdentifierString(), getName());
			return ret;
		}

	}

	public BearerTokenAuthenticatingRealm() {
		super(new MemcacheManager(), new AllowAllCredentialsMatcher());
		setAuthenticationTokenClass(BearerToken.class);
	}

	private static boolean tokenIsInvalid(BearerToken token, DBAuthenticationToken dbToken) {
		return token == null || dbToken == null || !dbToken.getEmail().equals(token.getPrincipal());
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		BearerToken token = (BearerToken)arg0;
		String email = (String)token.getPrincipal();
		String credentials = (String)token.getCredentials();

		Preconditions.checkNotNull(email, "Email can't be null");
		Preconditions.checkNotNull(token, "Token can't be null");

		DBAuthenticationToken dbToken = DatabaseService.ofy().load().type(DBAuthenticationToken.class).id(credentials).get();
		if (tokenIsInvalid(token, dbToken)) {
			LOGGER.info("Rejecting token " + credentials + " for user " + email);
			return null;
		}

		return new BearerAuthenticationInfo(dbToken);
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
		super.onLogout(principals);
		deleteTokens(principals);
	}

	@SuppressWarnings("unchecked")
	private void deleteTokens(PrincipalCollection principals) {
		Collection<String> tokens = principals.fromRealm(getName());
		if (tokens != null) { //  && tokens.size() > 1
			DatabaseService.ofy().deleteAuthenticationTokens(tokens);
		}
	}

}
