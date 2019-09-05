:orphan:

When things don't work
======================

Bash
----

Increase ``xenon``'s verbosity using its ``v`` option. More ``v``'s means more
verbose output.

.. code-block::

   xenon -vvvv <subcommands>

Java from Eclipse
-----------------

Increase verbosity by adding the following in Eclipse Debug Configuration, tab
*Arguments* >> *VM arguments*

.. code-block::

   -Dloglevel=DEBUG

Java from command line
----------------------

Increase the verbosity by adding ``loglevel=DEBUG`` as a property:

.. code-block::

   ./gradlew run -Pmain=nl.esciencecenter.xenon.tutorial.DirectoryListing -Ploglevel=DEBUG

Python
------

Start ``xenon-grpc`` in a terminal, using its ``v`` option. More ``v``'s means
more verbose output, e.g. ``xenon-grpc -vvvvv``. Subsequent calls to
``xenon.init()`` (originating from anywhere on the system) will connect to this
``grpc`` server, and its logging will appear in the terminal. Unfortunately,
this verbosity setting does not propagate down to ``xenon``.

|
|
|

:doc:`back to the tutorial</tutorial>`

|
|
