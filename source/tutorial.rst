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

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListing.java

The result should be more or less the same as that of ``ls -1``.

``xenon filesystem file list`` has a few options that let you specify the details of the list operation, e.g.
``--hidden``

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list --hidden /home/tutorial/xenon

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListingShowHidden.java

and ``--recursive``

.. tabs::

   .. code-tab:: bash

      xenon filesystem file list --recursive /home/tutorial/xenon

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DirectoryListingRecursive.java

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

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/CopyFileLocalToLocalAbsolutePaths.java

Note that the source path may be standard input, and that the target path may be standard output:

.. code-block:: bash

      # read from stdin:
      cat thefile.txt | xenon filesystem file copy - mystdin.txt

      # write to stdout:
      xenon filesystem file copy thefile.txt - 1> mystdout.txt

``xenon filesystem file`` has a few more subcommands, namely ``mkdir``, ``rename`` and ``remove``. You can
experiment a bit more with those or move on to the next section.

|
|
|

Interacting with schedulers
---------------------------

Now let's see if we can use schedulers, starting with `SLURM`__. For this part, we need access to a machine that is running
SLURM. To avoid problems related to network connectivity, we won't try to connect to a physically remote SLURM machine,
but instead, we'll use a dockerized SLURM installation. This way, we can mimic whatever infrastructure we need. The
setup will thus be something like this:

__ https://slurm.schedmd.com/

.. image:: _static/babushka.svg.png
   :height: 300px
   :alt: babushka
   :align: center

|
|

A copy of the SLURM Docker image (`nlesc/xenon-slurm`__:17) has been included in the RSE 2017 virtual machine. Bring it
up with:

__ https://hub.docker.com/r/nlesc/xenon-slurm/

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

    .. include:: java/nl/esciencecenter/xenon/examples/schedulers/SlurmQueuesGetter.java

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
For the moment, we'll use ``/bin/hostname`` as the executable. It should return the hostname ``slurm17`` of the Docker
container, or whatever hostname you specified for it when you ran the ``docker run`` command earlier:

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

Combining filesystems and schedulers
------------------------------------

So far, we've used ``xenon`` to manipulate files on the local filesystem, and to run system executables on the remote
machine. In typical usage, however, you would use ``xenon`` to run executables or scripts of your own, which means that
we need to upload such files from the local system to the remote system.

A typical workflow may thus look like this:

   1. upload input file(s)
   2. submit job
   3. download generated output file(s)

Use an editor to create a file ``sleep.sh`` with the following contents (the RSE 2017 virtual machine comes with
``gedit``, or you can install an other editor from the repositories if you like):

.. include:: bash/sleep.sh

You can test if your file is correct by:

.. code-block:: bash

      # last argument is the sleep duration in seconds
      bash sleep.sh 5

We need to upload ``sleep.sh`` to the remote machine. We can't use ``xenon filesystem file`` like we did before,
because we're copying between file systems, so let's look at what other options are available:

.. code-block:: bash

      xenon filesystem --help

      # let's try sftp protocol
      xenon filesystem sftp --help

      # we're interested in 'upload' for now
      xenon filesystem sftp upload --help

We'll also need to tell ``xenon`` what location we want to connect to, and what credentials to use. The SLURM Docker
container we used before is accessible via SFTP using the same location, username and password as before, so let's use
that:

.. tabs::

   .. code-tab:: bash

      # step 1: upload input file(s)
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
      upload /home/tutorial/xenon/sleep.sh /home/xenon/sleep.sh

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/UploadFileLocalToSftpAbsolutePaths.java

Now that the script is in place, we can submit a ``bash`` job using ``xenon scheduler slurm submit`` like before, taking
the newly uploaded ``sleep.sh`` file as input to ``bash``, and using a sleep duration of 60 seconds:

.. code-block:: bash

      # step 2: submit job
      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat \
      submit --stdout sleep.stdout.txt bash sleep.sh 60

      # (should return an identifier for the job)

With the job running, let's see if it shows up in any of the SLURM queues:

.. tabs::

    .. code-tab:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list
      # should have the job identifier in it that was printed on the command line

    .. include:: java/nl/esciencecenter/xenon/examples/schedulers/SlurmJobListGetter.java

When we submitted, we did not specify any queues, so the default queue ``mypartition`` was used:

.. code-block:: bash

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list --queue mypartition
      # should have the job identifier in it that was printed on the command line

      xenon scheduler slurm --location localhost:10022 --username xenon --password javagat list --queue otherpartition
      # this queue is empty

With step 1 (upload) and step 2 (submit) covered, step 3 (download) remains:

.. tabs::

   .. code-tab:: bash

      # step 3: download generated output file(s)
      xenon filesystem sftp --location localhost:10022 --username xenon --password javagat \
      download /home/xenon/sleep.stdout.txt /home/tutorial/xenon/sleep.stdout.txt

   .. include:: java/nl/esciencecenter/xenon/examples/filesystems/DownloadFileSftpToLocalAbsolutePaths.java

By this time you may start to consider putting those 3 commands in a script, as follows:

.. tabs::

   .. include:: bash/alltogethernowwrong.sh

   .. include:: java/nl/esciencecenter/xenon/examples/AllTogetherNowWrong.java

However, if you create the script above and run it, you'll find that:

1. Xenon complains about some destination paths already existing.
2. The script finishes suspiciously quickly;

The first error is easily avoided by adding a ``--replace`` optional argument after ``upload`` and ``download``, but
that does not address the real issue: that of Xenon not waiting for the completion of our sleep job.

Not to worry though, we can use ``xenon scheduler slurm wait`` to wait for jobs to finish. In order to make this work,
we do need to capture the identifier for a specific job, otherwise we don't know what to wait for.

Adapt the script as follows and run it:

.. tabs::

   .. include:: bash/alltogethernow.sh

   .. include:: java/nl/esciencecenter/xenon/examples/AllTogetherNow.java

After about 60 seconds, you should have a local copy of ``sleep.stdout.txt``, with the correct contents this time.

Congratulations -- you have successfully completed the tutorial!

|
|
|

What's next?
------------

If you want, you can continue reading about relevant subjects, or try some of the suggested exercises.

Further reading
^^^^^^^^^^^^^^^

- Xenon's JavaDoc
- `pyXenon`__: The Python interface to Xenon

__ https://github.com/nlesc/pyxenon

Suggested exercises
^^^^^^^^^^^^^^^^^^^

- Repeat selected exercises, but test against a physically remote system instead of a Docker container. Requires
  credentials for the remote system.
- Repeat selected exercises using `WebDAV`__ instead of SFTP. We included the Docker container `nlesc/xenon-webdav`__
  as part of the virtual machine for testing.
- Update ``xenon-cli`` to the `latest version`__, so you can use the ``s3`` file adaptor to connect to Amazon's
  `Simple Storage Service`__. Requires downloading of the Docker container `nlesc/xenon-s3`__ (300MB) from DockerHub for
  testing on your own machine, or an Amazon Web Services account for testing against a physically remote system.

__ https://en.wikipedia.org/wiki/WebDAV
__ https://hub.docker.com/r/nlesc/xenon-webdav/
__ https://github.com/NLeSC/xenon-cli
__ https://aws.amazon.com/s3
__ https://hub.docker.com/r/nlesc/xenon-s3/



