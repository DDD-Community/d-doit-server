if [ "$DEPLOYMENT_GROUP_NAME" == "d-doit-deploy-prod" ]
then
  echo "dev 폴더 appspec.yml 파일 삭제"
  rm ./dev/appspec.yml
fi

if [ "$DEPLOYMENT_GROUP_NAME" == "d-doit-deploy-dev" ]
then
  echo "prod 폴더 appspec.yml 파일 삭제"
  rm ./prod/appspec.yml
fi