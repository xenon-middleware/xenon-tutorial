
mention there's --help on any command

add verbosity in case of problems
```bash
# let's try adding some verbosity
xenon --verbose scheduler slurm --location localhost:10022 --username xenon --password javagat exec sleep 5
xenon -v scheduler slurm --location localhost:10022 --username xenon --password javagat exec sleep 5
# very verbose
xenon -vvvv scheduler slurm --location localhost:10022 --username xenon --password javagat exec sleep 5
```

add note on ease of changing file to sftp to webdav

