package edu.gatech.oad.rocket.findmythings.server.model;

public enum Category {
	HEIR, KEEPSAKE, MISC;

	/**
	 * A key suitable for putting a Category into a map.
	 */
	public static final String ID = "item_category";

	@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonValue public String JSONValue() {
		return toString();
	}


	public String searchableValue() {
		switch (this) {
			case HEIR:
				return "heirloom";
			case KEEPSAKE:
				return "keepsake";
			case MISC:
				return "miscellaneous";
		}
		return "";
	}

	@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonCreator public static Category
	fromString(@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonProperty("category") String desc) {
		return Category.valueOf(desc);
	}

}
