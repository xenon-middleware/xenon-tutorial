.. Xenon tutorial RSE 2017 documentation master file, created by
   sphinx-quickstart on Mon Aug  7 15:57:48 2017.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

|
|
|

Getting started
---------------

On your system, start VirtualBox.

All tutorials at `RSE2017`__ use the same virtual machine. In case you don't have a copy of the virtual machine, you can
download it from GoogleDrive `here`__. After the download finishes, click ``File`` in VirtualBox, then
``Import appliance``, then select the file you downloaded.

__ http://rse.ac.uk/conf2017/
__ https://drive.google.com/file/d/0B1GaxSkd5lU8MTFxN3JLaHlXT2s/view

During the import, you'll see an initialization wizard. Make sure that the virtual machine is configured with two CPUs.

Start the Fedora virtual machine and log in with password ``tutorial``.

Once the system has booted, Click ``Activities`` and then start both a terminal and Firefox by clicking their respective
icons. Use Firefox to navigate to the tutorial text at `<http://xenonrse2017.readthedocs.io>`_.

In the terminal, change directory to ``/home/tutorial/xenon`` and confirm that the ``xenon`` command line interface
program can be found on the system:

.. code-block:: bash

   cd xenon
   xenon --help

|
|
|

Interacting with filesystems
----------------------------

Essentially, ``xenon`` can be used to manipulate files and to interact with schedulers, where either one can be local
or remote. Let's start simple and see if we can do something with local files. First, check its help:

.. code-block:: bash

      xenon filesystem --help

The usage line suggests we need to pick one from ``{file,ftp,sftp,webdav}``.
Again, choose what seems to be the simplest option (``file``), and again, check its help.

.. code-block:: bash

      xenon filesystem file --help

``xenon filesystem file``'s usage line seems to suggest that I need to pick one
from ``{copy,list,mkdir,remove,rename}``. Simplest one is probably ``list``, so:

.. code-block:: bash

      xenon filesystem file list --help

So we need a ``path`` as final argument.

In case you hadn't noticed the pattern, stringing together any number of ``xenon`` subcommands and appending ``--help``
to it will get you help on the particular combination of subcommands you supplied.

The focus of this tutorial is on using Xenon's command line interface, but be aware that other programming
interfaces are available through `gRPC`__. Where relevant, we have included equivalent code snippets,
written in Java and Python, as separate tabs.

__ https://grpc.io/

Let's try listing the contents of ``/home/tutorial/xenon``.

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list /home/tutorial/xenon

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListing.java.txt

The result should be more or less the same as that of ``ls -1``.

``xenon filesystem file list`` has a few options that let you specify the details of the list operation, e.g.
``--hidden``

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list --hidden /home/tutorial/xenon

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListingShowHidden.java.txt

and ``--recursive``

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list --recursive /home/tutorial/xenon

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListingRecursive.java.txt

Now let's create a file and try to use ``xenon`` to copy it:

.. code-block:: bash

      cd /home/tutorial/xenon
      echo 'some content' > thefile.txt

Check the relevant help

.. code-block:: bash

      xenon filesystem file --help
      xenon filesystem file copy --help

So, the ``copy`` subcommand takes a source path and a target path:

.. tabs::

   .. code-tab:: bash

      xenon filesystem file copy /home/tutorial/xenon/thefile.txt /home/tutorial/xenon/thefile.bak

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/CopyFileLocalToLocalAbsolutePaths.java.txt

Note that the source path may be read from standard input, and that the target path may be written to standard output:

.. code-block:: bash

      cat thefile.txt | xenon filesystem file copy - mystdin.txt
      xenon filesystem file copy thefile.txt - 1> mystdout.txt

``xenon filesystem file`` has a few more subcommands, namely ``mkdir``, ``rename`` and ``remove``. You can
experiment a bit more with those or move on to the next section.

|
|
|

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

      docker run --detach --publish 10022:22 --hostname slurm17 nlesc/xenon-slurm:17

Use ``docker ps`` to check the state of the container

.. code-block:: bash

      docker ps

Once the status is ``healthy``, see if we can ``ssh`` into it on port ``10022`` as user ``xenon`` with password
``javagat``:

.. code-block:: bash

      ssh -p 10022 xenon@localhost

      # if that works, exit again
      exit

Check the help to see how the ``slurm`` subcommand works:

.. code-block:: bash

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

Besides ``queues``, other ``slurm`` subcommands are ``exec``, ``submit``, ``list``, ``remove``, and ``wait``. Let's try
to have ``xenon`` ask SLURM for its list of jobs in each queue, as follows:

.. code-block:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list
      # should work, but we don't have any jobs yet

Now, let's try to submit a job using ``slurm submit``. Its usage string suggests that we need to provide (the path
of) an ``executable``. Note that the executable should be present inside the container when SLURM starts its execution.
For the moment, we'll use some executables that come standard with most Linux'es, such as ``/bin/hostname``. It should
return the hostname ``slurm17`` of the Docker container, or whatever hostname you specified for it when you ran the
``docker run`` command earlier:

.. code-block:: bash

      # check the slurm submit help for correct syntax
      xenon scheduler slurm submit --help

      # let xenon submit a job with /bin/hostname as executable
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
      submit /bin/hostname

      # add --stdout to the submit job to capture its standard out so we know it worked:
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
      submit --stdout hostname.stdout.txt /bin/hostname

      # check to see if the output was written to file /home/xenon/hostname.stdout.txt
      ssh -p 10022 xenon@localhost ls -l
      # see what's in it
      ssh -p 10022 xenon@localhost cat hostname.stdout.txt

Below are a few more examples of ``slurm submit``:

.. code-block:: bash

      # executables that take options prefixed with '-' need special syntax, e.g. 'ls -la'
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
      submit --stdout /home/xenon/ls.stdout.txt ls -- -la

      # check to see if the output was written to file /home/xenon/ls.stdout.txt
      ssh -p 10022 xenon@localhost ls -l
      # see what's in it
      ssh -p 10022 xenon@localhost cat ls.stdout.txt

      # submit an 'env' job with environment variable MYKEY, and capture standard out so we know it worked
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
      submit --stdout env.stdout.txt --env MYKEY=myvalue env

      # check to see if the output from 'env' was written to file /home/xenon/env.stdout.txt
      ssh -p 10022 xenon@localhost ls -l
      # see what's in it
      ssh -p 10022 xenon@localhost cat env.stdout.txt

|
|
|

Typical usage -- combining filesystems and schedulers
-----------------------------------------------------

Now how do we get the output files we generated back to our local system? We can't use ``xenon filesystem file`` like
before, because we're copying between file systems, so let's look at what other options are available:

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

``xenon filesystem sftp --help`` also included a ``download`` command, let's see how that's supposed to work.

.. code-block:: bash

      xenon filesystem sftp download --help

.. tabs::

   .. code-tab:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
      download /home/xenon/hostname.stdout.txt /home/tutorial/xenon/hostname.stdout.txt

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DownloadFileSftpToLocalAbsolutePaths.java.txt

.. code-block:: bash

      # for directories, need to add --recursive option
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
      download --recursive /home/xenon/filesystem-test-fixture /home/tutorial/xenon/fixtures

Just like download, we can also upload a file. Let's first make it by ``echo``'ing some content into it:

.. code-block:: bash

      echo 'this is coming from stdin through a file' > cat.stdin.txt

Now we can upload it:

.. tabs::

   .. code-tab:: bash

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat upload \
      /home/tutorial/xenon/cat.stdin.txt /home/xenon/cat.stdin.txt

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/UploadFileLocalToSftpAbsolutePaths.java.txt

Now we can submit a ``cat`` job using ``xenon scheduler slurm submit`` like before, taking the newly uploaded
``cat.stdin.txt`` file as standard input to the ``cat`` program. We'll redirect ``cat``'s standard output to a file
``cat.stdout.txt`` like before.

.. code-block:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
      submit --stdin /home/xenon/cat.stdin.txt --stdout /home/xenon/cat.stdout.txt cat

      # download the stdout file xenon generated to see its contents (should be same as 'cat.stdin.txt')
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
      download /home/xenon/cat.stdout.txt /home/tutorial/xenon/cat.stdout.txt

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
      upload /home/tutorial/xenon/thefile.txt /home/xenon/thefile.txt

      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
      copy --target-username xenon --target-password javagat \
      /home/xenon/thefile.txt localhost:10022 /home/xenon/thefile.bak

|
|
|

What's next?
------------

Congratulations--you've successfully completed the tutorial! If you want, you can continue reading about relevant
subjects `here`__, or try some of the suggested exercises `here`__.

__ #further-reading
__ #suggested-exercises

Further reading
^^^^^^^^^^^^^^^

- Xenon's JavaDoc
- Xenon examples repository
- pyXenon repository

Suggested exercises
^^^^^^^^^^^^^^^^^^^

- exercise 1
- exercise 2





