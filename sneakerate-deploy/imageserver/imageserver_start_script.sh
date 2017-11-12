#./restart_imageserver.sh 8001

export GOPATH=/working/imageserver
export GOBIN=/working/bin

go install imageserver/src/http/imageserver/imageserver.go

cd $GOBIN

./start.sh
