#./stop.sh 9001

port=$1
fuser -k $port/tcp
