FROM golang:alpine
ARG port

RUN echo Image server port is $port

RUN mkdir /working
RUN mkdir /working/bin
COPY imageserver /working/imageserver
COPY warehouse /working/bin/warehouse
ADD start.sh /working/bin/start.sh

ADD imageserver_start_script.sh /working/imageserver_start_script.sh

EXPOSE $port

WORKDIR /working

CMD "/working/imageserver_start_script.sh"
