# Find My Things - API Documentation & Discussion

## The Basics

### HTTP GET

The basic kind of web request: "hey, can I get this URL?". The request has no body; everything like parameters has to be included in the URL. Browsing the web (except for HTML `<form>`, usually) uses this.

    GET /login.jsp HTTP/1.0
    User-Agent: FindMyThings/0.2 (Android; like Linux; Blasting Off Again; en-us)
    (this area purposefully left empty)
    

### HTTP PUT

The logical opposite of `GET`: here, have a bunch of data. Uploading files uses this, though we won't be in FMF. I don't care about this, so I'm not even doing an example.

### HTTP POST

The middle ground between `GET` and `POST`. Query data is sent in the body of the request (like `PUT`), with the intent of getting a full-bodied response like `GET`. The forms in the web app as well as all of the Android app API queries uses this method.

    POST /login.jsp HTTP/1.0
    User-Agent: FindMyThings/0.2 (Android; like Linux; Blasting Off Again; en-us)
	Content-Type: application/x-www-form-urlencoded
	Content-Length: 51

	username=zwaldowski%040gatech.edu&password=admin


### Web vs. App

Web endpoints will be designed to accept `GET` requests to render a web page. If it's a responder to a form or an AJAX request, it'll also respond to `POST`. Generally, I'll subclass the web endpoint for the `/api/` (app) endpoint to add/replace functionality relevant to returning JSON data to the app.

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

## API Endpoints

### All API endpoints

All other API endpoints follow the following access rules. Some of them (~~see below~~ not implemented yet) allow guest access, and are mentioned when pertinent.

The username and token returned by `/api/login` should be kept safe somewhere in the app's keychain. They are like a username and password - the server lets you in **immediately** with the username and token, do not pass go, do not collect $200. This greatly simplifies our API functions with some… debatable security aspects.

Tokenized API requests are to use a `WWW-Authorization` header (either `Basic` or `FMTTOKEN` works), just as if you were actually logging in with username and password. Put the username and token together in a format like `username:token` (i.e., `zwaldowski@gatech.edu:d98a5b30-e36e-449b-a498-6ac46911d344`) and then Base64 encode the result. (Android has a utility for that.)

Any request that fails will still respond with HTTP status 200 (OK) (such that the browser/JavaScript engine/app, whichever it may be at the time, doesn't hijack the request) and will return a JSON object where `message` is a specialized status message and `status` is the *actual* HTTP status, probably in the 400 range.

Request:

    POST /api/authtest.asp HTTP/1.0
    User-Agent: FindMyThings/0.2 (Android; like Linux; Blasting Off Again; en-us)
    WWW-Authorization: FMTTOKEN endhbGRvd3NraUBnYXRlY2guZWR1OmQ5OGE1YjMwLWUzNmUtNDQ5Yi1hNDk4LTZhYzQ2OTExZDM0NDo=
	Content-Type: application/x-www-form-urlencoded
	Content-Length: 19

	fakeparam=goeshere

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
    
    {"status":"Howdy, zwaldowski@gatech.edu!"}

It's pretty fucking awesome, but I'm sleep-deprived.
 
### /api/login

The only API endpoint that doesn't strictly follow the above Similar to `/login.jsp`. Returns JSON:

If login is a success, HTTP status 200 (OK), JSON with `message` = `OK`, `token` = some kind of session token, and the user's email address in `username`.

    {
      "message": "OK",
      "status": 200,
      "token": "d98a5b30-e36e-449b-a498-6ac46911d344",
      "username": "zwaldowski@gatech.edu"
    }

If login is a failure, JSON with `status` = { some kind of failure message } as described in `server.util.Messages.Login`. Note that these aren't to be given to the user straight and have to be localized. I'm too freaking tired right now to write down what all errors there are.
 
    {
      "message": "invalidData", // Messages.Login
      "status": 401
    }
 
### /api/authtest

Demo of a guest-enabled, authentication-based page.

* If the user is not logged in, returns HTTP status 200 and JSON dict `status` = `200` and `message` = `"You're not logged in."`.
* If the user is logged in, returns HTTP status 200 and JSON dict `status` = `200` and `message` = `"Howdy, <email address>!"`.

 
### /api/logout

Requires a username/token authorization if you want to get anything done.

* **NO MATTER WHAT** this endpoint returns JSON with `status` = `200` and `message` = `OK`.

### /api/register

Parameters:

* username - Must be a valid email address
* password - Must be greater than 3 characters
* password_alt - Must match password
* name - No validation.
* phone - Must match format 1-555-555-5555.
* address - Locale/region/neighborhood/street address. No validation.

If success: JSON with `status` = `200` and `message` = `OK`. Registration cannot be completed from the API, user must check their email.

If failure: JSON with `status` = `400`, `message` = `nope`, `failureReason` = `<Value of Messages.Register>`

### /api/forgot

Same inputs/outputs as `/api/register`

## Future API Methods & Design

Some API methods and web pages will be viewable to guests. These will be public-facing information, though some requests (i.e., looking at the information for an Item) will give more data if the user is authenticated.