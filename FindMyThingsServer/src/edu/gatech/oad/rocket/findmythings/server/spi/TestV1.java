package edu.gatech.oad.rocket.findmythings.server.spi;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import edu.gatech.oad.rocket.findmythings.server.model.MessageBean;
import edu.gatech.oad.rocket.findmythings.server.util.HTTP;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

@Api(name = "fmthings", version = "v1")
public class TestV1 extends BaseEndpoint {

	@ApiMethod(name = "test.authenticated", path = "test/auth")
	public MessageBean getTestAuthenticated() {
		return new MessageBean(HTTP.Status.OK.toInt(), "Hi.");
	}

	@ApiMethod(name = "test.unauthenticated", path = "test")
	public MessageBean getTest() {
		Subject subject = SecurityUtils.getSubject();
		String message;
		if (subject != null && subject.isAuthenticated()) {
			message = "Howdy, " + subject.getPrincipal() + "!";
		} else {
			message = "You're not logged in.";
		}
		return new MessageBean(HTTP.Status.OK.toInt(), message);
	}

}
