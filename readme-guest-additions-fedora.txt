
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


