#!/bin/bash

APP=$1
NUM=`docker ps -a | grep $APP | wc -l`
if [[ $NUM -gt 0 ]];then
  image=`docker ps -a | grep $APP | awk '{print $2}'`
	docker rm -f $APP;
	IMAGE_ID=`docker images | grep $image | awk '{print $3}'`
	docker rmi $IMAGE_ID
fi