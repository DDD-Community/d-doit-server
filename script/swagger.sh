aws s3 sync s3://d-doit-deploy/openapi3.yaml /home/ec2-user/app/swagger/ 2> /dev/null

if [ $? = 1 ]; then
  echo "########## openapi3가 재대로 sync 되지 않았습니다 #######"
  exit;
else
  echo "##############"
  echo "> swagger 페이지 재배포 시작"
  echo "##############"
  systemctl restart nginx.service
fi


