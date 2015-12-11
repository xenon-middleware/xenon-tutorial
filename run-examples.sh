#!/usr/bin/env bash
set -x #echo on
set -e

echo "This script requires a Linux machine with passwordless ssh to localhost."

# set the Java classpath
CP=build/libs/Xenon-examples-all.jar

# set the logback library's logging level/verbosity
# possible values: INFO / WARN / DEBUG / ERROR
LOGLEVEL=WARN

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.CreatingXenon

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.CreatingXenonWithProperties

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.credentials.CreatingCredential

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.CreateLocalFileSystem

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.CreateFileSystem local:///

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.CreateFileSystem ssh://$USER@localhost

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.DirectoryListing local://$PWD

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.DirectoryListing ssh://$USER@localhost$PWD

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.LocalFileExists $PWD/README.md

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.FileExists local://$PWD/README.md

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.FileExists ssh://$USER@localhost/$PWD/README.md

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.ShowFileAttributes local://$PWD/README.md

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.ShowFileAttributes ssh://$USER@localhost/$PWD/README.md

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.files.CopyFile local://$PWD/README.md local:///tmp/Copy.Of.README.md
ls /tmp/Copy.Of.README.md
rm /tmp/Copy.Of.README.md

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.jobs.SubmitSimpleBatchJob local:///

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.jobs.SubmitSimpleBatchJob ssh://$USER@localhost

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.jobs.SubmitBatchJobWithOutput local:///

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.jobs.SubmitBatchJobWithOutput ssh://$USER@localhost

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.jobs.SubmitInteractiveJobWithOutput ssh://$USER@localhost

java -Dloglevel=$LOGLEVEL -cp $CP nl.esciencecenter.xenon.examples.jobs.ListJobs local:///

