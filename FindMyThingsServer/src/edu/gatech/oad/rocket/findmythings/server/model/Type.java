package edu.gatech.oad.rocket.findmythings.server.model;

/**
 * An enumerated type representing
 * @author zwaldowski
 *
 */
public enum Type {
	LOST, FOUND, DONATION, REQUEST;

	/**
	 * A key suitable for putting a Type into a map.
	 */
	public static final String ID = "item_type";

	@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonValue public String JSONValue() {
		return toString();
	}

	@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonCreator public static Type
	fromString(@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonProperty("type") String desc) {
		return Type.valueOf(desc);
	}
}