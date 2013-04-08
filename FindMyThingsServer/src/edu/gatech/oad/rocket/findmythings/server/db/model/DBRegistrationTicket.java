package edu.gatech.oad.rocket.findmythings.server.db.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Unindex;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Cache @Unindex @Entity
public class DBRegistrationTicket {
	static final Logger LOGGER = Logger.getLogger(DBRegistrationTicket.class.getName());

	@Id private String ticket;
	private String email;
	private Date dateCreated;
	private long validityMilliseconds;

	// for Objectify
	protected DBRegistrationTicket() {}

	public DBRegistrationTicket(String ticket, String email, long amount, TimeUnit unit) {
		this.ticket = ticket;
		this.email = email;
		this.dateCreated = new Date();
		this.validityMilliseconds = unit.toMillis(amount);
	}

	public String getTicket() {
		return ticket;
	}

	public String getEmail() {
		return email;
	}

	public boolean isValid() {
		return dateCreated.getTime() + validityMilliseconds > new Date().getTime();
	}
}
