#! /bin/bash

source locations.sh

echo "Listing proxies at $server1..."
ssh $server1 ps aux | grep Endpoint 
echo "Listing proxies at $server2..."
ssh $server2 ps aux | grep Endpoint 
echo "Listing proxies at $server3..."
ssh $server3 ps aux | grep Endpoint 
echo "Listing proxies at $server4..."
ssh $server4 ps aux | grep Endpoint 
echo "Listing proxies at $server5..."
ssh $server5 ps aux | grep Endpoint 
echo "Listing proxies at $server6..."
ssh $server6 ps aux | grep Endpoint 
echo "Listing proxies at $server7..."
ssh $server7 ps aux | grep Endpoint 
echo "Listing proxies at $server8..."
ssh $server8 ps aux | grep Endpoint 
echo "Listing proxies at $server9..."
ssh $server9 ps aux | grep Endpoint 
echo "Listing proxies at $server0..."
ssh $server0 ps aux | grep Endpoint 
