# Find My Things - API Documentation & Discussion

I'm writing this sleep deprived, so forgive the brevity!

## The Basics

### HTTP GET

The basic kind of web request: "hey, can I get this URL?". The request has no body; everything like parameters has to be included in the URL. Browsing the web (except for HTML `<form>`, usually) uses this.

### HTTP PUT

The logical opposite of `GET`: here, have a bunch of data. Uploading files uses this, though we won't be in FMF.

### HTTP POST

The middle ground between `GET` and `POST`. Query data is sent in the body of the request (like `PUT`), with the intent of getting a full-bodied response like `GET`. The forms in the web app as well as all of the Android app API queries uses this method.

### Web vs. App

Web endpoints will be designed to accept `GET` requests to render a web page. If it's a responder to a form or an AJAX request, it'll also respond to `POST`. Generally, I'll subclass the web endpoint for the `/api/` (app) endpoint to add/replace functionality relevant to returning JSON data to the app.

## API Endpoints

### /login.jsp

Both GET and POST render the login page HTML. Submitting the form on this page submits a POST request. To this same page. If a POST request is performed, the authenticator will attempt logging in. If it succeeds, the page gets redirected; if it fails, the login page gets rendered.

Parameters:

 * String `username`: email address
 * String `password`: password sent over the air. That's totally safe, right. (TODO: enable SSL on this endpoint)
 * boolean `rememberMe`: whether the session expires normally. Should always be `true` for Android app, technically users are logged in indefinitely

 * If login is a failure, the login page is reloader with an error.
 * If login is a success and we have a callback URL, go there.
 * If login is a success and we have no callback, go to `/index.html`.
 * All methods return HTTP status 200 (OK) unless the server itself fails. Redirects may involve and HTTP status moved (304 or something).
 
### /api/login

Similar to `/login.jsp`. Returns JSON:

 * If login is a success, HTTP status 200 (OK), JSON with `status` = `ok` and `token` = some kind of session ID (String to be initialized as Serializable, not implemented yet).
 * If login is a failure, JSON with `status` = { some kind of failure message } as described in `server.util.Errors.Login`. Note that these aren't to be given to the user straight and have to be localized.

## Future API Methods & Design

The web side of things all uses the current Java session ID cookie to generate the contents for each request. Typical logged-in website stuff. So all requests are in theory automatically authenticated (just not necessarily authorized).

We don't quite have this luxury: we need to send the Session ID, which would be cached in the prefs of the Android app, along with each request. Not too big of a deal, especially if our login framework automatically picks it up.

The server needs to return some form of session token (the actual Java session ID may be unsafe?) that the Android app keeps and the server uses to create a Session (and an associated User, once we get that part going). See `Subject.Builder` under [http://shiro.apache.org/subject.html].

Some API methods and web pages will be viewable to guests. These will be public-facing information, though some requests (i.e., looking at the information for an Item) will give more data if the user is authenticated.