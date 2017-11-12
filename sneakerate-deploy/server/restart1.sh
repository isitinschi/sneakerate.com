version="$1"
port=9001

if [ "$#" -ne 1 ]; then
	echo "Please include a release version and port number eg ./restart_sneakerate.sh 1.10"
	exit
fi

cp ../jar/sneakerate-$version.jar server.jar

echo "java -jar /working/server.jar --server.port=$port" >> server_start_script.sh
chmod +x server_start_script.sh

docker build --no-cache --build-arg port=$port -t server-image .

rm server.jar
rm server_start_script.sh

docker stop sneakerate-server-1 && docker rm sneakerate-server-1
docker run -p $port:$port --name sneakerate-server-1 -d server-image --log-opt max-size=50m 
