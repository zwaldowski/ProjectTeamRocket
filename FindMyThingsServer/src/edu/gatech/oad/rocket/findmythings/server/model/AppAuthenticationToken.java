package edu.gatech.oad.rocket.findmythings.server.model;

import java.util.UUID;
import java.util.logging.Logger;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@Cache
@Entity
public class AppAuthenticationToken {

	static final Logger LOGGER = Logger.getLogger(AppAuthenticationToken.class.getName());

    @Id private String identifierString;
    @Index private String email; // not a Ref<AppMember> to support super-admins
    @Ignore transient UUID identifier;

    // for Objectify
    protected AppAuthenticationToken() {}

	public AppAuthenticationToken(String email) {
		this.email = email;
		this.identifier = UUID.randomUUID();
		this.identifierString = identifier.toString();
	}

	public String getIdentifierString() {
		return identifierString;
	}

	public String getEmail() {
		return email;
	}

	public UUID getIdentifier() {
		if (identifier == null && identifierString != null) {
			identifier = UUID.fromString(identifierString);
		}
		return identifier;
	}

}
