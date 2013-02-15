#!/bin/bash

source locations.sh

echo $server1

sleep 10
 
# 6 proxies
# 2460 --> 2461
ssh $server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:2460/choreography http://$server2:2461/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2461 --> 2462
ssh $server2 java -jar ProxyEndpoint.jar http://0.0.0.0:2461/proxy http://$server3:2462/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2462 --> 2463
ssh $server3 java -jar ProxyEndpoint.jar http://0.0.0.0:2462/proxy http://$server4:2463/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2463 --> 2464
ssh $server4 java -jar ProxyEndpoint.jar http://0.0.0.0:2463/proxy http://$server5:2464/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2464 --> 2465
ssh $server5 java -jar ProxyEndpoint.jar http://0.0.0.0:2464/proxy http://$server6:2465/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2465 --> 2460
ssh $server6 java -jar ProxyEndpoint.jar http://0.0.0.0:2465/proxy http://$server1:2460/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &


