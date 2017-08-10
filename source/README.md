
mention there's --help on any command

add verbosity in case of problems
```bash
# let's try adding some verbosity
xenon --verbose scheduler slurm --location localhost:10022 --username xenon --password javagat exec sleep 5
xenon -v scheduler slurm --location localhost:10022 --username xenon --password javagat exec sleep 5
# very verbose
xenon -vvvv scheduler slurm --location localhost:10022 --username xenon --password javagat exec sleep 5
```

supply passwords from a file

Check if you can provide passwords from a file

.. code-block:: bash

      echo javagat > password.txt & xenon scheduler slurm --username xenon --password @password.txt \
      --location localhost:10022 exec /bin/hostname


add note on ease of changing file to sftp to webdav

