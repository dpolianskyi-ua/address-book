#!/usr/bin/env bash
set -e

echo "Break Point #1"

app_guid=`cf app $1 --guid`
credentials=`cf curl /v2/apps/$app_guid/env | jq '.system_env_json.VCAP_SERVICES | .[] | .[] | select(.instance_name=="address-book-database") | .credentials'`

echo "Break Point #2"


ip_address=`echo $credentials | jq -r '.hostname'`
db_name=`echo $credentials | jq -r '.name'`
db_username=`echo $credentials | jq -r '.username'`
db_password=`echo $credentials | jq -r '.password'`

echo "Break Point #3"



echo "Opening ssh tunnel to $ip_address"
cf ssh -N -L 63306:$ip_address:3306 address-book &
cf_ssh_pid=$!

echo "Break Point #4"


echo "Waiting for tunnel"
sleep 5

echo "Break Point #5"


flyway-*/flyway -url="jdbc:mysql://127.0.0.1:63306/$db_name" -locations=filesystem:$2/databases/address-book -user=$db_username -password=$db_password migrate

echo "Break Point #6"


kill -STOP $cf_ssh_pid