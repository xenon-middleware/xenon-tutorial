```
# install a server
sudo apt-get install openssh-server

# generate key pair
ssh-keygen -t rsa
# (press enter a couple times to accept the defaults)

# copy identity
ssh-copy-id ${USER}@localhost

# now try to log in without entering key:
ssh ${USER}@localhost
```
