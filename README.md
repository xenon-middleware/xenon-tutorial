```bash
# download the dependencies into the lib directory
./gradlew download

# configure the classpath such that you can use the dependencies 
# from within Eclipse
./gradlew eclipse

```

## Running the examples from the Linux command line

Examples can be run with the following syntax
```bash
./gradlew run -Pmain=<main class name> [-Ploglevel=<ERROR|WARN|INFO|DEBUG>] [--args='<arguments for main method>']
```

To run the [DirectoryListing](src/main/java/nl/esciencecenter/xenon/examples/DirectoryListing.java) example use
```bash
./gradlew run -Pmain=nl.esciencecenter.xenon.examples.DirectoryListing
```
