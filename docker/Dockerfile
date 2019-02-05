FROM ubuntu:16.04

# basic
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install software-properties-common wget curl unzip vim nmon git openjdk-8-jdk -y

# Lisp
RUN apt-get install -y clisp

# Emacs
RUN add-apt-repository ppa:kelleyk/emacs
RUN apt-get update -y
RUN apt-get install -y emacs25
RUN echo "TERM=xterm-vt220" >> /root/.bashrc

# Clojure
COPY ./lein /root/bin/lein
ENV PATH $PATH:/root/bin
RUN chmod 755 /root/bin/lein
RUN lein

# Haskell
RUN apt-get install -y haskell-platform
RUN wget -qO- https://get.haskellstack.org/ | sh

ENV PATH $PATH:/root/.local/bin

# Elixir
RUN wget https://packages.erlang-solutions.com/erlang-solutions_1.0_all.deb && dpkg -i erlang-solutions_1.0_all.deb
RUN apt-get update
RUN apt-get install esl-erlang -y
RUN apt-get install elixir -y

# gradle
RUN wget https://services.gradle.org/distributions/gradle-5.0-bin.zip
RUN mkdir /opt/gradle
RUN unzip -d /opt/gradle gradle-5.0-bin.zip
RUN rm /gradle-5.0-bin.zip
ENV PATH $PATH:/opt/gradle/gradle-5.0/bin

# scala
RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
RUN apt-get install apt-transport-https -y
RUN apt-get update -y
RUN apt-get install sbt -y
