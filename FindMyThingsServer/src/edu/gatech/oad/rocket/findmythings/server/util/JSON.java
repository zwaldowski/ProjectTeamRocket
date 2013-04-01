package edu.gatech.oad.rocket.findmythings.server.util;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.common.base.Preconditions;

public final class JSON {
	
	private JSON() {}
	
	protected static JSONObject fromArgs(Object... args) {
		Preconditions.checkArgument(args.length % 2 == 0, "There must be an even number of argument strings");
		try {
			JSONObject obj = new JSONObject();
			for (int i = 0; i < args.length; i += 2) {
				obj.put((String)args[i], args[i+1]);
			}
			return obj;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

}
