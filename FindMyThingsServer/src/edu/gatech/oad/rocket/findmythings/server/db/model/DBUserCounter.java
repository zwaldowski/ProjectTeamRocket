package edu.gatech.oad.rocket.findmythings.server.db.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Unindex;

import java.util.Date;
import java.util.logging.Logger;

/**
 * This is a singleton, just holding a count of the number
 * of DBMember objects we have.
 * <p> The reason for using this is that counting the number of items
 * dynamically is very inefficient, and costly.
 * <p> This counter should be changed relatively rarely (less than once a second).
 */
@Cache @Unindex @Entity
public class DBUserCounter {
	static final Logger LOG = Logger.getLogger(DBUserCounter.class.getName());

	public static final Long COUNTER_ID = 1L;

	@Id Long id;

	private int count;

	private Date lastModified;

	public DBUserCounter() {
		id = COUNTER_ID;
		lastModified = new Date(0L);
	}

	public int getCount() {
		return count;
	}

	public void delta(long delta) {
		this.count += delta;
		this.lastModified = new Date();
	}

	public Date getLastModified() {
		return lastModified;
	}

	protected Long getId() {
		return id;
	}
}
