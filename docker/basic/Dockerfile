FROM ubuntu:16.04

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install software-properties-common wget unzip vim nmon git openjdk-8-jdk -y

RUN add-apt-repository ppa:kelleyk/emacs
RUN apt-get update -y
RUN apt-get install -y emacs25
RUN echo "TERM=xterm-vt220" >> /root/.bashrc