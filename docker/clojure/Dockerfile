FROM fp_basicimage:latest

COPY ./lein /root/bin/lein
ENV PATH $PATH:/root/bin
RUN chmod 755 /root/bin/lein
RUN lein
