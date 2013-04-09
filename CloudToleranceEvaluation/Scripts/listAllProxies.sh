#! /bin/bash

source locations.sh

echo "Listing proxies at $server1..."
ssh $user@$server1 ps aux | grep Endpoint 
echo "Listing proxies at $server2..."
ssh $user@$server2 ps aux | grep Endpoint 
echo "Listing proxies at $server3..."
ssh $user@$server3 ps aux | grep Endpoint 
echo "Listing proxies at $server4..."
ssh $user@$server4 ps aux | grep Endpoint 
echo "Listing proxies at $server5..."
ssh $user@$server5 ps aux | grep Endpoint 
echo "Listing proxies at $server6..."
ssh $user@$server6 ps aux | grep Endpoint 
echo "Listing proxies at $server7..."
ssh $user@$server7 ps aux | grep Endpoint 
echo "Listing proxies at $server8..."
ssh $user@$server8 ps aux | grep Endpoint 
echo "Listing proxies at $server9..."
ssh $user@$server9 ps aux | grep Endpoint 
echo "Listing proxies at $server0..."
ssh $user@$server0 ps aux | grep Endpoint 
