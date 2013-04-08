package edu.gatech.oad.rocket.findmythings.server.spi;

import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.security.ProfileRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;

abstract class BaseEndpoint {

	AppMember getMemberWithEmail(String email) {
		RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		for (Realm realm : manager.getRealms()) {
			if (realm instanceof ProfileRealm) {
				AppMember potential = ((ProfileRealm) realm).getAccount(email);
				if (potential != null && potential.getEmail().equals(email)) return potential;
			}
		}
		return null;
	}

	boolean memberExistsWithEmail(String email) {
		if (email == null || email.length() == 0) return false;
		RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		for (Realm realm : manager.getRealms()) {
			if (realm instanceof ProfileRealm) {
				if (((ProfileRealm) realm).accountExists(email)) return true;
			}
		}
		return false;
	}

}
