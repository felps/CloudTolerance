#!/bin/bash

source locations.sh

# 7 proxies
# 2470 --> 2471
ssh $user@$server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:2470/choreography http://$server2:2471/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2471 --> 2472
ssh $user@$server2 java -jar ProxyEndpoint.jar http://0.0.0.0:2471/proxy http://$server3:2472/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2472 --> 2473
ssh $user@$server3 java -jar ProxyEndpoint.jar http://0.0.0.0:2472/proxy http://$server4:2473/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2473 --> 2474
ssh $user@$server4 java -jar ProxyEndpoint.jar http://0.0.0.0:2473/proxy http://$server5:2474/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2474 --> 2475
ssh $user@$server5 java -jar ProxyEndpoint.jar http://0.0.0.0:2474/proxy http://$server6:2475/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2475 --> 2476
ssh $user@$server6 java -jar ProxyEndpoint.jar http://0.0.0.0:2475/proxy http://$server7:2476/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2476 --> 2470
ssh $user@$server7 java -jar ProxyEndpoint.jar http://0.0.0.0:2476/proxy http://$server1:2470/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &


