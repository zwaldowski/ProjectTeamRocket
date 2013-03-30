<#assign title="Find My Things &raquo; Hello World!">
<!DOCTYPE html>
<html lang="en">
  <head>
    <#include "inc/_head.ftl">
  </head>

  <body>

    <#include "inc/nav.ftl">

    <div class="container">

      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="hero-unit">
        <h1><i class="icon-search icon-3x pull-middle visible-phone"></i></h1>
        <h1><i class="icon-search icon-4x pull-right hidden-phone"></i></h1>
        <h1>We've all lost our stuff.</h1>
        <hr>
        <p>Find it again using Find My Things&trade; for Android and web.</p>
        <hr>
        <p>
        <a class="btn btn-large btn-info disabled" href="#"><i class="icon-download"></i> Download from Google Play</a>
        <a class="btn btn-large btn-success" href="/register.jsp"><i class="icon-pencil"></i> Register now</a>
      </p>
      </div>

      <!-- Example row of columns -->
      <div class="row">
        <div class="span4">
          <h2>Heading</h2>
          <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
          <p><a class="btn" href="#">View details &raquo;</a></p>
        </div>
        <div class="span4">
          <h2>Heading</h2>
          <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
          <p><a class="btn" href="#">View details &raquo;</a></p>
       </div>
        <div class="span4">
          <h2>Heading</h2>
          <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
          <p><a class="btn" href="#">View details &raquo;</a></p>
        </div>
      </div>

      <#include "inc/footer.ftl">

    </div> <!-- /container -->

    <#include "inc/_foot.ftl">

  </body>
</html>
