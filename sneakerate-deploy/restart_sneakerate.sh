#./restart_sneakerate.sh 1.10 9001

version="$1"
port="$2"

fuser -k $port/tcp

nohup java -jar jar/sneakerate-$version.jar --server.port=$port &
