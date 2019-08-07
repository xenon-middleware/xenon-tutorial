#!/usr/bin/env bash

if [ $CI ]; then
   echo "Making fixtures in /home/travis"
   FIXTURE_PREFIX=''
else
   echo "Making fixtures in /home/<user>/tmp/home/travis"
   FIXTURE_PREFIX=$HOME/tmp
fi
   

# files and directories for making directory listings
mkdir -p $FIXTURE_PREFIX/home/travis/fixtures/dir1
mkdir -p $FIXTURE_PREFIX/home/travis/fixtures/.dir2
echo 'dir1/file1.txt' > $FIXTURE_PREFIX/home/travis/fixtures/dir1/file1.txt
echo 'dir1/.file2.txt' > $FIXTURE_PREFIX/home/travis/fixtures/dir1/.file2.txt
echo '.dir2/file3.txt' > $FIXTURE_PREFIX/home/travis/fixtures/.dir2/file3.txt
echo '.dir2/.file4.txt' > $FIXTURE_PREFIX/home/travis/fixtures/.dir2/.file4.txt

# file for copying locally
echo 'some content' > $FIXTURE_PREFIX/home/travis/thefile.txt

# sleep script
echo '#!/usr/bin/env bash' > $FIXTURE_PREFIX/home/travis/sleep.sh
echo 'echo `date`: went to bed' >> $FIXTURE_PREFIX/home/travis/sleep.sh
echo 'sleep $1' >> $FIXTURE_PREFIX/home/travis/sleep.sh
echo 'echo `date`: woke up' >> $FIXTURE_PREFIX/home/travis/sleep.sh
