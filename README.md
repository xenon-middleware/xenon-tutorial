Xenon Examples
==============

Copyright 2015 The Netherlands eScience Center


This repository contains the source of the [Xenon](https://nlesc.github.io/Xenom) examples
and a build file to compile and run them.

Requirements
------------

* Copy of this repository
* Java JDK version 7 or greater.
* An internet connection to download dependencies

Compiling examples
------------------

The examples can be compiled into a single jar file with
```
./gradlew
```

This will generate `build/libs/Xenon-examples-all.jar` jar file which contains the compiled examples and their dependencies.

After changing the source code of an example rerun `./gradlew`.

Running the examples from the command line
------------------------------------------

An example can be executed using the following format
```
java -cp <jar file> <fully qualified example main class> <arguments required by example>
```

For example the `nl.esciencecenter.xenon.examples.files.DirectoryListing` example can be run with
```
java -cp build/libs/Xenon-examples-all.jar nl.esciencecenter.xenon.examples.files.DirectoryListing file:///tmp
```

Note that the classpath is specified in Linux/OSX format here. On
Windows use `build\libs\Xenon-examples-all.jar`.

All the examples can be run with `./run_examples.sh`.
This script requires a Linux machine with passwordless ssh to localhost.

Running examples from IDE
-------------------------

Generate IDE specific project files with `./gradlew eclipse` or `./gradlew idea`.
This repository can then be imported into Eclipse or IntelliJ IDEA.

An example file can be opened and run from inside the IDE.
