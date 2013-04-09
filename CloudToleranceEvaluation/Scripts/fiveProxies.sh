#!/bin/bash


source locations.sh

# 5 proxies
# 2450 --> 2451
ssh $user@$server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:2450/choreography http://$server2:2451/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2451 --> 2452
ssh $user@$server2 java -jar ProxyEndpoint.jar http://0.0.0.0:2451/proxy http://$server3:2452/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2452 --> 2453
ssh $user@$server3 java -jar ProxyEndpoint.jar http://0.0.0.0:2452/proxy http://$server4:2453/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2453 --> 2454
ssh $user@$server4 java -jar ProxyEndpoint.jar http://0.0.0.0:2453/proxy http://$server5:2454/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2454 --> 2450
ssh $user@$server5 java -jar ProxyEndpoint.jar http://0.0.0.0:2454/proxy http://$server1:2450/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

