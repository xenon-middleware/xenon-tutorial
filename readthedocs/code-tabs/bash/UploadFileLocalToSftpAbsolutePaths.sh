# step 1: upload input file(s)
xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
upload /home/travis/sleep.sh /home/xenon/sleep.sh
