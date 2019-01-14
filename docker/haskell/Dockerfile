FROM fp_basicimage:latest

RUN apt-get install -y haskell-platform
RUN wget -qO- https://get.haskellstack.org/ | sh

ENV PATH $PATH:/root/.local/bin
