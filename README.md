# xenon-rse2017-tutorial

Material for [Xenon](http://nlesc.github.io/Xenon/) tutorial for [Research Software Engineers Conference 2017](http://rse.ac.uk/conf2017/).

## Installation

Step to install tutorial material in to tutorial virtual machine.

```bash
# Login as tutorial user
cd xenon
git clone https://github.com/NLeSC/xenon-rse2017-tutorial.git .
./install.sh
```

After which 
* the [Xenon cli](https://github.com/NLeSC/xenon-cli) has been installed as `~/xenon/xenon-cli/bin/xenon` and added to the PATH env var.

### Test

If the install was run in same shell then relogin so user is joined the docker group and adjusts the PATH env var then run
```
newgrp docker
```

Slurm batch scheduler is running as Docker container, to login use `ssh -p 2222 xenon@localhost` and password `javagat`, it can be started with:

```
docker-compose up -d
# wait until containers are up and healty by running
docker ps
```

To test if the xenon works run

```
xenon --username xenon --password javagat slurm --location localhost:2222 --prop=xenon.adaptors.slurm.ignore.version=true submit hostname
```
