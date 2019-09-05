:orphan:

Troubleshooting ``SSH``
=======================


File permissions
----------------

The first thing to check is whether your system has the correct permissions on
the following files (you can check the octal representation of the file
permission with: ``stat -c %a <filename>``):

.. code-block::

   # client-side
   chmod go-w $HOME 
   chmod 700 $HOME/.ssh
   chmod 600 $HOME/.ssh/config       # (documentation varies 644, 600, 400)
   chmod 600 $HOME/.ssh/id_rsa       # (private keys, rsa and other types)
   chmod 644 $HOME/.ssh/id_rsa.pub   # (public keys, rsa and other types)
   chmod 600 $HOME/.ssh/known_hosts  # (not documented)

   # server side:
   chmod 600 <other system's home dir>/.ssh/authorized_keys

Ownership
---------

All files and directories under ``~/.ssh``, as well as ``~/.ssh`` itself, should
be owned by user $USER.

.. code-block:: bash

   chown -R $USER:$USER ~/.ssh


``ssh`` from the command line
-----------------------------

Check if you can access the remote system using OpenSSH instead of Xenon. On
Ubuntu-like systems, you can install OpenSSH with:

.. code-block:: bash

   sudo apt install openssh-client

Increase ``ssh``'s verbosity using the ``-vvvv`` option (more ``v``'s means higher
verbosity), e.g.

.. code-block:: bash

   ssh -vvv user@host
   
Another useful option is to ask ``ssh`` for a list of its configuration options
and their values with the ``-G`` option, e.g.

.. code-block:: bash

   ssh -G anyhost
   ssh -G user@some.system.com

Sometimes, a connection cannot be set up because of a configuration problem on
the server side. If you have access to the server through another way, running 

.. code-block:: bash

   sshd -T

might help track the problem down. Note that the results may be user-dependent,
for example the result may be different for ``root`` or for a user.

Configuration settings
----------------------

#. client-side, user configuration: ``/etc/ssh/ssh_config``
#. client-side, system configuration ``$HOME/.ssh/config``
#. server-side, system configuration ``/etc/ssh/sshd_config``


``known_hosts``
---------------

#. file permission
#. host name hashed or not ``hashKnownHosts``
#. removing a given host's key goes like this ``ssh-keygen -R [localhost]:10022``

Xenon with properties
---------------------

See http://xenon-middleware.github.io/xenon/versions/3.0.1/javadoc/

#. ``xenon.adaptors.schedulers.ssh.strictHostKeyChecking``
#. ``xenon.adaptors.schedulers.ssh.loadKnownHosts``
#. ``xenon.adaptors.schedulers.ssh.loadSshConfig``

Encrypted ``/home``
-------------------

Might negatively affect things https://help.ubuntu.com/community/SSH/OpenSSH/Keys

|
|
|

:doc:`back to the tutorial</tutorial>`

|
|
