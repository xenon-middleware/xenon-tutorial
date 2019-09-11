#!/bin/env bash

XENON_VERSION=3.0.4
XENON_CLI_VERSION=3.0.4
XENON_ADAPTORS_CLOUD_VERSION=3.0.2

VERBOSE=1

test () {
   if [ "$VERBOSE" -eq 1 ]
   then
      echo "actual   = $actual"
      echo "expected = $expected"
      echo
   fi
   if [ "$actual" == "$expected" ]; then
      # good
      return 0
   else
      # bad
      return 1
   fi
}


echo "checking .travis.yml"

actual=$(head --lines=+14 .travis.yml  | tail --lines=1)
expected="        - \"wget https://github.com/xenon-middleware/xenon-cli/releases/download/v$XENON_CLI_VERSION/xenon-cli-shadow-$XENON_CLI_VERSION.tar\""
test $actual $expected || exit 1

actual=$(head --lines=+15 .travis.yml  | tail --lines=1)
expected="        - \"tar -xvf xenon-cli-shadow-$XENON_CLI_VERSION.tar\""
test $actual $expected || exit 1

actual=$(head --lines=+17 .travis.yml  | tail --lines=1)
expected="        - \"mv xenon-cli-shadow-$XENON_CLI_VERSION /home/travis/.local/bin/xenon/\""
test $actual $expected || exit 1

actual=$(head --lines=+20 .travis.yml  | tail --lines=1)
expected="        - \"echo 'PATH=\$PATH:/home/travis/.local/bin/xenon/xenon-cli-shadow-$XENON_CLI_VERSION/bin' >> /home/travis/.bashrc\""
test $actual $expected || exit 1

echo "checking readthedocs/code-tabs/java/build.gradle"

actual=$(head --lines=+12 readthedocs/code-tabs/java/build.gradle  | tail --lines=1)
expected="    implementation 'nl.esciencecenter.xenon:xenon:$XENON_VERSION'"
test $actual $expected || exit 1

actual=$(head --lines=+13 readthedocs/code-tabs/java/build.gradle  | tail --lines=1)
expected="    implementation 'nl.esciencecenter.xenon.adaptors:xenon-adaptors-cloud:$XENON_ADAPTORS_CLOUD_VERSION'"
test $actual $expected || exit 1

echo "checking readthedocs/tutorial.rst"

actual=$(head --lines=+38 readthedocs/tutorial.rst  | tail --lines=1)
expected="   Xenon CLI v$XENON_CLI_VERSION, Xenon library v$XENON_VERSION, Xenon cloud library v$XENON_ADAPTORS_CLOUD_VERSION"
test $actual $expected || exit 1

actual=$(head --lines=+646 readthedocs/tutorial.rst  | tail --lines=1)
expected="__ http://xenon-middleware.github.io/xenon/versions/$XENON_VERSION/javadoc"
test $actual $expected || exit 1

echo "checking  vm-prep/README.md"

actual=$(head --lines=+126 vm-prep/README.md  | tail --lines=1)
expected="    wget https://github.com/xenon-middleware/xenon-cli/releases/download/v$XENON_CLI_VERSION/xenon-cli-shadow-$XENON_CLI_VERSION.tar"
test $actual $expected || exit 1

actual=$(head --lines=+127 vm-prep/README.md  | tail --lines=1)
expected="    tar -xvf xenon-cli-shadow-$XENON_CLI_VERSION.tar"
test $actual $expected || exit 1

actual=$(head --lines=+129 vm-prep/README.md  | tail --lines=1)
expected="    mv xenon-cli-shadow-$XENON_CLI_VERSION ~/.local/bin/xenon/"
test $actual $expected || exit 1

actual=$(head --lines=+133 vm-prep/README.md  | tail --lines=1)
expected="    echo 'PATH=\$PATH:~/.local/bin/xenon/xenon-cli-shadow-$XENON_CLI_VERSION/bin' >> ~/.bashrc"
test $actual $expected || exit 1

echo 'all passed'
exit 0
