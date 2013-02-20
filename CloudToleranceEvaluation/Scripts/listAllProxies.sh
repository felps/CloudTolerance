#! /bin/bash

source locations.sh

echo "Listing proxies at $server1..."
ssh ubuntu@$server1 ps aux | grep Endpoint 
echo "Listing proxies at $server2..."
ssh ubuntu@$server2 ps aux | grep Endpoint 
echo "Listing proxies at $server3..."
ssh ubuntu@$server3 ps aux | grep Endpoint 
echo "Listing proxies at $server4..."
ssh ubuntu@$server4 ps aux | grep Endpoint 
echo "Listing proxies at $server5..."
ssh ubuntu@$server5 ps aux | grep Endpoint 
echo "Listing proxies at $server6..."
ssh ubuntu@$server6 ps aux | grep Endpoint 
echo "Listing proxies at $server7..."
ssh ubuntu@$server7 ps aux | grep Endpoint 
echo "Listing proxies at $server8..."
ssh ubuntu@$server8 ps aux | grep Endpoint 
echo "Listing proxies at $server9..."
ssh ubuntu@$server9 ps aux | grep Endpoint 
echo "Listing proxies at $server0..."
ssh ubuntu@$server0 ps aux | grep Endpoint 
