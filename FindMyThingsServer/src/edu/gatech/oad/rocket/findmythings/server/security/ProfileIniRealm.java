package edu.gatech.oad.rocket.findmythings.server.security;

import com.google.appengine.api.datastore.PhoneNumber;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authz.SimpleRole;
import org.apache.shiro.config.Ini;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.util.StringUtils;

import java.util.*;
import java.util.logging.Logger;

public class ProfileIniRealm extends IniRealm implements ProfileRealm {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ProfileIniRealm.class.getName());

	private static final String PROFILES_SECTION_NAME = "profiles";

	@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnoreProperties({"objectPermissions", "credentialsExpired", "credentials", "principals"})
	private class IniAccount extends SimpleAccount implements AppMember {

		private static final long serialVersionUID = -1836831286248630414L;
		private String name = null;
		private PhoneNumber phone = null;
		private String address = null;
		private boolean isAdmin = false;

		IniAccount(String principal, String credentials, String realmName) {
			super(principal, credentials, realmName);
		}

		@Override
		public boolean isRegistered() {
			return true;
		}

		@Override
		public boolean isEditable() {
			return false;
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

		public boolean isAdmin() {
			return isAdmin;
		}

		private void setIsAdmin(boolean isAdmin) {
			this.isAdmin = isAdmin;
		}

	}

	public ProfileIniRealm() {
		super();
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		setCredentialsMatcher(new PasswordMatcher());
	}

	public ProfileIniRealm(Ini ini) {
		this();
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		setCredentialsMatcher(new PasswordMatcher());
		setIni(ini);
	}

	@Override
	protected void processUserDefinitions(Map<String, String> users) {
		if (users == null || users.isEmpty()) {
			return;
		}

		Ini.Section profilesSection = getIni().getSection(PROFILES_SECTION_NAME);

		for (String email : users.keySet()) {
			String value = users.get(email);

			String[] passwordAndRolesArray = StringUtils.split(value);

			String password = passwordAndRolesArray[0];

			String profileValue = profilesSection.get(email);
			String[] profileValuesArray;
			String name = null;
			PhoneNumber phone = null;
			String address = null;
			boolean hasProfile = false;

			if (profileValue != null) {
				profileValuesArray = StringUtils.split(profileValue, ',', '"', '"', false, true);
				if (profileValuesArray.length == 3) {
					name = profileValuesArray[0];
					phone = profileValuesArray[1] == null ? null : new PhoneNumber(profileValuesArray[1]);
					address = profileValuesArray[2];
				}
				hasProfile = (name != null || phone != null || address != null);
			}

			SimpleAccount account = getUser(email);

			if (hasProfile && !(account instanceof IniAccount)) {
				this.users.remove(email);
				account = null;
			}

			if (account == null) {
				account = new IniAccount(email, password, getName());
				add(account);
			}

			account.setCredentials(password);
			if (hasProfile) {
				((IniAccount) account).setName(name);
				((IniAccount) account).setPhone(phone);
				((IniAccount) account).setAddress(address);
			}

			if (passwordAndRolesArray.length > 1) {
				for (int i = 1; i < passwordAndRolesArray.length; i++) {
					String rolename = passwordAndRolesArray[i];
					account.addRole(rolename);

					SimpleRole role = getRole(rolename);
					if (role != null) {
						account.addObjectPermissions(role.getPermissions());
					}
				}
			} else {
				account.setRoles(null);
			}

			if (hasProfile) {
				((IniAccount) account).setIsAdmin(account.getRoles().contains("admin"));
			}
		}
	}

	@Override
	public boolean accountExists(String email) {
		return email != null && getUser(email) != null;
	}

	@Override
	public AppMember getAccount(String email) {
		if (email == null) return null;
		SimpleAccount account = getUser(email);
		if (account instanceof AppMember)
			return (AppMember)account;
		return null;
	}

	public Collection<? extends AppMember> getMembers() {
		Collection<SimpleAccount> accounts = this.users.values();
		Collection<AppMember> ret = new ArrayList<>(accounts.size());
		for (SimpleAccount e : accounts) {
			if (e instanceof AppMember) {
				ret.add((AppMember)e);
			}
		}
		return ret;
	}

}
