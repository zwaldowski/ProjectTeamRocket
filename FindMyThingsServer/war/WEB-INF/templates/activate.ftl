<#-- @ftlvariable name="code" type="java.lang.String" -->
<#assign title="Find My Things &raquo; Register">
<!DOCTYPE html>
<html lang="en">
  <head>
    <#include "inc/_head.ftl">
  </head>

  <body>

    <#include "inc/nav.ftl">

    <div class="container">

      <#if forgot??>

      <#if failureReason??>
      <#switch failureReason>
      <#case "expiredCode">
      <div class="alert">
        <strong>Whoops!</strong> The password reset attempt expired. <a href="/forgot" class="btn btn-warning btn-info">Try again.</a>
      </div>
      <#break>
      <#case "noSuchUser">
      <div class="alert alert-error">
        <strong>Oh no!</strong> That account doesn't exist. <a href="/contact" class="btn btn-warning btn-mini">Contact us.</a>
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
      <#default>
      <div class="alert">
        <strong>Oh no!</strong> That's invalid information. Try again?
      </div>
      </#switch>
      </#if>

      <form class="form-horizontal" method="post">
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
        <div class="form-actions">
          <button type="submit" class="btn btn-primary">Reset Password</button>
        </div>
        <input type="hidden" name="${usernameParam!"email"}" value="${email!""}">
        <input type="hidden" name="ticket" value="${code!""}">
        <input type="hidden" name="forgot" value="true">
      </form>

      <#else>

      <#if failureReason??>

      <div class="page-header">
        <h1>Oh no!</h1>
      </div>

      <#switch failureReason>
      <#case "expiredCode">
      <p>The registration code was either invalid or expired.</p>
      <#break>
      <#default>
      <p>Invalid data was received and we can't continue.</p>
      </#switch>

      <p>You may attempt to reset your password to continue.</p>

      <p>
        <a class="btn btn-large btn-primary" href="/login">Login</a>
        <a class="btn btn-large btn-warning" href="/forgot">Reset Password</a>
      </p>

      <#else>

      <div class="page-header">
        <h1>Ready to go!</h1>
      </div>

      <p>Your account is now activated. You may now log in.</p>

      <p>You may now log in.</p>

      <p>
        <a class="btn btn-large btn-primary" href="/login">Login</a>
      </p>

      </#if>
      </#if>
      
      <#include "inc/footer.ftl">

    </div>

    <#include "inc/_foot.ftl">

  </body>
</html>
