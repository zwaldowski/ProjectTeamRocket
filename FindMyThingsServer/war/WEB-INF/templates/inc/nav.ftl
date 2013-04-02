<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="brand" href="/">Find My Things</a>
      <div class="nav-collapse collapse">
        <ul class="nav">
          <li><a href="/"><i class="icon-home"></i> Home</a></li>
          <li><a href="/about"><i class="icon-info-sign"></i> About</a></li>
          <li><a href="/contact"><i class="icon-comment"></i> Contact</a></li>
        </ul>
        <ul class="nav pull-right">
          <@shiro.authenticated>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="icon-user"></i>
              ${userEmail!"User"}
              <b class="caret"></b>
              </a>
            <ul class="dropdown-menu">
              <li><a href="/account"><i class="icon-book"></i> My Account</a></li>
              <li><a href="/logout"><i class="icon-signout"></i> Log Out</a></li>
            </ul>
          </li>
          </@>
          <@shiro.notAuthenticated>
          <li><a href="/login"><i class="icon-signin"></i> Login</a></li>
          <li><a href="/register"><i class="icon-check"></i> Register</a></li>
          </@>
        </ul>
      </div><!--/.nav-collapse -->
    </div>
  </div>
</div>