#!/bin/sh

java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:2102/choreography http://127.0.0.1:2202/proxy?wsdl 4 addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:2103/choreography http://127.0.0.1:2203/proxy?wsdl 8 addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:2104/choreography http://127.0.0.1:2204/proxy?wsdl 16 addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:2105/choreography http://127.0.0.1:2205/proxy?wsdl 32 addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:2106/choreography http://127.0.0.1:2206/proxy?wsdl 64 addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:2107/choreography http://127.0.0.1:2207/proxy?wsdl 128 addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:2108/choreography http://127.0.0.1:2208/proxy?wsdl 256 addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:2109/choreography http://127.0.0.1:2209/proxy?wsdl 512 addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

java -jar MultipleMessagesChoreographyEndpointService.jar http://0.0.0.0:2110/choreography http://127.0.0.1:2210/proxy?wsdl 1024 addOne http://godzilla.ime.usp.br:2302/Linear?wsdl &

