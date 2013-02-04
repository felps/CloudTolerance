#!/bin/sh
#
# Script to check for service

# $1: Endpoint of the service
# $2: Probability of failure resulting in service downtime
# $3: Probability of returning a wrong answer
# $4: Minimal time between failure and the restart of the service
# $5: Jar file with the service executable

if [$# -ne 5] ; then
	echo "USAGE: restartServiceAutomatically.sh <endpoint> <fail stop> <faulty response> <donwtime> <service executable jar>
	exit 1
fi

endpoint=$1
failStop=$2
faultyResponse=$3
downtime=$4
service=$5
number_of_args=$#

wsdl=$endpoint?wsdl

while [ true ]
do
	if wget $wsdl -O serviceWSDL.wsdl
	then
		echo Service is running at $endpoint
		echo Current time is:
		date
		rm serviceWSDL.wsdl	
	else
		echo Service is down. 
		echo Restarting Service at $endpoint.
		echo Current time is:
		date
		java -jar $service $failStop $faultyResponse $endpoint
	fi

	sleep $4
done

