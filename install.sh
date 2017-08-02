#!/usr/bin/sh

# install docker
sudo dnf config-manager \
    --add-repo \
    https://download.docker.com/linux/fedora/docker-ce.repo
sudo dnf makecache fast
sudo dnf install -y docker-ce
sudo usermod -aG docker $USER
sudo systemctl enable docker
sudo systemctl start docker

# pull slurm batch scheduler Docker container
sudo docker pull nlesc/xenon-slurm:17

# download/untar van xenon-cli
wget https://github.com/NLeSC/xenon-cli/releases/download/v1.0.3/xenon-cli-shadow-1.0.3.tar
tar -xf xenon-cli-shadow-*.tar
rm *.tar
mv xenon-cli-shadow* xenon-cli

# add xenon-cli/bin to PATH at login
echo 'export PATH=$PATH:$HOME/xenon/xenon-cli/bin' >> ~/.bashrc

# Correct permissions of unsafe id_rsa private key, so it can be used to login to Docker containers
chmod go-rw unsafe-id_rsa
