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

# install docker-compose
sudo sh -c "curl -L https://github.com/docker/compose/releases/download/1.14.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose"
sudo chmod +x /usr/local/bin/docker-compose

# pull slurm batch scheduler Docker container
sudo docker-compose pull

# download/untar van xenon-cli
wget https://github.com/NLeSC/xenon-cli/releases/download/v1.0.3/xenon-cli-shadow-1.0.3.tar
tar -xf xenon-cli-shadow-*.tar
rm *.tar
mv xenon-cli-shadow* xenon-cli

# add xenon-cli/bin to PATH at login
echo 'export PATH=$PATH:$HOME/xenon/xenon-cli/bin' >> ~/.bashrc

# Correct permissions of unsafe id_rsa private key, so it can be used to login to Docker containers
chmod go-rw unsafe-id_rsa
