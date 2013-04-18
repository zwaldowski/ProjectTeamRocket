package edu.gatech.oad.rocket.findmythings.service;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import edu.gatech.oad.rocket.findmythings.control.LoginManager;

import java.io.IOException;

public class EndpointUtils {

	private static Fmthings initializeEndpoint() {
		return updateBuilder(new Fmthings.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				})).build();
	}

	private static final class EndpointSingleton {
		static final Fmthings endpoint = initializeEndpoint();
	}

	public static Fmthings getEndpoint() {
		return EndpointSingleton.endpoint;
	}

	  /*
	   * TODO: Need to change this to 'true' if you're running your backend locally using
	   * the DevAppServer. See
	   * http://developers.google.com/eclipse/docs/cloud_endpoints for more
	   * information.
	   */
	  protected static final boolean LOCAL_ANDROID_RUN = false;

	  /*
	   * The root URL of where your DevAppServer is running (if you're running the
	   * DevAppServer locally).
	   */
	  protected static final String LOCAL_APP_ENGINE_SERVER_URL = "http://localhost:8888/";

	  /*
	   * The root URL of where your DevAppServer is running when it's being
	   * accessed via the Android emulator (if you're running the DevAppServer
	   * locally). In this case, you're running behind Android's virtual router.
	   * See
	   * http://developer.android.com/tools/devices/emulator.html#networkaddresses
	   * for more information.
	   */
	  protected static final String LOCAL_APP_ENGINE_SERVER_URL_FOR_ANDROID = "http://10.0.2.2:8888";

	  /**
	   * Updates the Google client builder to connect the appropriate server based
	   * on whether LOCAL_ANDROID_RUN is true or false.
	   *
	   * @param builder
	   *            Google client builder
	   * @return same Google client builder
	   */
	  public static <B extends AbstractGoogleClient.Builder> B updateBuilder(
	      B builder) {
	    if (LOCAL_ANDROID_RUN) {
	      builder.setRootUrl(LOCAL_APP_ENGINE_SERVER_URL_FOR_ANDROID
	          + "/_ah/api/");
	    }

	    // only enable GZip when connecting to remote server
	    final boolean enableGZip = builder.getRootUrl().startsWith("https:");

	    builder.setGoogleClientRequestInitializer(new FmthingsRequestInitializer(){
		protected void initializeFmthingsRequest(FmthingsRequest<?> request) throws IOException {
			if (!enableGZip) {
				request.setDisableGZipContent(true);
			}

			String authHeader = LoginManager.getLoginManager().getAuthorizationHeader();
			if (authHeader != null) request.getRequestHeaders().put("X-Authorization", authHeader);
		}
	    });

	    return builder;
	  }
}
