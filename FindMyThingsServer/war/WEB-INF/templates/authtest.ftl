<#assign title="Find My Things &raquo; Hello World!">
<!DOCTYPE html>
<html lang="en">
  <head>
    <#include "inc/_head.ftl">
  </head>

  <body>

    <#include "inc/nav.ftl">

    <div class="container">

      <div class="hero-unit text-center">

        <@shiro.authenticated>
          <h1>Yeah, you're logged in.</h1>
        </@>
        <@shiro.notAuthenticated>
          <h1>No, you're not logged in.</h1>
        </@>

      </div>

      <#include "inc/footer.ftl">

    </div> <!-- /container -->

    <#include "inc/_foot.ftl">

  </body>
</html>
