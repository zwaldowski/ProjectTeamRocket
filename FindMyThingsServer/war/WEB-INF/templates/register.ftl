<#-- @ftlvariable name="usernameParam" type="java.lang.String" -->
<#assign title="Find My Things &raquo; Register">
<!DOCTYPE html>
<html lang="en">
  <head>
    <#include "inc/_head.ftl">
  </head>

  <body>

    <#include "inc/nav.ftl">

    <div class="container">

      <@shiro.notAuthenticated>

      <#if forgot??>

      <div class="page-header">
        <h1>Forgot something?</h1>
      </div>

      <#if failureReason??>
      <#switch failureReason>
      <#case "badEmailAdd">
      <div class="alert">
        <strong>Hey there!</strong> Your email wasn't valid. Care to try again?
      </div>
      <#break>
      <#case "superForgot">
      <div class="alert alert-error">
        <strong>Sure about that?</strong> No user exists with that email address. <a href="/register" class="btn btn-warning btn-mini">Register?</a>
      </div>
      <#break>
      <#default>
      <div class="alert">
        <strong>Oh no!</strong> That's invalid information. Try again?
      </div>
      </#switch>
      </#if>

      <form class="form-horizontal" method="post">
      <div class="control-group">
          <label class="control-label" for="inputName">Name</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-envelope"></i></span>
              <input name="${usernameParam!"email"}" id="inputEmail" type="email" placeholder="Email" autofocus required>
            </div>
            <span class="help-block">Enter the email address of the account you forgot the password to.</span>
          </div>
        </div>
        <div class="form-actions">
          <button type="submit" class="btn btn-primary">Recover Password</button>
          <a href="/" class="btn">Cancel</a>
        </div>
      </form>

      <#else>

      <div class="page-header">
        <h1>Tell us a little about you.</h1>
      </div>

      <#if failureReason??>
      <#switch failureReason>
      <#case "alreadyUser">
      <div class="alert alert-error">
        <strong>Hey there!</strong> A user with that email already exists. <a href="/forgot" class="btn btn-warning btn-mini">Forgot?</a>
      </div>
      <#break>
      <#case "badEmailAdd">
      <div class="alert">
        <strong>Whoops!</strong> Your email wasn't valid. Care to try again?
      </div>
      <#break>
      <#case "badPassword">
      <div class="alert">
        <strong>Whoops!</strong> Your password was too short. Try again.
      </div>
      <#break>
      <#case "passwordMatch">
      <div class="alert">
        <strong>Whoops!</strong> The two passwords didn't match. Try again.
      </div>
      <#break>
      <#case "badPhoneNum">
      <div class="alert">
        <strong>Ring-a-ding!</strong> That's not a valid phone number. Try again.
      </div>
      <#break>
      <#default>
      <div class="alert">
        <strong>Oh no!</strong> That's invalid information. Try again?
      </div>
      </#switch>
      </#if>

      <form class="form-horizontal" method="post">
      <div class="control-group">
          <label class="control-label" for="inputName">Name</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-pencil"></i></span>
              <input name="name" id="inputName" type="text" placeholder="Name" autofocus required>
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputEmail">Email</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-envelope"></i></span>
              <input name="${usernameParam!"email"}" id="inputEmail" type="email" placeholder="Email" required>
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputPassword">Password</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-key"></i></span>
              <input name="${passwordParam!"password"}" id="inputPassword" type="password" placeholder="Password" required>
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputConfirmPassword">Confirm Password</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-key"></i></span>
              <input name="password_alt" id="inputConfirmPassword" type="password" placeholder="Confirm Password" required>
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputPhone">Phone Number</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-th"></i></span>
              <input name="phone" id="inputPhone" type="tel" placeholder="Phone Number" pattern="[0-9]-([0-9]{3})-[0-9]{3}-[0-9]{4}">
            </div>
            <span class="help-block">i.e., <em>1-555-212-8403</em></span>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputAddress">Location</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-th"></i></span>
              <textarea name="address" rows="3" id="inputAddress" placeholder="Address or Neighborhood"></textarea>
            </div>
          </div>
        </div>
        <div class="form-actions">
          <button type="submit" class="btn btn-primary">Register</button>
          <a href="/" class="btn">Cancel</a>
        </div>
      </form>

      </#if>

      </@>

      <@shiro.authenticated>

      <div class="page-header">
        <h1>You're already logged in!</h1>
      </div>

      <h2><a href="/logout" class="btn btn-inverse btn-large">Log out instead?</a></h2>

      </@>

      <#include "inc/footer.ftl">

    </div>

    <#include "inc/_foot.ftl">

  </body>
</html>
