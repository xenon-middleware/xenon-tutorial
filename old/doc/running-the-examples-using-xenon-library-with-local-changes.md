Running the examples using Xenon library with local changes
-----------------------------------------------------------

You made changes to a local version of the Xenon library and want to make sure the examples work use the following steps:

1. Goto the repository containing local Xenon library
2. Make sure the version of the Xenon library defined in `Xenon/gradle/common.gradle` and `Xenon-examples/build.gradle` are the same.
3. Run `./gradlew publishToMavenLocal`, this will install the Xenon library in the local Maven repository
4. Go back to this (Xenon-examples) repository
5. The gradlew commands will use the version of the Xenon library installed in the local Maven repository
