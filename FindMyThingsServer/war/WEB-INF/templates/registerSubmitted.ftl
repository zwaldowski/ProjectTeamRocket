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

      <div class="page-header">
        <h1>Almost there...</h1>
      </div>

      <h3>You'll recieve an email shortly with a link to reset the password your account.</h3>

      <#else>

      <div class="page-header">
        <h1>Good to go!</h1>
      </div>

      <h3>You'll recieve an email shortly with a link to activate your new account.</h3>

      </#if>

      <#include "inc/footer.ftl">

    </div>

    <#include "inc/_foot.ftl">

  </body>
</html>
