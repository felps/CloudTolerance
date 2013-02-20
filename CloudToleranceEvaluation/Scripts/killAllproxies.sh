#! /bin/bash

source locations.sh
#! /bin/bash

source locations.sh

echo "Killing proxies at $server1..."
ssh ubuntu@$server1 pkill -ef Endpoint 

echo "Killing proxies at $server2..."
ssh ubuntu@$server2 pkill -ef Endpoint 

echo "Killing proxies at $server3..."
ssh ubuntu@$server3 pkill -ef Endpoint 

echo "Killing proxies at $server4..."
ssh ubuntu@$server4 pkill -ef Endpoint 

echo "Killing proxies at $server5..."
ssh ubuntu@$server5 pkill -ef Endpoint 

echo "Killing proxies at $server6..."
ssh ubuntu@$server6 pkill -ef Endpoint 

echo "Killing proxies at $server7..."
ssh ubuntu@$server7 pkill -ef Endpoint 

echo "Killing proxies at $server8..."
ssh ubuntu@$server8 pkill -ef Endpoint 

echo "Killing proxies at $server9..."
ssh ubuntu@$server9 pkill -ef Endpoint 

echo "Killing proxies at $server0..."
ssh ubuntu@$server0 pkill -ef Endpoint 
