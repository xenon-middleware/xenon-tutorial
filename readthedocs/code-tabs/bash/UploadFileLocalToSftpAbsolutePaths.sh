# step 1: upload input file(s)
xenon filesystem sftp --location ssh://localhost:10022 --username xenon --password javagat \
upload /home/alice/sleep.sh /home/xenon/sleep.sh
