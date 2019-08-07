#!/usr/bin/env bash

# some examples need a running docker, see .travis.yml

run_snippet () {
   echo "travis_fold:start:$1"
   echo "[output of $1]"
   bash $1 || exit 1; 
   echo "travis_fold:end:$1"
}

xenon scheduler slurm --location ssh://localhost:10022 \
--username xenon --password javagat \
--prop xenon.adaptors.schedulers.ssh.strictHostKeyChecking=false queues

snippets="DirectoryListing.sh \
          DirectoryListingShowHidden.sh \
          DirectoryListingRecursive.sh \
          CopyFileLocalToLocalAbsolutePaths.sh \
          SlurmQueuesGetter.sh
          UploadFileLocalToSftpAbsolutePaths.sh
          SlurmJobListGetter.sh \
          AllTogetherNowWrong.sh \
          AllTogetherNow.sh \
          DownloadFileSftpToLocalAbsolutePaths.sh"

for snippet in $snippets ; 
do
   run_snippet $snippet
done
 
exit 0;
