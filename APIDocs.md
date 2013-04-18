# Find My Things - API Documentation

## Bacgkround

### HTTP GET

The basic kind of web request: "hey, can I get this URL?". The request has no body; everything like parameters has to be included in the URL. Browsing the web (except for HTML `<form>`, usually) uses this.

    GET /login.jsp HTTP/1.0
    User-Agent: FindMyThings/0.2 (Android; like Linux; Blasting Off Again; en-us)
    (this area purposefully left empty)
    

### HTTP POST

The middle ground between `GET` and `POST`. Query data is sent in the body of the request (like `PUT`), with the intent of getting a full-bodied response like `GET`. The forms in the web app as well as all of the Android app API queries uses this method.

    POST /login.jsp HTTP/1.0
    User-Agent: FindMyThings/0.2 (Android; like Linux; Blasting Off Again; en-us)
	Content-Type: application/x-www-form-urlencoded
	Content-Length: 51

	username=zwaldowski%040gatech.edu&password=rawr


### HTTP PUT/DELETE/PATCH

All of these are minor modifications on `GET` that aren't like `POST`. Here, have a bunch of data. For example, uploading a file somewhere is usually HTTP `PUT`. A `DELETE` request is `GET` for deleting things, so when your URL's are special, i.e., `item/42`, a `DELETE` request deletes that in the database. We *sort of* use that. `PATCH` is a non-standard modification for `PUT` that has the same meaning on most servers, but instead of creating or replacing an entry in the database with this content, the server should only use the content specified in the request for updating.

## Special web pages

### /login

Both GET and POST render the login page HTML. Submitting the form on this page submits a POST request. To this same page. If a POST request is performed, the authenticator will attempt logging in. If it succeeds, the page gets redirected; if it fails, the login page gets rendered.

Parameters:

 * String `username`: email address
 * String `password`: password sent over the air. That's totally safe, right. (TODO: enable SSL on this endpoint)
 * boolean `rememberMe`: whether the session expires normally. Should always be `true` for Android app, technically users are logged in indefinitely

 * If login is a failure, the login page is reloader with an error.
 * If login is a success and we have a callback URL, go there.
 * If login is a success and we have no callback, go to `/`.
 * All methods return HTTP status 200 (OK) unless the server itself fails. Redirects may involve and HTTP status moved (304 or something).

### /logout
 
Requires a HTTP session cookie.
 
* **NO MATTER WHAT** this page redirects to `/`.
 
### /register

`GET` shows ther registration page. A `POST` request with errors also reloads the page.
 
`POST` Parameters:
 
* username - Must be a valid email address
* password - Must be greater than 3 characters
* password_alt - Must match password
* name - No validation.
* phone - Must match format 1-555-555-5555.
* address - Locale/region/neighborhood/street address. No validatio performedn.

If success: registration email sent, shows web page telling user to check their email.
 
If failure: Reloads the page with an error message.
 
### /forgot
 
Same inputs/outputs as `/register`. User must also check their email. Password is reset using a form upon activation.

### /activate

If the activation is done via registration, the user is told to log in. If the activation is done via "forgot password", the user is allowed to change their password.

## API Endpoints

Starting in commit c61aa744, the backend API is implemented using [Google Cloud Endpoints](https://developers.google.com/appengine/docs/java/endpoints/).

Note that this code is GENERATED and should not be used out of the box, it just covers all our boilerplate.

The API is inherently versioned. The current deployment version is `v1`. The API is called relevant to either `https://rocket-findmythings.appspot.com/` or `http://127.0.0.1:8888`, depending on where you're running it from.

All API requests are made relative to the App Engine namespace, `/_ah/api/<api name>/<api version>/<endpoint>`, like `/_ah/api/fmthings/v1/test`

### Endpoint list

* `members` (admin only)
* `members/get` (logged in user gets profile information)
* `members/update` (admin only)
* `items`
* `items/get` (logged-in user gets location information)
* `items/insert` (logged-in user)
* `items/update` (owning user or admin)
* `items/delete` (owning user or admin)
* `test`
* `test/auth`
* `register` (NOT the same as web register - automatically activates)
* `forgot` (SAME as web register)
* `account` (logged-in user)
* `account/update` (logged-in user)
* `account/login`
* `account/logout`

### Authentication

API endpoints that require special permissions or the user to be logged in are not handled in the endpoint code (and therefore the generated API), but rather by our authentication laye.

The username and token returned by a successful `/account/login` (same params as `/login`, above) should be kept somewhere in the app's keychain. They are like a username and password - the server lets you in **immediately** with the username and token, do not pass go, do not collect $200. This greatly simplifies our API functions with some… debatable security aspects. However, we can revoke a token at any time so it doesn't necessarily need to be protected like a password.

Google takes requests pointed at public API URLs (`/_ah/api/fmthings/v1/items/`) and translates them to `POST` requests at private API URLs (`/_ah/spi/edu.gatech.oad.rocket.findmythings.server.spi.ItemV1.listItems`). This would mangle any query parameters used for logging in and tokenizing, so tokenized APIs are used in one way only for simplicity. Append a standard HTTP `WWW-Authorization` header (either `Basic` or `FMTTOKEN`): `WWW-Authorization: FMTTOKEN endhbGRvd3NraUBnYXRlY2guZWR1OmQ5OGE1YjMwLWUzNmUtNDQ5Yi1hNDk4LTZhYzQ2OTExZDM0NDo=`.

The token parameter (the long crazy string above) is created by putting the username and token together in a format like `username:token` (i.e., `zwaldowski@gatech.edu:d98a5b30-e36e-449b-a498-6ac46911d344`) and Base64 encoding the result. (Android has a utility for this.)

Demo request:

    GET /_ah/api/fmthings/v1/items?type=LOST HTTP/1.0
    User-Agent: FindMyThings/0.2 (Android; like Linux; Blasting Off Again; en-us)
    WWW-Authorization: FMTTOKEN endhbGRvd3NraUBnYXRlY2guZWR1OmQ5OGE1YjMwLWUzNmUtNDQ5Yi1hNDk4LTZhYzQ2OTExZDM0NDo=


Becomes internally:

	POST /_ah/spi/edu.gatech.oad.rocket.findmythings.server.spi.ItemV1.listItems HTTP/1.0
	User-Agent: FindMyThings/0.2 (Android; like Linux; Blasting Off Again; en-us)
    WWW-Authorization: FMTTOKEN endhbGRvd3NraUBnYXRlY2guZWR1OmQ5OGE1YjMwLWUzNmUtNDQ5Yi1hNDk4LTZhYzQ2OTExZDM0NDo=
    Content-Type: application/x-www-form-urlencoded
    Content-Length: 15

	{'type','LOST'}

Response (success):

    HTTP/1.1 200 OK
    Content-Length: 43
    Set-Cookie: rememberMe=deleteMe; Path=/; Max-Age=0; Expires=Sun, 31-Mar-2013 07:48:03 GMT
    Content-Type: application/json
    Server: Development/1.0
    Date: Mon, 01 Apr 2013 07:48:04 GMT
    Cache-Control: no-cache
    Expires: Fri, 01 Jan 1990 00:00:00 GMT
    Connection: close
    
    [JSON response goes here]

It's pretty fucking awesome, but I'm sleep-deprived.