package edu.gatech.oad.rocket.findmythings.server.security;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authz.SimpleRole;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.config.Ini;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.PhoneNumber;
import com.google.inject.Inject;

import edu.gatech.oad.rocket.findmythings.server.model.AppAdmin;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.model.AppUser;

public class ProfileIniRealm extends IniRealm implements ProfileRealm {

	@SuppressWarnings("unused")
	private static transient final Logger LOG = LoggerFactory.getLogger(ProfileIniRealm.class);

	public static final String PROFILES_SECTION_NAME = "profiles";

	private abstract class IniMemberAccount extends SimpleAccount implements AppMember {

		private static final long serialVersionUID = -1836831286248630414L;
		private String name = null;
		private PhoneNumber phone = null;
		private String address = null;

		IniMemberAccount(String principal, String credentials, String realmName) {
			super((Object)principal, (Object)credentials, realmName);
		}

		@Override
		public boolean isRegistered() {
			return true;
		}

		@Override
		public String getEmail() {
			return (String)this.getPrincipals().getPrimaryPrincipal();
		}

		@Override
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		private void setName(String name) {
			this.name = name;
		}

		@Override
		public PhoneNumber getPhone() {
			return phone;
		}

		/**
		 * @param phone the phone to set
		 */
		private void setPhone(PhoneNumber phone) {
			this.phone = phone;
		}

		@Override
		public String getAddress() {
			return address;
		}

		/**
		 * @param address the address to set
		 */
		private void setAddress(String address) {
			this.address = address;
		}

	}

	private class IniAdminAccount extends IniMemberAccount implements AppAdmin {

		/**
		 *
		 */
		private static final long serialVersionUID = 9086707738437486637L;

		public IniAdminAccount(String email, String password, String realmName) {
			super(email, password, realmName);
		}

		@Override
		public boolean isAdmin() {
			return true;
		}

	}

	private class IniUserAccount extends IniMemberAccount implements AppUser {

		/**
		 *
		 */
		private static final long serialVersionUID = -5037213037512773118L;

		public IniUserAccount(String email, String password, String realmName) {
			super(email, password, realmName);
		}

		@Override
		public boolean isAdmin() {
			return false;
		}

	}

	public ProfileIniRealm() {
		super();
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		setCredentialsMatcher(new PasswordMatcher());
	}

	@Inject
	public ProfileIniRealm(Ini ini) {
		this();
		setIni(ini);
		init();
	}

    public ProfileIniRealm(String resourcePath) {
	super(resourcePath);
    }

    @Override
    protected void processUserDefinitions(Map<String, String> userDefs) {
    	if (userDefs == null || userDefs.isEmpty()) {
    		return;
    	}

    	Ini.Section profilesSection = getIni().getSection(PROFILES_SECTION_NAME);

    	for (String email : userDefs.keySet()) {
    		String value = userDefs.get(email);
    		String[] passwordAndRolesArray = StringUtils.split(value);
    		String password = passwordAndRolesArray[0];

    		String profileValue = profilesSection.get(email);
    		String[] profileValuesArray = null;
    		String name = null;
    		PhoneNumber phone = null;
    		String address = null;

    		if (profileValue != null) {
    			profileValuesArray = StringUtils.split(profileValue, ',', '"', '"', false, true);
    			if (profileValuesArray.length == 3) {
    				name = profileValuesArray[0];
    				phone = profileValuesArray[1] == null ? null : new PhoneNumber(profileValuesArray[1]);
    				address = profileValuesArray[2];
    			}
    		}

    		SimpleAccount account = getUser(email);

    		if (account != null &&
    				(name != null || phone != null || address != null) &&
    				!(account instanceof IniMemberAccount)) {
    			this.users.remove(email);
    			account = null;
    		}

    		Set<String> roles = null;
    		Set<Permission> permissions = null;

    		if (passwordAndRolesArray.length > 1) {
    			for (int i = 1; i < passwordAndRolesArray.length; i++) {
    				if (roles == null) roles = new HashSet<>();
    				String rolename = passwordAndRolesArray[i];
    				roles.add(rolename);

    				SimpleRole role = getRole(rolename);
    				if (role != null) {
    					if (permissions == null) permissions = new HashSet<>();
    					permissions.addAll(role.getPermissions());
    				}
    			}
    		}

    		if (account == null) {
    			if (roles.contains("admin")) {
    				account = new IniAdminAccount(email, password, getName());
    			} else {
    				account = new IniUserAccount(email, password, getName());
    			}
    		}

    		account.setCredentials(password);
    		account.setRoles(roles);
    		if (permissions != null) account.addObjectPermissions(permissions);

    		if (account instanceof IniMemberAccount) {
    			((IniMemberAccount) account).setName(name);
    			((IniMemberAccount) account).setPhone(phone);
    			((IniMemberAccount) account).setAddress(address);
    		}
    		
    		add(account);
    	}
    }

    @Override
	public boolean accountExists(String email) {
	if (email == null) return false;
		return getUser(email) != null;
	}

    @Override
    public AppMember getAccount(String email) {
	if (email == null) return null;
	SimpleAccount account = getUser(email);
	if (account instanceof AppMember)
		return (AppMember)account;
	return null;
    }

}
