FROM alpine:3.10

ADD ssh-user-config   /home/bob/.ssh/config
ADD ssh-system-config /etc/ssh/ssh_config

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
