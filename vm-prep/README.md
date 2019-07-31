# Installation


1. Use VirtualBox for virtualization. 
    - Windows: https://download.virtualbox.org/virtualbox/6.0.10/VirtualBox-6.0.10-132072-Win.exe
    - OS X: https://download.virtualbox.org/virtualbox/6.0.10/VirtualBox-6.0.10-132072-OSX.dmg
    - Linux: https://www.virtualbox.org/wiki/Linux_Downloads
1. Create a virtual machine in VirtualBox using Ubuntu 18.04.02 as a base image. Get the .iso here http://releases.ubuntu.com/18.04/
1. Configure the VM with at least 2 CPUs.
1. Call the user ``alice``
1. Set her password to ``password``
1. Once the virtual machine has started, update its packages

    ```
    sudo apt update
    sudo apt upgrade
    ```

1. Install Git
    
    ```
    sudo apt install git
    ```

1. Install Java version 11
    
    ```
    sudo apt install openjdk-11-amd64
    ```

1. Get docker (instructions from here https://docs.docker.com/install/linux/docker-ce/ubuntu/#install-docker-ce)

    ```
    # Install packages to allow apt to use a repository over HTTPS:
    sudo apt-get install \
        apt-transport-https \
        ca-certificates \
        curl \
        gnupg-agent \
        software-properties-common

    # Add Docker’s official GPG key:
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

    # Confirm you have the correct key ending in 0EBFCD88
    sudo apt-key fingerprint 0EBFCD88
    pub   rsa4096 2017-02-22 [SCEA]
          9DC8 5822 9FC7 DD38 854A  E2D8 8D81 803C 0EBF CD88
    uid           [ unknown] Docker Release (CE deb) <docker@docker.com>
    sub   rsa4096 2017-02-22 [S]

    # add the correct repository depending on architecture and lniux distribution:
    sudo add-apt-repository \
       "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
       $(lsb_release -cs) \
       stable"

    # update package index
    sudo apt-get update

    # install docker packages
    sudo apt-get install docker-ce docker-ce-cli containerd.io
    ```

1. FIXME get a copy of the tutorial repo
1. FIXME get a copy of xenon cli, install it, add to PATH
1. create filesystem fixtures
1. install editors: nano, geany, gedit, others
1. fix bash history
1. download docker images
    
    ```
    sudo docker pull nlesc/xenon-ssh
    sudo docker pull nlesc/xenon-slurm:17
    ```
1. generate the sphinx documentation locally just in case
1. export vm as ova
1. test the installation

----

This here is @jmaassen's setup to avoid having to deal with intricacies related to an OS that is not your own. With this setup, you can ssh into the VM, then make edits there from the comfort of the terminal that you are used to, with the tooling that you are used to.

1. Start the RSE2017 Fedora virtual machine from VirtualBox, log in as user ``tutorial`` using password ``tutorial``
1. In the VM, open a terminal and type ``ifconfig``. Look for an entry that has an ``inet`` value starting with ``10.`` (mine is ``10.0.2.15``). We will use this value as Guest IP later.
1. In the terminal, start the SSH server with ``sudo systemctl start sshd.service``
1. In VirtualBox, change the port forwarding settings, as follows:
    1. Go to menu item ``Machine``
    1. Go to ``Settings`` 
    1. Go to ``Network``
    1. On tab ``Adaptor 1``, go to ``Advanced``, click on ``Port Forwarding``
    1. Click the ``plus`` icon to add a rule
        1. Under ``protocol`` fill in ``TCP``
        1. Under ``Host IP`` fill in ``127.0.0.1`` 
        1. Under ``Host Port`` fill in ``2222`` 
        1. Under ``Guest IP`` fill in the value we got from ``ifconfig`` 
        1. Under ``Guest Port`` fill in ``22`` 
1. Outside the VM, open a terminal, type ``ssh -p 2222 tutorial@127.0.0.1`` (type password ``tutorial`` when asked). You should now be logged in to the RSE2017 VM.


----

Not sure we still need any of this (from the rse17 tutorial)

```

preparations for the rse vm
 - virtualbox guest additions
 - number of cpus

import ovafile
set number of cpus to 2
# enable 3D hardware acceleration
# set virtual memory to max, 128 MB
start fedora

in terminal
sudo dnf update kernel*
reboot
sudo dnf install gcc kernel-devel kernel-headers dkms make bzip2 perl
KERN_DIR=/usr/src/kernels/`uname -r`
export KERN_DIR
from top virtualbox dropdown menu, select devices, insert guest additions
let guest additions install from the autorun, shutdown afterwards
# disable 3d acceleration again
login

```


