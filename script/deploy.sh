CURRENT_PID=$(lsof -i :8080 | grep "LISTEN" | awk '{print $2}')

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
if [ "$DEPLOYMENT_GROUP_NAME" == "d-doit-deploy-prod" ]
then
  echo "운영 배포 시작"
  systemctl start d-doit-prod.service
fi

if [ "$DEPLOYMENT_GROUP_NAME" == "d-doit-deploy-dev" ]
then
  echo "개발 배포 시작."
  systemctl start d-doit.service
fi
