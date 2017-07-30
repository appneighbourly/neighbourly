#!/bin/bash

mkdir ~/.aws

curl "https://bootstrap.pypa.io/get-pip.py" -o "get-pip.py"
python get-pip.py;
pip install awscli --ignore-installed six;

cat <<EOT >> ~/.aws/credentials
[neighbourly]
aws_access_key_id=$AWS_ACCESS_KEY_ID
aws_secret_access_key=$AWS_SECRET_ACCESS_KEY
EOT

cat <<EOT >> ~/.aws/config
[neighbourly]
region=$AWS_ERC_REGION
EOT

login_string=`aws ecr get-login --no-include-email --region us-west-2 --profile neighbourly`
eval $login_string

ecr_host_name="282950125507.dkr.ecr.us-west-2.amazonaws.com/neighbourly/service"
datetag=$(date +"%Y%m%d-%H%M")

docker_latest_tag="$ecr_host_name:latest"
docker_timestamp_tag="$ecr_host_name:$datetag"

docker build -t $docker_timestamp_tag -t $docker_latest_tag .
echo $docker_timestamp_tag
echo $docker_latest_tag

docker push $docker_timestamp_tag
docker push $docker_latest_tag