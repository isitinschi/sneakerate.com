server1=$(cat /etc/hosts | grep sneakerate-server-1 | awk '{print $1}')
server2=$(cat /etc/hosts | grep sneakerate-server-2 | awk '{print $1}')
imageserver1=$(cat /etc/hosts | grep sneakerate-imageserver-1 | awk '{print $1}')
imageserver2=$(cat /etc/hosts | grep sneakerate-imageserver-2 | awk '{print $1}')

echo IP1 is $server1
echo IP2 is $server2
echo IP3 is $imageserver1
echo IP4 is $imageserver2

sed -i "s/sneakerate-server-1/$server1/g" /etc/nginx/nginx.conf
sed -i "s/sneakerate-server-2/$server2/g" /etc/nginx/nginx.conf
sed -i "s/sneakerate-imageserver-1/$imageserver1/g" /etc/nginx/nginx.conf
sed -i "s/sneakerate-imageserver-2/$imageserver2/g" /etc/nginx/nginx.conf

nginx -g 'daemon off;'