#!/bin/bash

source locations.sh

# 9 proxies
# 2490 --> 2491
ssh $server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:2490/choreography http://$server2:2491/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2491 --> 2492
ssh $server2 java -jar ProxyEndpoint.jar http://0.0.0.0:2491/proxy http://$server3:2492/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2492 --> 2493
ssh $server3 java -jar ProxyEndpoint.jar http://0.0.0.0:2492/proxy http://$server4:2493/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2493 --> 2494
ssh $server4 java -jar ProxyEndpoint.jar http://0.0.0.0:2493/proxy http://$server5:2494/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2494 --> 2495
ssh $server5 java -jar ProxyEndpoint.jar http://0.0.0.0:2494/proxy http://$server6:2495/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2495 --> 2496
ssh $server6 java -jar ProxyEndpoint.jar http://0.0.0.0:2495/proxy http://$server7:2496/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2496 --> 2497
ssh $server7 java -jar ProxyEndpoint.jar http://0.0.0.0:2496/proxy http://$server8:2497/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2497 --> 2498
ssh $server8 java -jar ProxyEndpoint.jar http://0.0.0.0:2497/proxy http://$server9:2498/proxy?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

# 2498 --> 2490
ssh $server9 java -jar ProxyEndpoint.jar http://0.0.0.0:2498/proxy http://$server1:2490/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &


