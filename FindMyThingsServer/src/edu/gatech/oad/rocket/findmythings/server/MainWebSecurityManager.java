package edu.gatech.oad.rocket.findmythings.server;

import java.util.Collection;

import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;

public class MainWebSecurityManager extends DefaultWebSecurityManager implements WebSecurityManager {

	public MainWebSecurityManager() {
        super();
        ((DefaultSubjectDAO) this.subjectDAO).setSessionStorageEvaluator(new MainSessionStorageEvaluator());
    }

    public MainWebSecurityManager(Realm singleRealm) {
        this();
        setRealm(singleRealm);
    }

    public MainWebSecurityManager(Collection<Realm> realms) {
        this();
        setRealms(realms);
    }

}