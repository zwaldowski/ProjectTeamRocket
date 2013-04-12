<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String title ="Find My Things &raquo; Contact"; %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file ="inc/_head.inc" %>
  </head>

  <body>

    <%@ include file ="inc/nav.inc" %>

    <div class="container">

      <div class="page-header">
        <h1>Find My Things <small>has been built by...</small></h1>
      </div>

      <div class="row">
        <div class="span1">&nbsp;</div>
        <div class="span10 container">
          <table class="table contactus">
            <tr>
              <th></th>
              <th>Cristina Chu</th>
              <th>Justin Cole</th>
              <th>Tyler Stowell</th>
              <th><em style="font-weight:500">and</em> Zachary Waldowski</th>
            </tr>
            <tr>
              <td></td>
              <td><img src="http://www.gravatar.com/avatar/d5f1b8c03129567a5572686d455ccfcf.jpg?s=178" width="89" height="89" class="img-polaroid"></td>
              <td><img src="http://i.imgur.com/swDbWYn.jpg" width="89" height="89" class="img-polaroid"></td>
              <td><img src="http://www.gravatar.com/avatar/065e17c616fedb0f59bba27eb08ee0e6.jpg?s=178" width="89" height="89" class="img-polaroid"></td>
              <td><img src="http://www.gravatar.com/avatar/84c0d9b7f0f1945fc58910b4a63ed313.jpg?s=178" width="89" height="89" class="img-polaroid"></td>
            </tr>
            <tr>
              <td><i class="icon-envelope-alt icon-large"></i>&nbsp;</td>
              <td><a href="mailto:cchu43@gatech.edu" class="btn btn-small btn-danger">cchu43@gatech.edu</a></td>
              <td><a href="mailto:cchu43@gatech.edu" class="btn btn-small btn-danger">jcole44@gatech.edu</a></td>
              <td><a href="mailto:cchu43@gatech.edu" class="btn btn-small btn-danger">tstowell3@gatech.edu</a></td>
              <td><a href="mailto:cchu43@gatech.edu" class="btn btn-small btn-danger">zwaldowski@gatech.edu</a></td>
            </tr>
            <tr>
              <td><i class="icon-globe icon-large"></i>&nbsp;</td>
              <td><a href="http://gatech.edu" class="text-center btn btn-small btn-warning" style="margin: 0 auto">http://gatech.edu</a></td>
              <td><a href="http://gatech.edu" class="text-center btn btn-small btn-warning" style="margin: 0 auto">http://gatech.edu</a></td>
              <td><a href="http://gatech.edu" class="text-center btn btn-small btn-warning" style="margin: 0 auto">http://gatech.edu</a></td>
              <td><a href="http://dzzy.us" class="btn btn-small btn-success">http://dzzy.us</a></td>
            </tr>
            <tr>
              <td><i class="icon-github icon-large"></i>&nbsp;</td>
              <td><a href="https://github.com/cchu43" class="btn btn-small btn-inverse">cchu43</a></td>
              <td><a href="https://github.com/jcole123" class="btn btn-small btn-inverse">jcole123</a></td>
              <td><a href="https://github.com/tstowel1" class="btn btn-small btn-inverse">tstowel1</a></td>
              <td><a href="https://github.com/zwaldowski" class="btn btn-small btn-inverse">zwaldowski</a></td>
            </tr>
          </table>
        </div>
        <div class="span1">&nbsp;</div>
      </div>

      <%@ include file ="inc/footer.inc" %>

    </div> <!-- /container -->

    <%@ include file ="inc/_foot.inc" %>

  </body>
</html>
