package edu.gatech.oad.rocket.findmythings.test;

import android.test.InstrumentationTestCase;
import edu.gatech.oad.rocket.findmythings.control.LoginManager;
import edu.gatech.oad.rocket.findmythings.model.MessageBean;
import edu.gatech.oad.rocket.findmythings.service.EndpointUtils;
import edu.gatech.oad.rocket.findmythings.service.Fmthings;
import edu.gatech.oad.rocket.findmythings.util.EnumHelper;
import edu.gatech.oad.rocket.findmythings.util.Messages;

import java.io.IOException;

public class Zachary extends InstrumentationTestCase {

	public void testServerUserWorkflow() {

		// Log out if already logged in

		if (LoginManager.getLoginManager().getCurrentToken() != null) {

			MessageBean preLogoutTestOutput = null;
			try {
				preLogoutTestOutput = EndpointUtils.getEndpoint().account().logout().execute();
			} catch (IOException e) {
				LoginManager.getLoginManager().setCurrentEmailAndToken(null, null);
				fail("Raised exception while logging out as user");
			}
			LoginManager.getLoginManager().setCurrentEmailAndToken(null, null);
			assertNotNull("Did not receive logout message back from server", preLogoutTestOutput);
			assertEquals("Did not receive OK from user logout.", 200, (int) preLogoutTestOutput.getStatus());

		}

		// Register test user

		String testEmail = "zwaldowski@gmail.com";
		String testPassword = "whythehellamiawake7";
		String testPhone = "1-843-333-6836";
		String testName = "Zach Waldowski";

		MessageBean registerOutput = null;
		try {
			Fmthings.Account.Register op = EndpointUtils.getEndpoint().account().register(testEmail, testPassword, testPassword);
			op.setPhone(testPhone);
			op.setName(testName);
			registerOutput = op.execute();
		} catch (IOException e) {
			fail("Raised exception while registering user.");
		}

		assertNotNull("Did not receive message back from server", registerOutput);

		String status = registerOutput.getMessage(), registerFailureMessage = registerOutput.getFailureReason();
		Messages.Register registerFailureType = null;
		if (registerFailureMessage != null) {
			registerFailureType = EnumHelper.forTextString(Messages.Register.class, registerFailureMessage);
		}

		if (registerFailureType != Messages.Register.ALREADY_USER) {
			assertEquals("Did not receive ok from server with result code" + registerFailureType, Messages.Status.OK.getText(), status);
		}

		// Login as user

		MessageBean loginOutput = null;
		try {
			loginOutput = EndpointUtils.getEndpoint().account().login(testEmail, testPassword).execute();
		} catch (IOException e) {
			fail("Raised exception while logging in as user.");
		}

		assertNotNull("Did not receive log in message back from server", loginOutput);

		String token = loginOutput.getToken(), email = loginOutput.getEmail(), loginFailureMessage = loginOutput.getFailureReason();
		Messages.Login loginFailureType = null;
		if (loginFailureMessage != null) {
			loginFailureType = EnumHelper.forTextString(Messages.Login.class, loginFailureMessage);
		}

		assertNotNull(token, "Did not receive login token from server with result code" + loginFailureType);

		LoginManager.getLoginManager().setCurrentEmailAndToken(email, token);

		// Authentication test

		MessageBean authTestOutput = null;
		try {
			authTestOutput = EndpointUtils.getEndpoint().test().authenticated().execute();
		} catch (IOException e) {
			fail("Raised exception while testing user logged in");
		}

		assertNotNull("Did not receive test message back from server", authTestOutput);

		assertEquals("Did not receive OK from authentication test.", 200, (int)authTestOutput.getStatus());

		// User logout

		MessageBean userLogoutTestOutput = null;
		try {
			userLogoutTestOutput = EndpointUtils.getEndpoint().account().logout().execute();
		} catch (IOException e) {
			LoginManager.getLoginManager().setCurrentEmailAndToken(null, null);
			fail("Raised exception while logging out as user");
		}
		LoginManager.getLoginManager().setCurrentEmailAndToken(null, null);
		assertNotNull("Did not receive logout message back from server", userLogoutTestOutput);
		assertEquals("Did not receive OK from user logout.", 200, (int) userLogoutTestOutput.getStatus());

	}

}
