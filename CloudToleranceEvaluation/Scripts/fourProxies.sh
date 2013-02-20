#!/bin/bash

source locations.sh

# 4 proxies
# 2440 --> 2441
ssh ubuntu@$server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:2440/choreography http://$server2:2441/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2441 --> 2442
ssh ubuntu@$server2 java -jar ProxyEndpoint.jar http://0.0.0.0:2441/proxy http://$server3:2442/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2442 --> 2443
ssh ubuntu@$server3 java -jar ProxyEndpoint.jar http://0.0.0.0:2442/proxy http://$server4:2443/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

#2443 --> 2440
ssh ubuntu@$server4 java -jar ProxyEndpoint.jar http://0.0.0.0:2443/proxy http://$server1:2440/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

