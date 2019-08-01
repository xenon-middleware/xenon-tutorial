# step 3: download generated output file(s)
xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
download /home/xenon/sleep.stdout.txt /home/alice/sleep.stdout.txt
