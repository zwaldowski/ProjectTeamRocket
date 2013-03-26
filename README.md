ProjectTeamRocket
=================

CS 2340 Android application, Spring 2013, Georgia Tech


p1 = Chu, Cristina

p2 = Cole, Justin

p3 = Stowell, Tyler

p4 = Waldowski, Zach

Prereq's
--------

### Your emulator!

If you aren't blessed enough to be running directly on a device, do note that you need the Google API's installed for your current version of Android (so, for 4.2.1, Google APIs 17 or whatever) and your AVD needs to target *that* SDK instead of just Android 17 for the app to install.

### Everybody Hates Eclipse

If you intend to work on the server, install "Google App Engine Tools For Android", "Google Plugin for Eclipse", "Google App Engine Java SDK", and "Google Web Toolkit SDK" using `http://dl.google.com/eclipse/plugin/4.2` in Help > Install New Software. See [here](https://developers.google.com/eclipse/docs/install-eclipse-4.2) for further details.


Checking out the project
------------------------

	git clone https://github.com/zwaldowski/ProjectTeamRocket.git
	
Also check out using the GitHub app on [Mac](github-mac://openRepo/https://github.com/zwaldowski/ProjectTeamRocket) or using the [Windows App](http://windows.github.com/).

After checking out, you'll have the *project root* `ProjectTeamRocket` and within that the *project folder* `FindMyThings` folder. You can put anything you want into `ProjectTeamRocket`, but only code should go in `FindMyThings` because Eclipse has emotional problems.

You should, now, be able to import the project into Eclipse by using File > Import… > General > Existing Projects into Workspace and selecting the *project root* of the repo (`ProjectTeamRocket`) as the root directory. FindMyThings (as well as FindMyThings server, as of recent) will appear in the Projects box, make sure it's checked. Make sure "Copy project into workspace" and "Add project to working sets" are UNCHECKED. Hit next and you should be cool.

### Adding the Google services

Alright, Eclipse is very finicky… to say the freaking least. You may or may not need to add the Googe Play services library after importing the project - I don't know, I haven't tried it on anybody else's computer yet.

File > Import… > Android > Existing Android Code Into Workspace. Root directory is `libs` in the *project root* (`ProjectTeamRocket`), you should have the Play SDK pop up in projects. Same rules as before, no checkboxes, hit Finish. The project should build now...

### Classpath woes & dependencies

If either the app or the server are clearly missing some sort of requirement (i.e., unable to resolve imports, `ClassNotFoundException`, etc), right-click the project, go to Java Build Path, and hit Libraries.

As of last update of this file, the Android app requires (Add Libraries or project references, something like that, ask me in person if it's not working):

* Project link to Google Play SDK, so `../libs/google-play-services_lib` (see above) 

As of last update of this file, the server requires (Add JAR):

* All of the .JARs in `FindMyThingsServer/war/WEB-INF/lib`, the app loader requires them there.