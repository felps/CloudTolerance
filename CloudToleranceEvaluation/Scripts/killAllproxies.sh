#! /bin/bash

source locations.sh
#! /bin/bash

source locations.sh

echo "Killing proxies at $server1..."
ssh $server1 pkill -ef Endpoint 

echo "Killing proxies at $server2..."
ssh $server2 pkill -ef Endpoint 

echo "Killing proxies at $server3..."
ssh $server3 pkill -ef Endpoint 

echo "Killing proxies at $server4..."
ssh $server4 pkill -ef Endpoint 

echo "Killing proxies at $server5..."
ssh $server5 pkill -ef Endpoint 

echo "Killing proxies at $server6..."
ssh $server6 pkill -ef Endpoint 

echo "Killing proxies at $server7..."
ssh $server7 pkill -ef Endpoint 

echo "Killing proxies at $server8..."
ssh $server8 pkill -ef Endpoint 

echo "Killing proxies at $server9..."
ssh $server9 pkill -ef Endpoint 

echo "Killing proxies at $server0..."
ssh $server0 pkill -ef Endpoint 
