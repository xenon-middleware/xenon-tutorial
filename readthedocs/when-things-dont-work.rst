# Increase verbosity

1. Bash

    ```
    xenon -vvvv <subcommands>
    ```

1. Java from Eclipse

   Add the follwoing in Eclipse Debug Configuration, tab Arguments, VM arguments
    ```
    -Dloglevel=DEBUG
    ```

1. Java from command line

    ```
    ./gradlew run -Pmain=nl.esciencecenter.xenon.tutorial.DirectoryListing -Ploglevel=DEBUG
    ```

1. Python

   Start ``xenon-grpc`` in a terminal, using ``xenon-grpc -vvvvv``, then ``xenon.init()`` 
   will connect to that and the logging will be in the terminal.

# Troubleshooting

1. permissions:
   - ``chmod go-w $HOME`` 
   - ``chmod 700 $HOME/.ssh``
   - ``chmod 600 $HOME/.ssh/config`` (documentation varies 644, 600, 400 (xenial))
   - ``chmod 600 $HOME/.ssh/id_rsa`` (private keys)
   - ``chmod 644 $HOME/.ssh/id_rsa.pub`` (public keys)
   - ``chmod 600 $HOME/.ssh/known_hosts`` (not documented)
   - ``chmod 600 <other system's home dir>/.ssh/authorized_keys`` (server-side)
1. ownership:
   - ``chown -R $USER:$USER ~/.ssh``
1. settings in 
   - ``/etc/ssh/ssh_config``
   - ``$HOME/.ssh/config``
1. key format inside ``known_hosts`` (hashed or not, other aspects?)
1. run with ssh props, e.g. (from http://xenon-middleware.github.io/xenon/versions/3.0.1/javadoc/)
    - ``xenon.adaptors.schedulers.ssh.strictHostKeyChecking``
    - ``xenon.adaptors.schedulers.ssh.loadKnownHosts``
    - ``xenon.adaptors.schedulers.ssh.loadSshConfig``
1. ssh from the command line
1. removing a given host's key e.g. ``ssh-keygen -R [localhost]:10022``
1. encrypted home can negatively affect things https://help.ubuntu.com/community/SSH/OpenSSH/Keys
