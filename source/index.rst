.. Xenon tutorial RSE 2017 documentation master file, created by
   sphinx-quickstart on Mon Aug  7 15:57:48 2017.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Xenon tutorial RSE 2017
=======================

.. toctree::

Check if xenon cli can be found and works (should print a line about usage)

.. tabs::

   .. code-tab:: bash

      xenon

Get some help

.. tabs::

   .. code-tab:: bash

      xenon --help

Check the version (should return 2.x.x)

.. tabs::

   .. code-tab:: bash

      xenon --version

I want to do something with files

.. tabs::

   .. code-tab:: bash

      xenon filesystem --help

The usage line suggests I need to pick at least one from ``{file,ftp,sftp,webdav}``.
Not sure what to do yet, let's keep it simple and ask for help on (local)
file manipulation.

.. tabs::

   .. code-tab:: bash

      xenon filesystem file --help

``xenon filesystem file`` usage line seems to suggest that I need to pick one
from ``{copy,list,mkdir,remove,rename}``. Simplest one is probably ``list``, so:

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list --help

So I need a ``path`` as final argument

.. tabs::

   .. code-tab:: bash

      # valid syntax, absolute paths
      xenon filesystem file list .
      xenon filesystem file list $PWD
      xenon filesystem file list $HOME

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list /home/tutorial/xenon

   .. code-tab:: java

      import nl.esciencecenter.xenon.filesystems.FileSystem;
      import nl.esciencecenter.xenon.filesystems.Path;
      import nl.esciencecenter.xenon.filesystems.PathAttributes;

      public class DirectoryListing {

          public static void main(String[] args) throws Exception {

              String adaptor = "file";
              FileSystem filesystem = FileSystem.create(adaptor);

              Path dir = new Path("/home/tutorial/xenon");
              boolean recursive = false;

              Iterable<PathAttributes> listing = filesystem.list(dir, recursive);

              for (PathAttributes elem : listing) {
                  if (!elem.isHidden()) {
                      System.out.println(elem.getPath());
                  }
              }

          }
      }

.. tabs::

   .. code-tab:: bash

      # valid, but returns error because env var is empty
      xenon filesystem file list $NON_EXISTENT_ENV_VAR

      # valid syntax, relative paths
      xenon filesystem file list tmp
      xenon filesystem file list build
      xenon filesystem file list ./build
      xenon filesystem file list build/install
      xenon filesystem file list build/install/..

      # additional options
      xenon filesystem file list --hidden .
      xenon filesystem file list --recursive .
      xenon filesystem file list --hidden --recursive .

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list --hidden /home/tutorial/xenon

   .. code-tab:: java

      import nl.esciencecenter.xenon.filesystems.FileSystem;
      import nl.esciencecenter.xenon.filesystems.Path;
      import nl.esciencecenter.xenon.filesystems.PathAttributes;

      public class DirectoryListingShowHidden {

          public static void main(String[] args) throws Exception {

              String adaptor = "file";
              FileSystem filesystem = FileSystem.create(adaptor);

              Path dir = new Path("/home/tutorial/xenon");
              boolean recursive = false;

              Iterable<PathAttributes> listing = filesystem.list(dir, recursive);

              for (PathAttributes elem : listing) {
                  System.out.println(elem.getPath());
              }

          }
      }

Let's try to copy a file, first create it

.. tabs::

   .. code-tab:: bash

      cd /home/daisycutter/tmp
      touch thefile.txt

Check the help

.. tabs::

   .. code-tab:: bash

      xenon filesystem file --help

So need ``copy`` argument

.. tabs::

   .. code-tab:: bash

      xenon filesystem file copy --help

First try with absolute paths and without any optional arguments

.. tabs::

   .. code-tab:: bash

      # absolute paths
      xenon filesystem file copy /home/tutorial/xenon/thefile.txt /home/tutorial/xenon/thefile.bak

   .. code-tab:: java

      import nl.esciencecenter.xenon.filesystems.CopyMode;
      import nl.esciencecenter.xenon.filesystems.CopyStatus;
      import nl.esciencecenter.xenon.filesystems.FileSystem;
      import nl.esciencecenter.xenon.filesystems.Path;

      public class CopyFileLocalToLocalAbsolutePaths {

          public static void main(String[] args) throws Exception {

              // use the local file system adaptor to create a file system representation
              String adaptor = "file";
              FileSystem filesystem = FileSystem.create(adaptor);

              // create Paths for the source and destination files, using absolute paths
              Path sourceFile = new Path("/home/tutorial/xenon/thefile.txt");
              Path destFile = new Path("/home/tutorial/xenon/thefile.bak");

              // create the destination file only if the destination path doesn't exist yet
              CopyMode mode = CopyMode.CREATE;
              boolean recursive = false;

              // perform the copy and wait 1000 ms for the successful or otherwise
              // completion of the operation
              String copyId = filesystem.copy(sourceFile, filesystem, destFile, mode, recursive);
              long timeoutMilliSecs = 1000;
              CopyStatus copyStatus = filesystem.waitUntilDone(copyId, timeoutMilliSecs);

              // print any exceptions
              if (copyStatus.getException() != null) {
                  System.out.println(copyStatus.getException().getMessage());
              }
          }
      }

.. tabs::

   .. code-tab:: bash

      rm thefile.txt

   Copying a file on the local file system using relative paths.

.. tabs::

   .. code-tab:: bash

      xenon filesystem file copy thefile.txt thefile.bak

   .. code-tab:: java

      import nl.esciencecenter.xenon.filesystems.CopyMode;
      import nl.esciencecenter.xenon.filesystems.CopyStatus;
      import nl.esciencecenter.xenon.filesystems.FileSystem;
      import nl.esciencecenter.xenon.filesystems.Path;

      public class CopyFileLocalToLocalRelativePaths {

          public static void main(String[] args) throws Exception {

              // use the local file system adaptor to create a file system representation
              String adaptor = "file";
              FileSystem filesystem = FileSystem.create(adaptor);

              // create Paths for the source and destination files, using relative paths
              Path sourceFile = new Path("thefile.txt");
              Path destFile = new Path("thefile.bak");

              // create the destination file only if the destination path doesn't exist yet
              CopyMode mode = CopyMode.CREATE;
              boolean recursive = false;

              // perform the copy and wait 1000 ms for the successful or otherwise
              // completion of the operation

              // FIXME copy expects absolute paths? If so, say so in JavaDoc and convert sourceFile and destFile
              String copyId = filesystem.copy(sourceFile, filesystem, destFile, mode, recursive);
              long timeoutMilliSecs = 1000;
              CopyStatus copyStatus = filesystem.waitUntilDone(copyId, timeoutMilliSecs);

              // print any exceptions
              if (copyStatus.getException() != null) {
                  System.out.println(copyStatus.getException().getMessage());
              }
          }
      }


What about recursive copy?

.. tabs::

   .. code-tab:: bash

      xenon filesystem file copy --recursive thedir thecopieddir
      # try again:
      xenon filesystem file copy --recursive thedir thecopieddir
      returns an error (on the first existing path?)
      xenon filesystem file copy --recursive --replace thedir thecopieddir

Standard in / standard out

.. tabs::

   .. code-tab:: bash

      cat testfile1024.txt | xenon filesystem file copy - mystdin.txt
      xenon filesystem file copy testfile1024.txt - 1> mystdout.txt

Make a directory

.. tabs::

   .. code-tab:: bash

      xenon filesystem file mkdir xenoncli-made-this-dir
      xenon filesystem file mkdir --parents xenoncli-made-this-dir/thesubdir/thesubsubdir

Copy a directory recursively

.. tabs::

   .. code-tab:: bash

      xenon filesystem file copy --recursive xenoncli-made-this-dir xenoncli-copied-this-dir

Rename/move a file

.. tabs::

   .. code-tab:: bash

      xenon filesystem file rename xenoncli-copied-this-dir/ xenoncli-moved-this-dir

Remove a directory

.. tabs::

   .. code-tab:: bash

      xenon filesystem file remove xenoncli-made-this-dir/thesubdir/thesubsubdir

Remove a directory (recursively)

.. tabs::

   .. code-tab:: bash

      xenon filesystem file remove --recursive xenoncli-moved-this-dir/


Now let's see if we can use schedulers, starting with SLURM.
First need to bring up a 'remote' SLURM.
We'll use a SLURM docker container called ``nlesc/xenon-slurm`` from DockerHub.

.. tabs::

   .. code-tab:: bash

      docker run --detach --publish 10022:22 nlesc/xenon-slurm:17

      # use docker ps to check the state of the container
      docker ps

      # once the status is healthy, see if we can ssh into it as
      # user 'xenon' with password 'javagat'
      ssh -p 10022 xenon@localhost

      # if that works, exit again
      exit

.. tabs::

   .. code-tab:: bash

      # let's see what help is available for slurm
      xenon scheduler slurm --help

Let's first ask what queues the slurm scheduler has. We need to specify location,
otherwise we don't know who to ask. According to the help, ``LOCATION`` is any
location format supported by ``ssh`` or ``local`` scheduler.

.. tabs::

   .. code-tab:: bash

      xenon scheduler slurm --location ssh://xenon@localhost queues    # errors, invalid LOCATION string format
      xenon scheduler slurm --location ssh://localhost queues          # also errors
      xenon scheduler slurm --location localhost queues                # error because it tries to connect to default port 22 (should be 10022)

      # check the xenon scheduler ssh --help help to find out the valid syntax for LOCATION
      xenon scheduler ssh --help
      xenon scheduler slurm --location localhost:10022 queues          # immediately returns authentication timeout error (a bit confusing, it's not a timeout issue I think FIXME)

      # we need to also provide credentials
      xenon scheduler slurm --help

      # so --username and --password
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 queues   # winning!
      # returns:
      Available queues: mypartition, otherpartition
      Default queue: mypartition

What else we got

.. tabs::

   .. code-tab:: bash

      xenon scheduler slurm --help

So can choose from ``{exec,submit,list,remove,wait,queues}``. Let's try to list the scheduler's queues.

.. tabs::

   .. code-tab:: bash

      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 list
      # works, not very exciting because empty

Let's try to run an executable

.. tabs::

   .. code-tab:: bash

      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 exec   # doesn't work

Usage string suggests that I need to provide (the path of) an executable residing in the container. For example, ``/bin/hostname``

.. tabs::

   .. code-tab:: bash

      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 exec /bin/hostname
      # returns the image id of the docker container, all good

      # what about an 'ls' (/bin/ls)
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 exec /bin/ls
      # returns the listing for the current directory (the last WORKDIR? or the place where SSH ends up? not sure)

      # usage string suggests you can set the working directory:
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 \
          exec --working-directory /home/xenon /bin/ls     # all good

      # try other directory
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 \
          exec --working-directory /home/xenon/filesystem-test-fixture /bin/ls

      # misspelled directory
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 \
          exec --working-directory /home/xenon/filesystem-test-fxture /bin/ls
      # says dir doesn't exist

      # try with arguments to the executable (prepend -l option with ' -- ')
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 exec ls -- -l
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 exec ls -- -la
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 exec ls -- -lR
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 exec ls -- -l --human-readable

      # these two work as expected:
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 exec which sleep
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 exec sleep 20

      # let's try adding some verbosity
      xenon --verbose scheduler slurm --username xenon --password javagat --location localhost:10022 exec sleep 5
      xenon -v scheduler slurm --username xenon --password javagat --location localhost:10022 exec sleep 5
      # very verbose
      xenon -vvvv scheduler slurm --username xenon --password javagat --location localhost:10022 exec sleep 5


Check if you can provide passwords from a file

.. tabs::

   .. code-tab:: bash

      echo javagat > password.txt & xenon scheduler slurm --username xenon --password @password.txt \
          --location localhost:10022 exec /bin/hostname

Time to submit stuff, check the ``xenon scheduler slurm submit`` help

.. tabs::

   .. code-tab:: bash

      xenon scheduler slurm submit --help

      # submit a plain 'ls -la' job
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 submit ls -- -la

      # submit an 'env' job with environment variables
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 \
          submit --env MYKEY=myvalue env

      # submit an 'env' job with environment variables, and capture standard out so we know if it worked
      xenon scheduler slurm --username xenon --password javagat --location localhost:10022 \
          submit --env MYKEY=myvalue --stdout=out.txt env

      # check to see if the output from 'env' was written to file /home/xenon/out.txt
      ssh -p 10022 xenon@localhost ls -l
      # see what's in it
      ssh -p 10022 xenon@localhost cat out.txt

Now how do we get the output file back to our local system? We can't use ``xenon filesystem file`` like before, because
we're copying between file systems, so let's look at what other options are available:

.. tabs::

   .. code-tab:: bash

      xenon filesystem --help

      # let's try sftp
      xenon filesystem sftp --help

      # so basic syntax is
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat <something>

      # we could list the contents of the remote system, check how
      xenon filesystem sftp list --help

.. tabs::

   .. code-tab:: bash

      # so 'list' command, followed by a path
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat list /home/xenon

   .. code-tab:: java

      import nl.esciencecenter.xenon.filesystems.FileSystem;
      import nl.esciencecenter.xenon.filesystems.Path;
      import nl.esciencecenter.xenon.filesystems.PathAttributes;
      import nl.esciencecenter.xenon.credentials.PasswordCredential;

      public class DirectoryListingWithPasswordCredential {

          public static void main(String[] args) throws Exception {

              String username = "xenon";
              char[] password = "javagat".toCharArray();
              PasswordCredential credential = new PasswordCredential(username, password);

              String adaptor = "sftp";
              String location = "localhost:10022";

              FileSystem filesystem = FileSystem.create(adaptor, location, credential);

              Path dir = new Path("/home/xenon");
              boolean recursive = false;

              Iterable<PathAttributes> listing = filesystem.list(dir, recursive);

              for (PathAttributes elem : listing) {
                  if (!elem.isHidden) {
                     System.out.println(elem.getPath());
                  }
              }
          }
      }

.. tabs::

   .. code-tab:: bash

      # also list hidden files
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat list --hidden /home/xenon

   .. code-tab:: java

      import nl.esciencecenter.xenon.filesystems.FileSystem;
      import nl.esciencecenter.xenon.filesystems.Path;
      import nl.esciencecenter.xenon.filesystems.PathAttributes;
      import nl.esciencecenter.xenon.credentials.PasswordCredential;

      public class DirectoryListingWithPasswordCredentialShowHidden {

          public static void main(String[] args) throws Exception {

              String username = "xenon";
              char[] password = "javagat".toCharArray();
              PasswordCredential credential = new PasswordCredential(username, password);

              String adaptor = "sftp";
              String location = "localhost:10022";

              FileSystem filesystem = FileSystem.create(adaptor, location, credential);

              Path dir = new Path("/home/xenon");
              boolean recursive = false;

              Iterable<PathAttributes> listing = filesystem.list(dir, recursive);

              for (PathAttributes elem : listing) {
                  System.out.println(elem.getPath());
              }
          }
      }

.. tabs::

   .. code-tab:: bash

      # let's see what --long does
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat list --long /home/xenon

      # what about JSON format?
      xenon --json filesystem sftp --location localhost:10022 --username xenon --password javagat list --long /home/xenon
      # returns a whole bunch of JSON

      # in combination with --recursive
      xenon --json filesystem sftp --location localhost:10022 --username xenon --password javagat \
          list --long --recursive /home/xenon

Nice, but we were trying to get a look at ``/home/xenon/out.txt`` to see if the ``xenon scheduler slurm submit`` job
worked. ``xenon filesystem sftp --help`` also listed a ``download`` command, let's see how that's supposed to work.

.. tabs::

   .. code-tab:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat download --help
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download /home/xenon/out.txt /home/daisycutter/tmp/out.txt

      # repeat operation to see what happens if local file exists
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download /home/xenon/out.txt /home/daisycutter/tmp/out.txt
      # error message and download aborted

      # what happens if remote file does not exist
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download /home/xenon/doesnotexist.txt /home/daisycutter/tmp/doesnotexist.txt
      # xenon says does not exist, nice

      # can we copy remote file to local dir?
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download /home/xenon/out.txt /home/daisycutter/tmp
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download /home/xenon/out.txt /home/daisycutter/tmp/
      # no on both accounts

      # what about directories?
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download /home/xenon/filesystem-test-fixture /home/daisycutter/tmp/thedir
      # complains about source being a directory

      # add --recursive option
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download --recursive /home/xenon/filesystem-test-fixture /home/daisycutter/tmp/thedir
      # (only copies files and directories, not links)

      # with relative target path
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download /home/xenon/out.txt out.txt

FIXME This next code block needs checking to see if the error is fixed

.. tabs::

   .. code-tab:: bash

      # with relative source path
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download out.txt /home/daisycutter/tmp/out.txt
      # says
      # sftp adaptor: No such file out.txt

      # Q: is there a starting directory somehow? It's / I think

      # let's try to do recursively copy of whatever directory we land in to local system:
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat download --recursive . thedir
      sftp adaptor: SFTP error (SSH_FX_PERMISSION_DENIED): Permission denied

      # not sure what . means in this context, from the error it could be / ...? at least this generates the same error
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat download --recursive / thedir
      # and
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat list .
      # lists the contents of /
      # Q maybe disallow the use of . as a remote location
      # Q should we have a default value for path, such that xenon filesystem sftp list <empty location>
      # returns list of / for example.

      # back to xenon filesystem sftp download, let
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download --recursive /home thedir
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download --recursive /home /home/daisycutter/tmp/thedir

      # TODO because we don't support directories in the location (I think), the source path is effectively always an
      # absolute path. Maybe say so in the docs?


``xenon filesystem sftp download`` can also download to the local system's standard out, as follows (note the minus at the end)

.. tabs::

   .. code-tab:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat download /home/xenon/out.txt -

Just like download, we can also upload a file. Let's first make it by ``echo``'ing some content into it:

.. tabs::

   .. code-tab:: bash

      echo 'this is coming from stdin through a file' > stdin.txt

Now we can upload it:

.. tabs::

   .. code-tab:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat upload \
          stdin.txt /home/xenon/stdin.txt

Now we can submit a ``cat`` job using ``xenon scheduler slurm submit`` like before, taking the newly uploaded
``stdin.txt`` file as standard in to the ``cat`` program. We'll redirect ``cat``'s standard out to a file ``stdout.txt``
like before.

.. tabs::

   .. code-tab:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
          submit --stdin /home/xenon/stdin.txt --stdout /home/xenon/stdout.txt cat

      # download the stdout file xenon generated to see its contents (should be same as ``stdin.txt``)
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          download /home/xenon/stdout.txt stdout.txt

Checking on jobs

.. tabs::

   .. code-tab:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat submit sleep 100
      # on return says job identifier is e.g. 10
      # while the sleep job is running, do
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list
      # this queue has job 10 in it

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list --queue mypartition
      # this queue has job 10 in it

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list --queue otherpartition
      # this queue is empty

      # submit 3 sleep jobs one after the other
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat submit sleep 100
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat submit sleep 100
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat submit sleep 100
      # check the response, job ids are 12-14

      # remove job id 13
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat remove 13

      # check the queues
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list
      # only has job 12 and 14

      # capturing job ids in scripts
      JOBID=$(xenon scheduler slurm --location localhost:10022 --username xenon --password javagat submit sleep 100)
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat remove $JOBID

Moving files around on the remote

.. tabs::

   .. code-tab:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          upload thefile.txt /home/xenon/thefile.txt
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          copy /home/xenon/thefile.txt localhost:10022 /home/xenon/thefile.bak
      # FIXME returns authentication timeout

      # FIXME need to add the not-so optional --target-username and --target-password
      # TODO maybe keep --target-location --target-username --target-password together at one level?
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          copy --target-username xenon --target-password javagat \
          /home/xenon/thefile.txt localhost:10022 /home/xenon/thefile.bak

      # now try with a directory
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          copy --target-username xenon --target-password javagat \
          /home/xenon/filesystem-test-fixture localhost:10022 /home/xenon/thedir
      sftp adaptor: Source path is a directory: /home/xenon/filesystem-test-fixture
      # all good, add --recursive
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
          copy --recursive --target-username xenon --target-password javagat \
          /home/xenon/filesystem-test-fixture localhost:10022 /home/xenon/thedir
      # works, except doesn't copy links

      # let's try with credentials FIXME
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat copy --recursive \
          --target-certfile /home/daisycutter/github/nlesc/xenon-docker-images/xenon-slurm-ssh/.ssh/id_rsa \
          /home/xenon/filesystem-test-fixture localhost:10022 /home/xenon/thedir
      sftp adaptor: Failed to retrieve username from credential

      xenon filesystem sftp --location localhost:10022 \
          --certfile /home/daisycutter/github/nlesc/xenon-docker-images/xenon-slurm-ssh/.ssh/id_rsa \
          download /home/xenon/thefile.txt thefile.bak
      sftp adaptor: Failed to retrieve username from credential
