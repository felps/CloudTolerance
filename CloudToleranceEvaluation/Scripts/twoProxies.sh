#!/bin/bash

source locations.sh

# 2 proxies
# 2420 --> 2421
ssh ubuntu@$server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:2420/choreography http://$server2:2421/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2421 --> 2420
ssh ubuntu@$server2 java -jar ProxyEndpoint.jar http://0.0.0.0:2421/proxy http://$server1:2420/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

