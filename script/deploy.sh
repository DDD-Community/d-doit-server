DEPLOYMENT_NAME="개발"
DEPLOYMENT_PORT=8080
SERVICE_NAME="d-doit"
DEPLOY_FOLDER="dev"

if [ "$DEPLOYMENT_GROUP_NAME" == "d-doit-deploy-prod" ]
then
  DEPLOYMENT_NAME="운영"
  DEPLOYMENT_PORT=8080
  SERVICE_NAME="d-doit-prod"
  DEPLOY_FOLDER="prod"
fi

cd /home/ec2-user/app
cp *.jar ./$DEPLOY_FOLDER/
rm -rf *.jar *.yml ./script

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
echo "> $DEPLOYMENT_NAME 배포 시작"
echo "##############"

systemctl start $SERVICE_NAME.service
