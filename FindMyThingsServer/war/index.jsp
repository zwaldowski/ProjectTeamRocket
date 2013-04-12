<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String title ="Find My Things"; %>
<!DOCTYPE html>
<html lang="en">
  <head>
  	<%@ include file ="inc/_head.inc" %>
  </head>

  <body>

    <%@ include file ="inc/nav.inc" %>

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
        <a class="btn btn-large btn-success" href="/register"><i class="icon-pencil"></i> Register now</a>
      </p>
      </div>

      <!-- Example row of columns -->

        <div class="centre">
          <h2>Heading</h2>
          <p>Donde Esta La Biblioteca</p>
          <p><a class="btn" href="#">View details &raquo;</a></p>
        </div>


      <%@ include file ="inc/footer.inc" %>

    </div> <!-- /container -->

    <%@ include file ="inc/_foot.inc" %>

  </body>
</html>
