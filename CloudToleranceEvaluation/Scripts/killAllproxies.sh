#! /bin/bash

source locations.sh
#! /bin/bash

source locations.sh

echo "Killing proxies at $server1..."
ssh $user@$server1 pkill -f Endpoint 

echo "Killing proxies at $server2..."
ssh $user@$server2 pkill -f Endpoint 

echo "Killing proxies at $server3..."
ssh $user@$server3 pkill -f Endpoint 

echo "Killing proxies at $server4..."
ssh $user@$server4 pkill -f Endpoint 

echo "Killing proxies at $server5..."
ssh $user@$server5 pkill -f Endpoint 

echo "Killing proxies at $server6..."
ssh $user@$server6 pkill -f Endpoint 

echo "Killing proxies at $server7..."
ssh $user@$server7 pkill -f Endpoint 

echo "Killing proxies at $server8..."
ssh $user@$server8 pkill -f Endpoint 

echo "Killing proxies at $server9..."
ssh $user@$server9 pkill -f Endpoint 

echo "Killing proxies at $server0..."
ssh $user@$server0 pkill -f Endpoint 
