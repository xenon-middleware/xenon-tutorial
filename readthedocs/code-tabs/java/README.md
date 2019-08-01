
## Running the code snippets as used in the tutorial from the Linux command line

The code snippets from ``src/main/java/nl/esciencecenter/xenon/tutorial`` can
all be run. For this, you'll need Java Runtime Environment version 11 or later.

Additionally, be aware that some snippets expect certain paths, for example when
copying a file.

Individual snippets can be run with the following syntax:

```bash
./gradlew run -Pmain=<main class name> [-Ploglevel=<ERROR|WARN|INFO|DEBUG>] [--args='<arguments for main method>']
```

For example, to run
[DirectoryListing](src/main/java/nl/esciencecenter/xenon/tutorial/DirectoryListing.java)
use:

```bash
./gradlew run -Pmain=nl.esciencecenter.xenon.tutorial.DirectoryListing
```

## Configuring your IDE

You may want to use an IDE for running the code snippets. Here's how you an set
up a project for use with Eclipse:

```bash
./gradlew eclipse
```

This configures the classpath such that you can use the project dependencies
from within Eclipse. After running the task, you can see to what location the
dependencies were downloaded on your system by opening ``.classpath`` with a
text editor.

If you want to "undo" ``./gradlew eclipse``, use

```bash
./gradlew cleanEclipse
```

which will remove files ``.classpath``, ``.project``, and ``.settings``.

