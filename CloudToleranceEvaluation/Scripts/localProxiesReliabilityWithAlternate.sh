#!/bin/bash

serviceProvider=godzilla.ime.usp.br

echo "Raising Fail Stop Fault Tolerant proxies"
myBasePort=22000
# 22000, 23000 and 24000 = fail stop
# 25000, 26000 and 27000 = faulty response

providerBasePort=22000 

for ((reliability=100; reliability>=90;reliability=reliability-1))
do

	myPort=$((myBasePort + reliability))
	providerPort=$((providerBasePort + reliability))

	echo "java -jar ChoreographyEndpointServiceRetryAlternate.jar http://0.0.0.0:$myPort/choreography http://127.0.0.1:$myPort/choreography?wsdl addOne http://$serviceProvider:$providerPort/Linear?wsdl http://$serviceProvider:$((providerPort+1000))/Linear?wsdl http://$serviceProvider:$((providerPort+2000))/Linear?wsdl &"
	      java -jar ChoreographyEndpointServiceRetryAlternate.jar http://0.0.0.0:$myPort/choreography http://127.0.0.1:$myPort/choreography?wsdl addOne http://$serviceProvider:$providerPort/Linear?wsdl http://$serviceProvider:$((providerPort+1000))/Linear?wsdl http://$serviceProvider:$((providerPort+2000))/Linear?wsdl &
done

