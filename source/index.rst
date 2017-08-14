.. Xenon tutorial RSE 2017 documentation master file, created by
   sphinx-quickstart on Mon Aug  7 15:57:48 2017.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

|
|

.. image:: _static/xenon-logo.png
   :scale: 50 %
   :alt: Xenon
   :align: center

|
|
|


Xenon tutorial RSE 2017
=======================

.. toctree::

First, let's check if the ``xenon`` command line interface program can be found on the system:

.. code-block:: bash

   xenon --help

----------------------------
Interacting with filesystems
----------------------------

Essentially, we can use ``xenon`` to manipulate files or to interact with schedulers. Let's start simple and see if
we can do something with files. First, check its help:

.. code-block:: bash

      xenon filesystem --help

The usage line suggests we need to pick one from ``{file,ftp,sftp,webdav}``.
Again, choose what seems to be the simplest option (``file``), and again, check its help.

.. code-block:: bash

      xenon filesystem file --help

``xenon filesystem file`` usage line seems to suggest that I need to pick one
from ``{copy,list,mkdir,remove,rename}``. Simplest one is probably ``list``, so:

.. code-block:: bash

      xenon filesystem file list --help

So we need a ``path`` as final argument.

In case you hadn't noticed the pattern, stringing together any number of ``xenon`` subcommands and appending ``--help``
to it will get you help on the particular combination of subcommands you supplied.

Let's try listing the contents of ``/home/tutorial/xenon``.

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list /home/tutorial/xenon

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListing.java.txt

``xenon filesystem file list`` has a few options that let you specify the details of the list operation, e.g. ``--hidden``

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list --hidden /home/tutorial/xenon

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListingShowHidden.java.txt

and ``--recursive``

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list --recursive /home/tutorial/xenon

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListingRecursive.java.txt

Now let's try to copy a file, first create it

.. code-block:: bash

      cd /home/tutorial/xenon
      echo 'some content' > thefile.txt

Check the help

.. code-block:: bash

      xenon filesystem file --help

So need ``copy`` argument

.. code-block:: bash

      xenon filesystem file copy --help

First try with absolute paths and without any optional arguments

.. tabs::

   .. code-tab:: bash

      xenon filesystem file copy /home/tutorial/xenon/thefile.txt /home/tutorial/xenon/thefile.bak

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/CopyFileLocalToLocalAbsolutePaths.java.txt

.. code-block:: bash

   rm thefile.txt

Copying a file on the local file system using relative paths.

.. tabs::

   .. code-tab:: bash

      xenon filesystem file copy thefile.txt thefile.bak

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/CopyFileLocalToLocalRelativePaths.java.txt

What about recursive copy?

.. code-block:: bash

      xenon filesystem file copy --recursive thedir thecopieddir
      # try again:
      xenon filesystem file copy --recursive thedir thecopieddir
      returns an error (on the first existing path?)
      xenon filesystem file copy --recursive --replace thedir thecopieddir

Standard in / standard out

.. code-block:: bash

      cat testfile1024.txt | xenon filesystem file copy - mystdin.txt
      xenon filesystem file copy testfile1024.txt - 1> mystdout.txt

Make a directory

.. code-block:: bash

      xenon filesystem file mkdir xenoncli-made-this-dir
      xenon filesystem file mkdir --parents xenoncli-made-this-dir/thesubdir/thesubsubdir

Copy a directory recursively

.. code-block:: bash

      xenon filesystem file copy --recursive xenoncli-made-this-dir xenoncli-copied-this-dir

Rename/move a file

.. code-block:: bash

      xenon filesystem file rename xenoncli-copied-this-dir/ xenoncli-moved-this-dir

Remove a directory

.. code-block:: bash

      xenon filesystem file remove xenoncli-made-this-dir/thesubdir/thesubsubdir

Remove a directory (recursively)

.. code-block:: bash

      xenon filesystem file remove --recursive xenoncli-moved-this-dir/

---------------------------
Interacting with schedulers
---------------------------

Now let's see if we can use schedulers, starting with SLURM. For this part, we need access to a machine that is running
SLURM. To avoid problems related to network connectivity, we won't try to connect to a physically remote SLURM machine,
but instead, we'll use a dockerized SLURM installation. This way, we can mimic whatever infrastructure we need. The
setup will thus be something like this:

.. image:: _static/babushka.svg.png
   :height: 300px
   :alt: babushka
   :align: center

A copy of the SLURM Docker image (``nlesc/xenon-slurm:17``) has been included in the RSE 2017 virtual machine. Bring it
up with:

.. code-block:: bash

      docker run --detach --publish 10022:22 nlesc/xenon-slurm:17

Use ``docker ps`` to check the state of the container

.. code-block:: bash

      docker ps

Once the status is ``healthy``, see if we can ``ssh`` into it on port ``10022`` as user ``xenon`` with password
``javagat``:

.. code-block:: bash

      ssh -p 10022 xenon@localhost

      # if that works, exit again
      exit

.. code-block:: bash

      # let's see what help is available for slurm
      xenon scheduler slurm --help

Let's first ask what queues the SLURM scheduler has. For this, we need to specify
a location, otherwise ``xenon`` does not know who to ask for the list of queues. According to the help,
``LOCATION`` is any location format supported by ``ssh`` or ``local`` scheduler.
Our dockerized SLURM machine is reachable as ``localhost:10022``.
We'll also need to provide a ``--username`` and ``--password``
for that location, as follows:

.. tabs::

    .. code-tab:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat queues
      # returns:
      Available queues: mypartition, otherpartition
      Default queue: mypartition

    .. include:: java/nl/esciencecenter/xenon/examples/schedulers/SlurmQueuesGetter.java.txt

In case you are reluctant to type plaintext passwords on the command line, for example because of logging in
``~/.bash_history``, know that you can supply passwords from a file, as follows:

.. code-block:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password @password.txt queues

in which the file ``password.txt`` should contain the password. Since everything about the user ``xenon`` is public
knowledge anyway, such security precautions are not needed for this tutorial, so we'll just continue to use the
``--password PASSWORD`` syntax.

What else we got

.. code-block:: bash

      xenon scheduler slurm --help

So can choose from ``{exec,submit,list,remove,wait,queues}``. Let's try to list the scheduler's queues.

.. code-block:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list
      # works, not very exciting because empty

Now, let's try to run an executable. The usage string for ``xenon scheduler slurm exec`` suggests that we need to
provide (the path of) an ``executable`` residing in the container. For example, the executable ``/bin/hostname`` should
return the ``CONTAINER ID`` of the Docker container (since our ``docker run`` command did not specify a custom hostname,
the hostname has been set equal to the container's id):

.. code-block:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat exec /bin/hostname

Time to submit stuff, check the ``xenon scheduler slurm submit`` help

.. code-block:: bash

      xenon scheduler slurm submit --help

      # submit a plain 'ls -la' job
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat submit ls -- -la

      # submit an 'env' job with environment variables
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
      submit --env MYKEY=myvalue env

      # submit an 'env' job with environment variables, and capture standard out so we know if it worked
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
      submit --env MYKEY=myvalue --stdout=out.txt env

      # check to see if the output from 'env' was written to file /home/xenon/out.txt
      ssh -p 10022 xenon@localhost ls -l
      # see what's in it
      ssh -p 10022 xenon@localhost cat out.txt

-----------------------------------------------------
Typical usage -- combining filesystems and schedulers
-----------------------------------------------------

Now how do we get the output file back to our local system? We can't use ``xenon filesystem file`` like before, because
we're copying between file systems, so let's look at what other options are available:

.. code-block:: bash

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

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListingWithPasswordCredential.java.txt

.. tabs::

   .. code-tab:: bash

      # also list hidden files
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat list --hidden /home/xenon

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListingWithPasswordCredentialShowHidden.java.txt

.. code-block:: bash

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

.. code-block:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat download --help

.. tabs::

   .. code-tab:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
      download /home/xenon/out.txt /home/daisycutter/tmp/out.txt

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DownloadFileSftpToLocalAbsolutePaths.java.txt

.. code-block:: bash

      # for directories, need to add --recursive option
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
      download --recursive /home/xenon/filesystem-test-fixture /home/daisycutter/tmp/thedir


``xenon filesystem sftp download`` can also download to the local system's standard out, as follows (note the minus at
the end)

.. code-block:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat download \
      /home/xenon/out.txt -

Just like download, we can also upload a file. Let's first make it by ``echo``'ing some content into it:

.. code-block:: bash

      echo 'this is coming from stdin through a file' > stdin.txt

Now we can upload it:

.. tabs::

   .. code-tab:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat upload \
      /home/tutorial/xenon/stdin.txt /home/xenon/stdin.txt

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/UploadFileLocalToSftpAbsolutePaths.java.txt

Now we can submit a ``cat`` job using ``xenon scheduler slurm submit`` like before, taking the newly uploaded
``stdin.txt`` file as standard in to the ``cat`` program. We'll redirect ``cat``'s standard out to a file ``stdout.txt``
like before.

.. code-block:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
      submit --stdin /home/xenon/stdin.txt --stdout /home/xenon/stdout.txt cat

      # download the stdout file xenon generated to see its contents (should be same as ``stdin.txt``)
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
      download /home/xenon/stdout.txt stdout.txt

Checking on jobs

.. code-block:: bash

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

Let's check the queues:

.. tabs::

    .. code-tab:: bash

       xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list
       # only has job 12 and 14

    .. include:: java/nl/esciencecenter/xenon/examples/schedulers/SlurmJobListGetter.java.txt

.. code-block:: bash

      # capturing job ids in scripts
      JOBID=$(xenon scheduler slurm --location localhost:10022 --username xenon --password javagat submit sleep 100)
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat remove $JOBID

Moving files around on the remote

.. code-block:: bash

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



---------------
Further reading
---------------

- Xenon's JavaDoc
- Xenon examples repository
- pyXenon repository
