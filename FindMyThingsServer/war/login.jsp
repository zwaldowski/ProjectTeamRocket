<%@ page contentType="text/html;charset=UTF-8" language="java" import="edu.gatech.oad.rocket.findmythings.server.util.Config,edu.gatech.oad.rocket.findmythings.server.model.MessageBean" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<% String title ="Find My Things &raquo; Log In"; %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file ="inc/_head.inc" %>
  </head>

  <body>

    <%@ include file ="inc/nav.inc" %>

    <div class="container">

      <shiro:notAuthenticated>

      <div class="page-header">
        <h1>Please log in.</h1>
      </div>

      <%
        String failureReason = (String)request.getAttribute(MessageBean.FAILURE_REASON);
      %>

      <%=failureReason%>

      <c:if test="${not empty failureReason}">
      <c:choose>
      <c:when test="${failureReason eq 'noSuchUser'}">
      <div class="alert">
        <strong>Oh no!</strong> That user doesn't exist. <a href="/register" class="btn btn-info btn-mini">Register?</a>
      </div>
      </c:when>
      <c:when test="${failureReason eq 'badPassword'}">
      <div class="alert alert-error">
        <strong>Oh no!</strong> Your password was rejected. <a href="/forgot" class="btn btn-warning btn-mini">Forgot?</a>
      </div>
      </c:when>
      <c:when test="${failureReason eq 'accountLocked'}">
      <div class="alert alert-error">
        <strong>Oh no!</strong> Your account has been locked. <a href="/contact" class="btn btn-warning btn-mini">Ask us why?</a>
      </div>
      </c:when>
      <c:when test="${failureReason eq 'accountDisabled'}">
      <div class="alert alert-error">
        <strong>Oh no!</strong> Your account has been disabled. <a href="/contact" class="btn btn-warning btn-mini">Ask us why?</a>
      </div>
      </c:when>
      <c:when test="${failureReason eq 'noSuchUser'}">
      <div class="alert alert-error">
        <strong>Whoa there, buddy.</strong> Too many attempts have been made. Slow down.
      </div>
      </c:when>
      <c:otherwise>
      <div class="alert">
        <strong>Oh no!</strong> That's invalid information. Try again?
      </div>
      </c:otherwise>
      </c:choose>
      </c:if>

      <form class="form-horizontal" method="post">
        <div class="control-group">
          <label class="control-label" for="inputEmail">Email</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-envelope"></i></span>
              <input name="<%=Config.USERNAME_PARAM%>" id="inputEmail" type="text" placeholder="Email">
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputPassword">Password</label>
          <div class="controls">
            <div class="input-prepend">
              <span class="add-on"><i class="icon-key"></i></span>
              <input name="<%=Config.PASSWORD_PARAM%>" id="inputPassword" type="password" placeholder="Password">
            </div>
          </div>
        </div>
        <div class="control-group">
          <div class="controls">
            <label class="checkbox">
              <input name="<%=Config.REMEMBER_ME_PARAM%>" type="checkbox"> Remember me
            </label>
          </div>
        </div>
        <div class="form-actions">
          <button type="submit" class="btn">Sign in</button>
          <a href="/forgot" class="btn btn-warning">Forgot Password</a>
        </div>
      </form>

      </shiro:notAuthenticated>

      <shiro:authenticated>

      <div class="page-header">
        <h1>You're already logged in!</h1>
      </div>

      <h2><a href="/logout" class="btn btn-inverse btn-large">Log out instead?</a></h2>

      </shiro:authenticated>

      <%@ include file ="inc/footer.inc" %>

    </div> <!-- /container -->

    <%@ include file ="inc/_foot.inc" %>

  </body>
</html>
