# xenon-rse2017-tutorial

Material for [Xenon](http://nlesc.github.io/Xenon/) tutorial for [Research Software Engineers Conference 2017](http://rse.ac.uk/conf2017/).

## Installation

Step to install tutorial material into the tutorial virtual machine.

```bash
# Login as tutorial user
cd xenon
git clone https://github.com/NLeSC/xenon-rse2017-tutorial.git .
./install.sh
```

After which 
* the [Xenon cli](https://github.com/NLeSC/xenon-cli) has been installed as `~/xenon/xenon-cli/bin/xenon` and added to the PATH env var
* Docker has been installed with Slurm image pulled

### Test

If the install was run in same shell run following to update the users groups and refresh the PATH env var:
```
newgrp docker
```

The Slurm batch scheduler can be started with:

```
docker run --detach --publish 2222:22 nlesc/xenon-slurm:17
# wait until the container is up and healty by running
docker ps
```

To login use `ssh -p 2222 xenon@localhost` and password `javagat`.

To test if the Xenon CLI and the Slurm Docker container work, run

```
xenon --username xenon --password javagat slurm --location localhost:2222 --prop=xenon.adaptors.slurm.ignore.version=true exec sinfo
```
