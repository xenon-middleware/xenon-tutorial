#!/usr/bin/env bash

# step 1: upload input file(s)
xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
upload /home/alice/sleep.sh /home/xenon/sleep.sh

# step 2: submit job
xenon scheduler slurm --location ssh://localhost:10022 --username xenon --password javagat \
submit --stdout sleep.stdout.txt bash sleep.sh 60

# step 3: download generated output file(s)
xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
download /home/xenon/sleep.stdout.txt /home/alice/sleep.stdout.txt
