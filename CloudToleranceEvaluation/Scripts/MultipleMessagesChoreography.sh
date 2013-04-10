#!/bin/bash

source locations.sh

messageAmount=4;

chorPort=2101	
while (($messageAmount < 1025)); do
	proxyPort=$(($chorPort+100))
	
	echo "Starting proxies for $messageAmount at http://$server1:$chorPort/choreography"

	ssh $user@$server1 java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:$chorPort/choreography http://$server2:$proxyPort/proxy?wsdl $messageAmount addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

	ssh $user@$server2 java -jar ProxyEndpoint.jar http://0.0.0.0:$proxyPort/proxy http://$server1:$chorPort/choreography?wsdl addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

	chorPort=$(($chorPort+1))
	messageAmount=$(($messageAmount*2))

	sleep 120
 done
