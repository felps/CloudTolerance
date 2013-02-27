#! /bin/bash

serviceProvider=godzilla.ime.usp.br

echo "Raising non-FT proxies"
myBasePort=21000
providerBasePort=22000

for ((reliability=100; reliability>=90;reliability=reliability-1))
do

	myPort=$((myBasePort + reliability))
	providerPort=$((providerBasePort + reliability))

echo "	java -jar ChoreographyEndpointService.jar http://0.0.0.0:$myPort/choreography http://127.0.0.1:$myPort/choreography addOne http://$serviceProvider:$providerPort/Linear?wsdl &"
	java -jar ChoreographyEndpointService.jar http://0.0.0.0.0:$myPort/choreography http://127.0.0.1:$myPort/choreography?wsdl addOne http://$serviceProvider:$providerPort/Linear?wsdl &

done
