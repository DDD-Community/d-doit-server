if [ "$DEPLOYMENT_GROUP_NAME" == "d-doit-deploy-prod" ]
then
  DEPLOYMENT_NAME = "운영"
  DEPLOYMENT_PORT = 8080
  SERVICE_NAME = "d-doit-prod"
fi
if [ "$DEPLOYMENT_GROUP_NAME" == "d-doit-deploy-dev" ]
then
  DEPLOYMENT_NAME = "개발"
  DEPLOYMENT_PORT = 8080
  SERVICE_NAME = "d-doit"
fi

CURRENT_PID=$(lsof -i :$DEPLOYMENT_PORT | grep "LISTEN" | awk '{print $2}')

if [ -z $CURRENT_PID ]
then
  echo "> [종료될 프로세스가 없습니다.]"
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  echo "[프로세스 종료]"
  sleep 5
fi

echo "##############"
echo "> 배포 시작"
echo "##############"

systemctl start $SERVICE_NAME.service
