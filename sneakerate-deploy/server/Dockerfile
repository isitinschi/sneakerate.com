FROM anapsix/alpine-java
ARG port
#ENV server_port "$port"
RUN echo Server port is $server_port

RUN mkdir /working && cd working

ADD server.jar /working/server.jar
ADD server_start_script.sh /working/server_start_script.sh

EXPOSE $port

CMD "/working/server_start_script.sh"

#docker build -t server-image .
#docker stop sneakerate-server && docker rm sneakerate-server
#docker run -p ${port}:${port} --name sneakerate-server -d server-image
