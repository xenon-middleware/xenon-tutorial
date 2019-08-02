#!/usr/bin/env bash

# files and directories for making directory listings
mkdir fixtures/dir1
mkdir fixtures/.dir2
echo 'dir1/file1.txt' > fixtures/dir1/file1.txt
echo 'dir1/.file2.txt' > fixtures/dir1/.file2.txt
echo '.dir2/file3.txt' > fixtures/.dir2/file3.txt
echo '.dir2/.file4.txt' > fixtures/.dir2/.file4.txt

# file for copying locally
echo 'some content' > thefile.txt

# sleep script
echo '#!/usr/bin/env bash' > sleep.sh
echo 'echo `date`: went to bed' >> sleep.sh
echo 'sleep $1' >> sleep.sh
echo 'echo `date`: woke up' >> sleep.sh

