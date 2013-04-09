#!/bin/bash

source locations.sh

# 3 proxies
# 2430 --> 2431
ssh $user@$server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:2430/choreography http://$server2:2431/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2431 --> 2432
ssh $user@$server2 java -jar ProxyEndpoint.jar http://0.0.0.0:2431/proxy http://$server3:2432/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2432 --> 2430
ssh $user@$server3 java -jar ProxyEndpoint.jar http://0.0.0.0:2432/proxy http://$server1:2430/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &


