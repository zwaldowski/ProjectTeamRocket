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
          <li><a href="/">Home</a></li>
          <li><a href="/about">About</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
        <ul class="nav pull-right">
          <li class="auth-guest"><a href="/login">Login</a></li>
          <li class="auth-guest"><a href="/register">Register</a></li>
          <li class="dropdown auth-user">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Howdy, ${firstname}! <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="/account">My Account</a></li>
              <li><a href="/logout">Log Out</a></li>
            </ul>
          </li>
        </ul>
      </div><!--/.nav-collapse -->
    </div>
  </div>
</div>