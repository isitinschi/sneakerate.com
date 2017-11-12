FROM nginx:alpine

ADD nginx.conf /etc/nginx/nginx.conf

RUN mkdir -p /etc
RUN mkdir -p /etc/letsencrypt
RUN mkdir -p /etc/letsencrypt/live
RUN mkdir -p /etc/nginx
RUN mkdir -p /etc/nginx/ssl
RUN mkdir -p /etc/letsencrypt/live/www.sneakerate.com
RUN mkdir -p /etc/letsencrypt/live/www.img.sneakerate.com

COPY www.sneakerate.com /etc/letsencrypt/live/www.sneakerate.com
COPY www.img.sneakerate.com /etc/letsencrypt/live/www.img.sneakerate.com
ADD dhparam.pem /etc/nginx/ssl/dhparam.pem

RUN mkdir /working && cd working
ADD nginx_start_script.sh /working/nginx_start_script.sh

EXPOSE 80 443

ENTRYPOINT "/working/nginx_start_script.sh"
#CMD ["nginx", "-g", "daemon off;"]
#CMD ["sh", "-c", "nginx -g daemon off ; /working/nginx_start_script.sh"]

#docker build --no-cache -t nginx-image .
#docker stop nginx-balancer && docker rm nginx-balancer
#docker run --name nginx-balancer -p 80:80 -p 443:443 -d nginx-image
