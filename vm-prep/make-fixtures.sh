#!/usr/bin/env bash

# files and directories for making directory listings
mkdir -p /home/travis/fixtures/dir1
mkdir -p /home/travis/fixtures/.dir2
echo 'dir1/file1.txt' > /home/travis/fixtures/dir1/file1.txt
echo 'dir1/.file2.txt' > /home/travis/fixtures/dir1/.file2.txt
echo '.dir2/file3.txt' > /home/travis/fixtures/.dir2/file3.txt
echo '.dir2/.file4.txt' > /home/travis/fixtures/.dir2/.file4.txt

# file for copying locally
echo 'some content' > /home/travis/thefile.txt

# sleep script
echo '#!/usr/bin/env bash' > /home/travis/sleep.sh
echo 'echo `date`: went to bed' >> /home/travis/sleep.sh
echo 'sleep $1' >> /home/travis/sleep.sh
echo 'echo `date`: woke up' >> /home/travis/sleep.sh
