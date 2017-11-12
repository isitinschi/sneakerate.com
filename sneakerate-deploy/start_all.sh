git clone https://github.com/isitinschi/imageserver.git

./restart_sneakerate.sh 1.10 9001
./restart_sneakerate.sh 1.10 9002

./restart_imageserver.sh 8001
./restart_imageserver.sh 8002

sudo service nginx start