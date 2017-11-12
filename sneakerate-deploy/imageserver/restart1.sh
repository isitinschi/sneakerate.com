port=8001

if [ ! -d "warehouse" ]; then
    echo "Didn't find warehouse directory!";
	exit 1;
fi

git clone https://github.com/isitinschi/imageserver.git

echo "./imageserver $port" >> start.sh
chmod +x start.sh

docker build --no-cache --build-arg port=$port -t imageserver-image .

rm start.sh
rm -rf imageserver

docker stop sneakerate-imageserver-1 && docker rm sneakerate-imageserver-1
docker run -p $port:$port --name sneakerate-imageserver-1 -d imageserver-image --log-opt max-size=50m 
