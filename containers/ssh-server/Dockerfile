FROM alpine:3.10

ADD sshd-system-config /etc/ssh/sshd_config

RUN apk add --no-cache openssh-server
RUN apk add --no-cache nano

# Generate the key pair for the host 
RUN ssh-keygen -N "" -t ecdsa -f /etc/ssh/ssh_host_ecdsa_key

#################################################################
# configure a user so we can connect from another container as that user

RUN /usr/sbin/adduser -s /bin/sh -D robert   && \
echo robert:robertpwd | chpasswd

# start the SSH server without detaching
CMD [ "/usr/sbin/sshd", "-D" ]

EXPOSE 22
