#!/bin/bash

source locations.sh

#source copyFiles.sh

source twoProxies.sh &

sleep 480

echo "Raising choreography with 3 proxies"
source threeProxies.sh &

sleep 480

echo "Raising choreography with 4 proxies"
source fourProxies.sh &

sleep 480

echo "Raising choreography with 5 proxies"
source fiveProxies.sh &

sleep 480

echo "Raising choreography with 6 proxies"
source sixProxies.sh &

sleep 480

echo "Raising choreography with 7 proxies"
source sevenProxies.sh &

sleep 480

echo "Raising choreography with 8 proxies"
source eightProxies.sh &

sleep 480

echo "Raising choreography with 9 proxies"
source nineProxies.sh &

sleep 480

echo "Raising choreography with 10 proxies"
source tenProxies.sh &

sleep 480
