package edu.gatech.oad.rocket.findmythings.server.security;

import edu.gatech.oad.rocket.findmythings.server.model.AppMember;

public interface ProfileRealm {

	public abstract boolean accountExists(String email);

	public abstract AppMember getAccount(String email);

}
