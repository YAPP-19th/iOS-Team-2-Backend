REPOSITORY=/home/ubuntu/budi-app
cd $REPOSITORY

APP_NAME=TeamBuildingApplication
JAR_PATH=$(ls $REPOSITORY/build/libs/*.jar | grep -v 'plain')

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 APP_NAME이 없음" >>/home/ubuntu/budi-app/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> /home/ubuntu/budi-app/deploy.log
  kill -9 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 에 실행 권한 추가" >> /home/ubuntu/budi-app/deploy.log
chmod +x $JAR_PATH

echo "> $JAR_PATH 배포" >> /home/ubuntu/budi-app/deploy.log
nohup java -jar -Dspring.config.location=/home/ubuntu/budi-env/application-prod.yml $JAR_PATH >/home/ubuntu/budi-log/log_$(date +"%Y%m%d_%H:%M:%S").out 2>&1 &

exit 0