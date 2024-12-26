#!/bin/bash

APP_NAME=batch001.jar
DIR_PATH=/home/ec2-user/batch/
TARGET_APP=$DIR_PATH$APP_NAME

PID=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')

# PID가 존재하면 프로세스 종료
if [ ! -z "$PID" ]; then
    echo "Stopping process with PID: $PID"
    kill -9 $PID

    # 3초 대기
    sleep 3

    # 프로세스 종료 확인
    if lsof -p $PID > /dev/null 2>&1; then
        echo "Process failed to terminate normally"
        exit 1
    fi
else
    echo "No existing $APP_NAME process found"
fi

# 새로운 프로세스 시작
echo "Starting new $APP_NAME process"
nohup java -Dspring.profiles.active=prd,schd -Dappender.file=true -jar $TARGET_APP > /dev/null 2>&1 &

# 3초 대기
sleep 3

# 새 프로세스 확인
NEW_PID=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')

if [ -z "$NEW_PID" ]; then
    echo "Failed to start $APP_NAME process"
    exit 1
else
    echo "Successfully started $APP_NAME process with PID: $NEW_PID"
fi