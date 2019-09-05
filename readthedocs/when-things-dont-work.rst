:orphan:

When things don't work
======================

Increase verbosity
------------------

Bash
~~~~

``xenon -vvvv <subcommands>``

Java from Eclipse
~~~~~~~~~~~~~~~~~

Add the following in Eclipse Debug Configuration, tab *Arguments* >> *VM arguments*

``-Dloglevel=DEBUG``

Java from command line
~~~~~~~~~~~~~~~~~~~~~~

``./gradlew run -Pmain=nl.esciencecenter.xenon.tutorial.DirectoryListing -Ploglevel=DEBUG``

Python
~~~~~~

Start ``xenon-grpc`` in a terminal, using ``xenon-grpc -vvvvv``, then ``xenon.init()`` 
will connect to that and the logging will be in the terminal.

|
|
|

:doc:`back to the tutorial</tutorial>`

|
|
