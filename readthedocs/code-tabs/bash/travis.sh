#!/usr/bin/env bash

# some examples need a running docker, see .travis.yml
set -x
bash DirectoryListing.sh || exit 1; 
# bash DirectoryListingShowHidden.sh || exit 1;
# bash DirectoryListingRecursive.sh || exit 1;
# bash CopyFileLocalToLocalAbsolutePaths.sh || exit 1;
bash SlurmQueuesGetter.sh || exit 1;
# bash UploadFileLocalToSftpAbsolutePaths.sh || exit 1;
# bash SlurmJobListGetter.sh || exit 1;
# bash DownloadFileSftpToLocalAbsolutePaths.sh || exit 1;
# bash AllTogetherNowWrong.sh || exit 1;
# bash AllTogetherNow.sh || exit 1;
 
exit 0;