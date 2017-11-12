docker build --no-cache -t nginx-image .
docker stop nginx-balancer && docker rm nginx-balancer
docker run --name nginx-balancer -p 80:80 -p 443:443 --link sneakerate-server-1:sneakerate-server-1 --link sneakerate-server-2:sneakerate-server-2 --link sneakerate-imageserver-1:sneakerate-imageserver-1 --link sneakerate-imageserver-2:sneakerate-imageserver-2 -d nginx-image --log-opt max-size=50m 
