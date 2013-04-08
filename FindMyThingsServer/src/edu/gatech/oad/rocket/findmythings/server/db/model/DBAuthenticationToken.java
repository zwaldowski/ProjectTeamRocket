package edu.gatech.oad.rocket.findmythings.server.db.model;

import com.googlecode.objectify.annotation.*;

import java.util.UUID;
import java.util.logging.Logger;

@Cache @Entity
public class DBAuthenticationToken {

	static final Logger LOGGER = Logger.getLogger(DBAuthenticationToken.class.getName());

	@Id private String identifierString;
	@Index private String email; // not a Ref<DBMember> to support super-admins
	@Ignore private transient UUID identifier;

	// for Objectify
	protected DBAuthenticationToken() {}

	public DBAuthenticationToken(String email) {
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
