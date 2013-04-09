#! /bin/bash

source locations.sh
#! /bin/bash

source locations.sh

echo "Killing proxies at $server1..."
ssh $user@$server1 pkill -ef Endpoint 

echo "Killing proxies at $server2..."
ssh $user@$server2 pkill -ef Endpoint 

echo "Killing proxies at $server3..."
ssh $user@$server3 pkill -ef Endpoint 

echo "Killing proxies at $server4..."
ssh $user@$server4 pkill -ef Endpoint 

echo "Killing proxies at $server5..."
ssh $user@$server5 pkill -ef Endpoint 

echo "Killing proxies at $server6..."
ssh $user@$server6 pkill -ef Endpoint 

echo "Killing proxies at $server7..."
ssh $user@$server7 pkill -ef Endpoint 

echo "Killing proxies at $server8..."
ssh $user@$server8 pkill -ef Endpoint 

echo "Killing proxies at $server9..."
ssh $user@$server9 pkill -ef Endpoint 

echo "Killing proxies at $server0..."
ssh $user@$server0 pkill -ef Endpoint 
