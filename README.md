Formatters
==========

This site, and readme, is under construction.


This repo contains two apps, an Sql formatter and an Xml formatter.  You can use either of the two links below to see them
in action as they each have pointers to the other.  They are hosted on Google's appengine.


* The Sql formatter currently formats most of the common SQL commands and some functions, they are listed in the tools.java.pats.nodes package.  I've used W3Schools as a guide for the common commands.  This is a work in process, I'm adding functions, corrections and refactoring when I get the time.


* The Xml formatter was in response to errors I would get when cutting SOAP messages from logs and trying to run them in SoapUI.
It's a simple solution, replace all white space occurances with one space each and pretty print the result.  SoapUI is happy with this approach.


http://patsformatters.appspot.com/SqlFormatter.html

http://patsformatters.appspot.com/XmlFormatter.html
