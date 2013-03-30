package edu.gatech.oad.rocket.findmythings.server.db;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.cache.Cache;

import com.googlecode.objectify.VoidWork;

import edu.gatech.oad.rocket.findmythings.server.model.*;

public class UserDatabaseService extends DatabaseService {
    static final Logger LOG = Logger.getLogger(UserDatabaseService.class.getName());

    private static final long REGISTRATION_VALID_DAYS = 1;

    private final Cache<String, AppMember> userCache;

    public UserDatabaseService() {
        userCache = new MemcacheManager().getCache(AppMember.class.getName());
    }

    /**
     * Save user with authorization information
     * @param user  User
     * @param changeCount should the user count be incremented
     * @return the user, after changes
     */
    public AppMember updateMember(AppMember user, boolean changeCount) {
	ofy().save().entity(user);
        userCache.remove(user.getEmail());
        if (changeCount) {
            changeCount(1L);
        }
        return user;
    }

    public AppMember deleteMember(AppMember user) {
	ofy().delete().entity(user);
        userCache.remove(user.getEmail());
        changeCount(-1L);
        return user;
    }

    public RegistrationTicket saveRegistration(String ticket, String email) {
	RegistrationTicket reg = new RegistrationTicket(ticket, email, REGISTRATION_VALID_DAYS, TimeUnit.DAYS);
        ofy().save().entity(reg);
        return reg;
    }

    public String findEmailFromValidCode(String code) {
	RegistrationTicket reg = ofy().load().type(RegistrationTicket.class).id(code).get();
        return (reg == null) ?  null : (reg.isValid() ? reg.getEmail() : null);
    }

    public AppMember findMember(String email) {
	AppMember user = null;
        try {
            user = userCache.get(email);
            if (user == null) {
                user = ofy().load().type(AppMember.class).id(email).get();
                if (user != null) {
                    userCache.put(email, user);
                }
            }
        } catch (NullPointerException e) {
            LOG.log(Level.INFO, "Can't put to cache", e);
        }
        return user;
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
	AppMember user = ofy().load().type(AppMember.class).id(email).get();
        if (user != null) {
            user.register();
            ofy().save().entity(user);
            userCache.remove(email);
        }

        ofy().delete().type(RegistrationTicket.class).id(code);
    }

    private AppUserCounter getCounter() {
	return ofy().load().type(AppUserCounter.class).id(AppUserCounter.COUNTER_ID).get();
    }

    public long getCount() {
	AppUserCounter th = getCounter();
	if (th == null) return 0;
        return th.getCount();
    }

    public Date getCountLastModified() {
	AppUserCounter th = getCounter();
	if (th == null) return new Date(0L);
        return th.getLastModified();
    }

    /**
     * Change the user count.  Wrapped in a transaction to make sure the
     * count is accurate.
     * @param delta amount to change
     */
    private void changeCount(final long delta) {
	ofy().transact(new VoidWork() {
            public void vrun() {
		AppUserCounter th = getCounter();
                if (th == null) {
                    th = new AppUserCounter();
                }
                th.delta(delta);
                ofy().save().entity(th);
            }
        });
    }

}
