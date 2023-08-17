#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/rfServerProject/content
cd $REPOSITORY

APP_NAME=rfserver
JAR_PATH=$(ls $REPOSITORY | grep 'SNAPSHOT.jar' | tail -n 1)

APP_LOG="$REPOSITORY/application.log"
ERROR_LOG="$REPOSITORY/error.log"
DEPLOY_LOG="$REPOSITORY/deploy.log"

TIME_NOW=$(date +%c)

JAVA_PATH=$(which java)
echo "$JAVA_PATH"

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID]
then
  echo "> 실행중이지 않음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  echo "$TIME_NOW > $CURRENT_PID 프로세스 종료"
  echo "$TIME_NOW > $CURRENT_PID 프로세스 종료" >> $DEPLOY_LOG
  sleep 5
fi

echo "$TIME_NOW > $JAR_FILE 파일 실행"
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG

nohup $JAVA_PATH -jar $JAR_PATH > $APP_LOG 2> $ERROR_LOG &

NEW_PID=$(pgrep -f $JAR_PATH)

echo "> $JAR_PATH 파일 $NEW_PID 프로세스로 배포"
echo "> $JAR_PATH 파일 $NEW_PID 프로세스로 배포" >> $DEPLOY_LOG
