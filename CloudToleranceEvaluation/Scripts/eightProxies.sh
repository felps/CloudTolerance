#!/bin/bash

source locations.sh


# 8 proxies
# 2480 --> 2481
ssh $server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:2480/choreography http://$server2:2481/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2481 --> 2482
ssh $server2 java -jar ProxyEndpoint.jar http://0.0.0.0:2481/proxy http://$server3:2482/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2482 --> 2483
ssh $server3 java -jar ProxyEndpoint.jar http://0.0.0.0:2482/proxy http://$server4:2483/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2483 --> 2484
ssh $server4 java -jar ProxyEndpoint.jar http://0.0.0.0:2483/proxy http://$server5:2484/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2484 --> 2485
ssh $server5 java -jar ProxyEndpoint.jar http://0.0.0.0:2484/proxy http://$server6:2485/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2485 --> 2486
ssh $server6 java -jar ProxyEndpoint.jar http://0.0.0.0:2485/proxy http://$server7:2486/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2486 --> 2487
ssh $server7 java -jar ProxyEndpoint.jar http://0.0.0.0:2486/proxy http://$server8:2487/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2487 --> 2480
ssh $server8 java -jar ProxyEndpoint.jar http://0.0.0.0:2487/proxy http://$server1:2480/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

