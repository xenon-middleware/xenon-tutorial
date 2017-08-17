These are the install instructions for configuring the Virtual Machine as used for the [Xenon](http://nlesc.github.io/Xenon/) tutorial at
[Research Software Engineers Conference 2017](http://rse.ac.uk/conf2017/). For the tutorial document, go to http://xenonrse2017.readthedocs.io.

# Installation

Use the RSE2017 base virtual machine from [GoogleDrive](https://drive.google.com/file/d/0B1GaxSkd5lU8MTFxN3JLaHlXT2s/view) as a base.
Configure it with at least 2 CPUs during import of the ``*.ova`` file. Log in to the virtual machine (password is ``tutorial``) and proceed as follows:

```bash
cd xenon
git clone https://github.com/NLeSC/xenon-rse2017-tutorial.git .
./install.sh
# Enter sudo password when asked
```

After which 

* the [Xenon cli](https://github.com/NLeSC/xenon-cli) has been installed as `~/xenon/xenon-cli/bin/xenon` and added to the PATH environment variable
* Docker has been installed with SSH, SFTP, WebDAV and SLURM enabled images.

# Test

If the install was run in the same shell, run the following command to update the user's group and refresh the PATH environment variable:

```bash
newgrp docker
```

To test if Xenon CLI works, we first need to start the provided Docker containers. 

## SFTP

The SFTP Docker container can be started with:

```bash
docker run --detach --publish 3322:22 nlesc/xenon-ssh
# wait until the container is up and healthy by running
docker ps
```

To test if Xenon-CLI works and can access the container use: 

```bash
xenon filesystem sftp --location localhost:3322 --username xenon --password javagat list /home/xenon
# should return:
# filesystem-test-fixture
```


## WebDAV

The WebDAV Docker container can be started with:

```bash
docker run --detach --publish 2280:80 nlesc/xenon-webdav
# wait until the container is up and healthy by running
docker ps
```

The WebDAV server can be tested with:

```bash
xenon filesystem webdav --location http://localhost:2280 --username xenon --password javagat list /~xenon
# should return:
# .ssh
# filesystem-test-fixture
# uploads
```

## SLURM

The SLURM Docker container can be started with:

```bash
docker run --detach --publish 2222:22 nlesc/xenon-slurm:17
# wait until the container is up and healthy by running
docker ps
```

To test if the Xenon CLI can access SLURM, run:

```bash
xenon scheduler slurm --location localhost:2222 --username xenon --password javagat exec sinfo
# should return:
# PARTITION      AVAIL  TIMELIMIT  NODES  STATE NODELIST
# mypartition*      up   infinite      1  alloc node-0
# mypartition*      up   infinite      4   idle node-[1-4]
# otherpartition    up   infinite      1  alloc node-0
# otherpartition    up   infinite      2   idle node-[1-2]
```
