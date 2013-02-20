#! /bin/bash

source locations.sh
port=2640
servicePort=2400

for failStopProbability in 0 5 10 15 20 25 30 35 40 45 50
do

	ssh $server1 java -jar ChoreographyEndpointService.jar http://0.0.0.0:$port/choreography http://$server2:$((port+1))/proxy?wsdl addOne http://godzilla.ime.usp.br:$servicePort/Linear?wsdl &
	((port++))

	ssh $server2 java -jar ProxyEndpoint.jar http://0.0.0.0:$port/proxy http://$server3:$((port+1))/proxy?wsdl addOne http://godzilla.ime.usp.br:$servicePort/Linear?wsdl &
	((port++))

	ssh $server3 java -jar ProxyEndpoint.jar http://0.0.0.0:$port/proxy http://$server4:$((port+1))/proxy?wsdl addOne http://godzilla.ime.usp.br:$servicePort/Linear?wsdl &
	((port++))

	ssh $server4 java -jar ProxyEndpoint.jar http://0.0.0.0:$port/proxy http://$server1:$((port-3))/choreography?wsdl addOne http://godzilla.ime.usp.br:$servicePort/Linear?wsdl &
	port=$((port-3+100))

	servicePort=$((servicePort+5))
	sleep 4
done