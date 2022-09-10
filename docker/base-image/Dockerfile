FROM openjdk:17-alpine

USER root

# copy the bootstrap script
COPY bootstrap bootstrap
# make bootstrap script executable
RUN chmod +x bootstrap

# copy ssh-daemon configuration
COPY sshd_config /etc/ssh/sshd_config

# Install OpenSSH and set the password for root to "Docker!". In this example, "apk add" is the install instruction for an Alpine Linux-based image.
RUN apk add openssh \
     && echo "root:Docker!" | chpasswd

# Open port 2222 for SSH access
# auth needs rework (user/password/sha file)
#EXPOSE 80 2222
EXPOSE 80

