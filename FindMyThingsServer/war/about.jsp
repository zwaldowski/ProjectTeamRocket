<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String title ="Find My Things &raquo; About"; %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file ="inc/_head.inc" %>
  </head>

  <body>

    <%@ include file ="inc/nav.inc" %>

    <div class="container">

      <div class="page-header">
        <h1><small>Learn About</small> Find My Things</h1>
      </div>
      <dl class="dl-horizontal">
        <dt>Class</dt>
        <dd>CS 2340</dd>
        <dt>Professor</dt>
        <dd>Bob Waters</dd>
        <dt>Undergraduate TA</dt>
        <dd>Amit Paduvalli</dd>
        <dt>Project Group</dt>
        <dd>#32 - Team Rocket</dd>
      </dl>
      <p class="lead">One simple mission:  one semester, one Android app.</p>
      <p>CS 2340 at the <a href="http://cc.gatech.edu">College of Computing</a> at <a href="http://gatech.edu">Georgia Institute of Technology</a> takes students who know an object-oriented language, and focuses on getting them to use that language in a true object-oriented style.  The course achieves this goal by introducing a design methodology and notation, and covering standard principles and practice in design.</p>
      <p>The final project for Spring 2013 was an Android app, entitled "Where is My Stuff". <em>(You can see how original our expansion on it was.)</em></p>
      <blockquote class="pull-right">
        <p>After a devastating tornado, flood or hurricane, not only do people face the loss of life and their homes, but they often lose precious family heirlooms that are carried away by winds and water. Photo albums, pictures and momentos might seem like trash to some folks, but these victims of storms they are important.</p><br/>
        <p>Imagine after a tornado, you find a photo album. How would you know who it belonged to? If you were the person who lost it, how would you let people know to be on the lookout for it?</p><br/>
        <p>That is where your app comes in.</p>
      </blockquote>
      <p>It might seem a little far-fetched that the first thing someone pulls out after a disaster is their Android smartphone, but it's a future that's not too far off. Cellular towers are one of the few things to survive a disaster with relatively little change in service. Already, third-world countries who can't even get water to all their citizens ubiquitous cellular networks, because they're far easier to set up and maintain.</p>
      <p>Goals for the semester includes building a secure, modern, well-designed Android app, with features ranging from search to authentication. Students were allowed to expand on the ideas however they pleased. Our group brought mapping and a web backend to the fold.</p>

      <%@ include file ="inc/footer.inc" %>

    </div> <!-- /container -->

    <%@ include file ="inc/_foot.inc" %>

  </body>
</html>
