package edu.gatech.oad.rocket.findmythings.server.model;

public enum Category {
	HEIR, KEEPSAKE, MISC;

	/**
	 * A key suitable for putting a Category into a {@link android.os.Bundle}, {@link android.content.Intent}, etc.
	 */
	public static final String ID = "item_category";
	
	@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonValue public String JSONValue() {
		return toString();
	}

	@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonCreator public static Category
	fromString(@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonProperty("category") String desc) {
		return Category.valueOf(desc);
	}

}
