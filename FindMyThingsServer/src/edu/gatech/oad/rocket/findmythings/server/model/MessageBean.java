package edu.gatech.oad.rocket.findmythings.server.model;

import edu.gatech.oad.rocket.findmythings.server.util.HTTP;

/**
 * This simple wrapper class allows us to return status messages from the Google App Engine Cloud Endpoints.
 *
 * User: zw
 * Date: 4/7/13
 * Time: 5:10 PM
 */
@com.google.appengine.repackaged.org.codehaus.jackson.map.annotate.JsonSerialize(
		include=com.google.appengine.repackaged.org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL)
public class MessageBean {

	public MessageBean(HTTP.Status status, String message) {
		this.status = status;
		this.message = message;
	}

	public MessageBean(HTTP.Status status, String message, String failureReason) {
		this.status = status;
		this.message = message;
		this.failureReason = failureReason;
	}

	private HTTP.Status status;
	private String message;
	private String failureReason;

	public HTTP.Status getStatus() {
		return status;
	}

	public void setStatus(HTTP.Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
}
