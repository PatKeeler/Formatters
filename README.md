Formatters
==========

This site, and readme, is under construction.


This repo contains two apps, an Sql formatter and an Xml formatter.  You can use either of the two links below to see them
in action as they each have pointers to the other.  They are hosted on Google's appengine.


* The Sql formatter was quite an endeavor and took me roughly two years to get it to this stage working on it nights and 
weekends.  I started the project thinking I would just do a simple select but as I got into it I realized I had to put in
all the common SQL commands.  Currently it will format non-proprietary commands and some functions.

** I would appreciate comments on the thread safety aspects of the Sql formatter app, I've done my best but have never had to create
a thread safe app so what I've done here is from reading books and information gathered from the internet.

** This is still a work in process, I'm adding functions and refactoring when I get the time.


* The Xml formatter was in response to errors I would get when cutting SOAP messages from logs and trying to run them in SoapUI.
It's a simple solution, replace all white space occurances with one space and pretty print the string.  SoapUI seems to like this
approach.


http://patsformatters.appspot.com/SqlFormatter.html
http://patsformatters.appspot.com/XmlFormatter.html
