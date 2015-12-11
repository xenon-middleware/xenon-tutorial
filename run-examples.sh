#!/usr/bin/env bash
set -x #echo on
set -e

###
#
# This script requires a Linux machine with passwordless ssh to localhost.
#
###

class_path=build/libs/*-all.jar

java -cp $class_path nl.esciencecenter.xenon.examples.CreatingXenon

java -cp $class_path nl.esciencecenter.xenon.examples.CreatingXenonWithProperties

java -cp $class_path nl.esciencecenter.xenon.examples.credentials.CreatingCredential

java -cp $class_path nl.esciencecenter.xenon.examples.files.CreateLocalFileSystem

java -cp $class_path nl.esciencecenter.xenon.examples.files.CreateFileSystem local:///

java -cp $class_path nl.esciencecenter.xenon.examples.files.CreateFileSystem ssh://$USER@localhost

java -cp $class_path nl.esciencecenter.xenon.examples.files.DirectoryListing local://$PWD

java -cp $class_path nl.esciencecenter.xenon.examples.files.DirectoryListing ssh://$USER@localhost$PWD

java -cp $class_path nl.esciencecenter.xenon.examples.files.LocalFileExists $PWD/README.md

java -cp $class_path nl.esciencecenter.xenon.examples.files.FileExists local://$PWD/README.md

java -cp $class_path nl.esciencecenter.xenon.examples.files.FileExists ssh://$USER@localhost/$PWD/README.md

java -cp $class_path nl.esciencecenter.xenon.examples.files.ShowFileAttributes local://$PWD/README.md

java -cp $class_path nl.esciencecenter.xenon.examples.files.ShowFileAttributes ssh://$USER@localhost/$PWD/README.md

java -cp $class_path nl.esciencecenter.xenon.examples.files.CopyFile local://$PWD/README.md local:///tmp/Copy.Of.README.md
ls /tmp/Copy.Of.README.md
rm /tmp/Copy.Of.README.md

java -cp $class_path nl.esciencecenter.xenon.examples.jobs.SubmitSimpleBatchJob local:///

java -cp $class_path nl.esciencecenter.xenon.examples.jobs.SubmitSimpleBatchJob ssh://$USER@localhost

java -cp $class_path nl.esciencecenter.xenon.examples.jobs.SubmitBatchJobWithOutput local:///

java -cp $class_path nl.esciencecenter.xenon.examples.jobs.SubmitBatchJobWithOutput ssh://$USER@localhost

java -cp $class_path nl.esciencecenter.xenon.examples.jobs.SubmitInteractiveJobWithOutput ssh://$USER@localhost

java -cp $class_path nl.esciencecenter.xenon.examples.jobs.ListJobs local:///

