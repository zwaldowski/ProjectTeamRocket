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

      <div class="page-header">
        <h1>Register:</h1>
      </div>

      <#if failureReason??>
      <#switch failureReason>
      <#case "noSuchUser">
      <div class="alert">
        <strong>Oh no!</strong> That user doesn't exist. <a href="/register" class="btn btn-info btn-mini">Register?</a>
      </div>
      <#break>
      <#case "badPassword">
      <div class="alert alert-error">
        <strong>Oh no!</strong> Your password was rejected. <a href="/forgot" class="btn btn-warning btn-mini">Forgot?</a>
      </div>
      <#break>
      <#case "accountLocked">
      <div class="alert alert-error">
        <strong>Oh no!</strong> Your account has been locked. <a href="/contact" class="btn btn-warning btn-mini">Ask us why?</a>
      </div>
      <#break>
      <#case "accountDisabled">
      <div class="alert alert-error">
        <strong>Oh no!</strong> Your account has been disabled. <a href="/contact" class="btn btn-warning btn-mini">Ask us why?</a>
      </div>
      <#break>
      <#case "tooManyAttempts">
      <div class="alert alert-error">
        <strong>Whoa there, buddy.</strong> Too many attempts have been made. Slow down.
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
              <input name="password" id="inputName" type="text" placeholder="Name">
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputEmail">Email</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-envelope"></i></span>
              <input name="username" id="inputEmail" type="text" placeholder="Email">
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputPassword">Password</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-key"></i></span>
              <input name="password" id="inputPassword" type="password" placeholder="Password">
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputConfirmPassword">Confirm Password</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-key"></i></span>
              <input name="confirmpassword" id="inputConfirmPassword" type="password" placeholder="Confirm Password">
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputPhone">Phone Number</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-th"></i></span>
              <input name="phone" id="inputPhone" type="tel" placeholder="Phone #">
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputAddress">Address</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-th"></i></span>
              <input name="address" id="inputAddress" type="text" placeholder="Address">
            </div>
          </div>
        </div>
        <div class="form-actions">
          <button type="submit" class="btn">Register</button>
        </div>
        <input type="hidden" name="isWebLogin" value="true"/>
      </form>

      </@>

      <@shiro.authenticated>

      <div class="page-header">
        <h1>You're already logged in!</h1>
      </div>

      <h2><a href="/logout.jsp" class="btn btn-inverse btn-large">Log out instead?</a></h2>

      </@>

      <#include "inc/footer.ftl">

    </div>

    <#include "inc/_foot.ftl">

  </body>
</html>
