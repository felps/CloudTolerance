#!/bin/bash

source locations.sh


# 10 proxies
# 2400 --> 2401
ssh $user@$server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:2400/choreography http://$server2:2401/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2401 --> 2402
ssh $user@$server2 java -jar ProxyEndpoint.jar http://0.0.0.0:2401/proxy http://$server3:2402/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2402 --> 2403
ssh $user@$server3 java -jar ProxyEndpoint.jar http://0.0.0.0:2402/proxy http://$server4:2403/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2403 --> 2404
ssh $user@$server4 java -jar ProxyEndpoint.jar http://0.0.0.0:2403/proxy http://$server5:2404/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2404 --> 2405
ssh $user@$server5 java -jar ProxyEndpoint.jar http://0.0.0.0:2404/proxy http://$server6:2405/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2405 --> 2406
ssh $user@$server6 java -jar ProxyEndpoint.jar http://0.0.0.0:2405/proxy http://$server7:2406/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2406 --> 2407
ssh $user@$server7 java -jar ProxyEndpoint.jar http://0.0.0.0:2406/proxy http://$server8:2407/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2407 --> 2408
ssh $user@$server8 java -jar ProxyEndpoint.jar http://0.0.0.0:2407/proxy http://$server9:2408/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2408 --> 2409
ssh $user@$server9 java -jar ProxyEndpoint.jar http://0.0.0.0:2408/proxy http://$server0:2409/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2409 --> 2400
ssh $user@$server0 java -jar ProxyEndpoint.jar http://0.0.0.0:2409/proxy http://$server1:2400/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &



