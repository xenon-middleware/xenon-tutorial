xenon -vvvv scheduler slurm --location ssh://localhost:10022 --username xenon --password javagat --prop xenon.adaptors.schedulers.ssh.loadKnownHosts=false queues
# returns:
# Available queues: mypartition, otherpartition
# Default queue: mypartition
