# client side configuration, for access to a password-only server
# 
# make sure the network bridge is up, or create it with
# docker network create ssh-testing-network
# 
# docker build --tag sshclient .
# docker run -it --network ssh-testing-network --name sshclient sshclient /bin/sh
#
# inside docker container, check which settings ssh actually uses by
# ssh -G anyhostyoulike (make sure you are user: bob)

FROM alpine:3.10

ADD user-config   /home/bob/.ssh/config
ADD system-config /etc/ssh/ssh_config

RUN apk add --no-cache openssh-client
RUN apk add --no-cache nano

RUN /usr/sbin/adduser -s /bin/sh -D bob      && \
echo bob:bobpwd | chpasswd                   && \
chmod go-w /home/bob                         && \
chmod 600 /home/bob/.ssh/config              && \
chmod 700 /home/bob/.ssh                     && \
chown -R bob:bob /home/bob/.ssh

WORKDIR /home/bob
USER bob

