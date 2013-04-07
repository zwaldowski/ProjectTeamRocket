package edu.gatech.oad.rocket.findmythings.server.spi;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService;
import edu.gatech.oad.rocket.findmythings.server.db.DatabaseService.Database;
import edu.gatech.oad.rocket.findmythings.server.db.model.DBMember;
import edu.gatech.oad.rocket.findmythings.server.model.AppMember;
import edu.gatech.oad.rocket.findmythings.server.security.ProfileIniRealm;
import edu.gatech.oad.rocket.findmythings.server.security.ProfileRealm;

import javax.inject.Named;

@Api(name = "fmthings")
public class AccountV1 {
	
	@ApiMethod(name = "members.list", path = "members")
	public List<AppMember> listMembers() {
		Database mgr = DatabaseService.ofy();
		List<AppMember> retMembers = new ArrayList<>();
		
		List<DBMember> members = mgr.load().type(DBMember.class).list();
		retMembers.addAll(members);
		
		RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
	    for (Realm realm : manager.getRealms()) {
			if (realm instanceof ProfileIniRealm) {
				retMembers.addAll(((ProfileIniRealm) realm).getMembers());
			}
		}
	    return retMembers;
	}
	 
	 @ApiMethod(name = "members.getCurrent", path = "members/current")
	 public AppMember getCurrentMember() {
		 PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		 if (principals == null || principals.isEmpty()) return null;
		 String email = (String)principals.getPrimaryPrincipal();
		 return getMember(email);
	 }
	 
	 @ApiMethod(name = "members.get", path = "members/get")
	 public AppMember getMember(@Named("email") String email) {
		RealmSecurityManager manager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
	    for (Realm realm : manager.getRealms()) {
			if (realm instanceof ProfileRealm) {
				AppMember potential = ((ProfileRealm) realm).getAccount(email);
				if (potential != null && potential.getEmail().equals(email)) return potential;
			}
		}
	    return null;
	 }

}
