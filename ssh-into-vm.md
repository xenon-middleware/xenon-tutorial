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

