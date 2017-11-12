sudo cp --remove-destination -rL /etc/letsencrypt/live/www.sneakerate.com www.sneakerate.com
sudo cp --remove-destination -rL /etc/letsencrypt/live/www.img.sneakerate.com www.img.sneakerate.com
sudo cp --remove-destination /etc/nginx/ssl/dhparam.pem dhparam.pem

docker cp ./www.sneakerate.com/fullchain.pem nginx-balancer:/etc/letsencrypt/live/www.sneakerate.com/fullchain.pem
docker cp ./www.sneakerate.com/privkey.pem nginx-balancer:/etc/letsencrypt/live/www.sneakerate.com/privkey.pem
docker cp ./www.img.sneakerate.com/fullchain.pem nginx-balancer:/etc/letsencrypt/live/www.img.sneakerate.com/fullchain.pem
docker cp ./www.img.sneakerate.com/privkey.pem nginx-balancer:/etc/letsencrypt/live/www.img.sneakerate.com/privkey.pem
docker cp dhparam.pem nginx-balancer:/etc/nginx/ssl/dhparam.pem

docker cp nginx.conf nginx-balancer:/etc/nginx/nginx.conf
docker exec -it nginx-balancer nginx -s reload
