# Installation


1. Use VirtualBox for virtualization.
    - Windows: https://download.virtualbox.org/virtualbox/6.0.10/VirtualBox-6.0.10-132072-Win.exe
    - OS X: https://download.virtualbox.org/virtualbox/6.0.10/VirtualBox-6.0.10-132072-OSX.dmg
    - Linux: https://www.virtualbox.org/wiki/Linux_Downloads
1. Create a virtual machine in VirtualBox using Ubuntu 18.04.03 as a base image. Get the .iso here http://releases.ubuntu.com/18.04/
1. Configure the VM with at least 2 CPUs.
1. Configure main memory to use 4 GB.
1. Configure video memory to use the maximum of 128 MB.
1. Call the user ``travis``
1. Set his password to ``password``
1. Update packages

    ```
    sudo apt update
    sudo apt upgrade
    ```

1. Enable Bash completion from history

    ```
    echo '"\e[A": history-search-backward            # arrow up' >> ~/.inputrc
    echo '"\e[B": history-search-forward             # arrow down'  >> ~/.inputrc
    echo 'set completion-ignore-case on' >> ~/.inputrc
    ```

1. Install Java version 11

    ```
    sudo apt install openjdk-11-jre
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

    # add the correct repository depending on architecture and linux distribution:
    sudo add-apt-repository \
       "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
       $(lsb_release -cs) \
       stable"

    # update package index
    sudo apt-get update

    # install docker packages
    sudo apt-get install docker-ce docker-ce-cli containerd.io
    ```

1. Configure the current user to allow running docker without asking for password

    ```
    # docker install should have created the docker group already, but just in case:
    sudo groupadd docker

    # add current user to docker group
    sudo usermod -aG docker $USER

    # start using the new settings (no sudo)
    # Note: I actually had to reboot to make this work.
    newgrp docker
    ```

1. Install Git

    ```
    sudo apt install git
    ```

1. Get a copy of the tutorial materials:

    ```
    git clone https://github.com/xenon-middleware/xenon-tutorial.git
    ```

1. Install dependencies for generating the Sphinx documentation

    ```
    sudo apt install python3-pip
    cd ~/xenon-tutorial/readthedocs/
    pip3 install --user -r requirements.txt
    ```

1. Add the user-space Python packages to the PATH

    ```
    echo '' >> ~/.bashrc
    echo '# add directory where python has user space packages, e.g. when installing' >> ~/.bashrc
    echo '# with pip3 install --user <package name>' >> ~/.bashrc
    echo 'PATH=$PATH:~/.local/bin' >> ~/.bashrc

    # enable the new settings
    source ~/.bashrc
    ```

1. Generate the Sphinx documentation

    ```
    cd ~/xenon-tutorial/readthedocs/
    sphinx-build -b html . build/
    ```

1. Get a copy of xenon cli, install it, add to PATH:

    ```
    cd ~/Downloads
    wget https://github.com/xenon-middleware/xenon-cli/releases/download/v3.0.1/xenon-cli-shadow-3.0.1.tar
    tar -xvf xenon-cli-shadow-3.0.1.tar
    mkdir -p ~/.local/bin/xenon
    mv xenon-cli-shadow-3.0.1 ~/.local/bin/xenon/

    echo '' >> ~/.bashrc
    echo '# add xenon-cli directory to PATH' >> ~/.bashrc
    echo 'PATH=$PATH:~/.local/bin/xenon/xenon-cli-shadow-3.0.1/bin' >> ~/.bashrc

    # enable the new settings
    source ~/.bashrc

    ```

1. Check that the downloaded version of xenon-cli uses the same xenon as what is defined in ``readthedocs/code-tabs/java/build.gradle``.
1. Check that the downloaded version of xenon-cli is the same as what is defined in ``.travis.yml``.
1. Create filesystem fixtures

    ```
    mkdir -p /home/travis/fixtures/dir1
    mkdir -p /home/travis/fixtures/.dir2
    echo 'dir1/file1.txt' > /home/travis/fixtures/dir1/file1.txt
    echo 'dir1/.file2.txt' > /home/travis/fixtures/dir1/.file2.txt
    echo '.dir2/file3.txt' > /home/travis/fixtures/.dir2/file3.txt
    echo '.dir2/.file4.txt' > /home/travis/fixtures/.dir2/.file4.txt
    ```

1. Install ``tree``

    ```
    sudo apt install tree
    ```

1. Install simple editors: nano, others

    ```
    sudo apt install geany
    sudo apt install leafpad
    sudo apt install joe

    # choose which editor should be default (nano)
    sudo update-alternatives --config editor

    ```

1. Install Eclipse

    ```
    # (from https://download.eclipse.org/eclipse/downloads/drops4/R-4.12-201906051800/)
    # download platform runtime binary
    cd ~/Downloads
    wget http://ftp.snt.utwente.nl/pub/software/eclipse//eclipse/downloads/drops4/R-4.12-201906051800/eclipse-platform-4.12-linux-gtk-x86_64.tar.gz
    tar -xzvf eclipse-platform-4.12-linux-gtk-x86_64.tar.gz

    mkdir -p ~/opt/eclipse/
    mv eclipse ~/opt/eclipse/eclipse-4.12
    ```

    ```
    mkdir -p $HOME/.local/share/applications/

    echo "[Desktop Entry]
    Type=Application
    GenericName=Text Editor
    Encoding=UTF-8
    Name=Eclipse
    Comment=Java IDE
    Exec=$HOME/opt/eclipse/eclipse-4.12/eclipse
    Icon=$HOME/opt/eclipse/eclipse-4.12/icon.xpm
    Terminal=false
    Categories=GNOME;GTK;Utility;TextEditor;Development;
    MimeType=text/plain" > $HOME/.local/share/applications/eclipse.desktop

    echo "
    # Add directory containing eclipse to the PATH
    PATH=\$PATH:$HOME/opt/eclipse/eclipse-4.12
    " >> $HOME/.profile
    ```

    Configure Eclipse by installing _Help_ >> _Install New Software..._ >> (select _--All Available Sites--_) >> _Programming Languages_ >> _Eclipse Java Development Tools_ (see https://medium.com/@acisternino/a-minimal-eclipse-java-ide-d9ba75491418).

    Prepare Java snippets directory as an Eclipse Java project:

    ```
    cd $HOME/xenon-tutorial/readthedocs/code-tabs/java
    ./gradlew eclipse
    ```

    In Eclipse, _Import project_.

1. Download Docker images

    ```
    docker pull xenonmiddleware/s3
    docker pull xenonmiddleware/slurm:17
    docker pull xenonmiddleware/ssh
    docker pull xenonmiddleware/webdav
    ```
1. Generate the Sphinx documentation locally just in case there are network problems
1. Configure OpenSSH

    ```
    sudo apt install openssh-client
    chmod go-w /home/travis
    chmod 700 /home/travis/.ssh
    cp vm-prep/.ssh/known_hosts /home/travis/.ssh/known_hosts
    chmod 644 /home/travis/.ssh/known_hosts
    cp vm-prep/.ssh/config /home/travis/.ssh/config
    chmod 600 /home/travis/.ssh/config
    chown -R travis:travis /home/.ssh
    ```

1. Install pyxenon from git

    ```
    pip install -r readthedocs/code-tabs/python/requirements.txt
    ```

    Also include the test framework

    ```
    pip install -r readthedocs/code-tabs/python/requirements-dev.txt
    ```

    Check that it works by ``which xenon-grpc``

1. Export vm as ova
1. Test the installation

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


