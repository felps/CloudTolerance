#!/bin/sh

java -jar ChoreographyEndpointService.jar http://0.0.0.0:2100/choreography http://127.0.0.1:2201/proxy addOne http://godzilla.ime.usp.br:2301/Linear?wsdl

java -jar ProxyEndpoint.jar http://0.0.0.0:2201/proxy http://127.0.0.1:2100/choreography  addOne http://godzilla.ime.usp.br:2302/Linear?wsdl
