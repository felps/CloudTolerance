#! /bin/bash

failStopProbability=0
responseErrorProbability=0
port=2302

./restartServiceAutomatically.sh http://godzilla.ime.usp.br:$port/Linear 0.$failStopProbability 0.$responseErrorProbability 0 LinearService.jar &

port=2400

while (($failStopProbability <= 50))
do
	echo "Service at http://0.0.0.0:$port/Linear; Fail-Stop: $errorProbability"
	./restartServiceAutomatically.sh http://godzilla.ime.usp.br:$port/Linear 0.$failStopProbability 0.$responseErrorProbability 0 LinearService.jar &
	port=$(($port+5))
	failStopProbability=$(($failStopProbability+5))
	sleep 40
done


failStopProbability=0
responseErrorProbability=0
port=2500

while (($responseErrorProbability<50))
do
	echo "Service at http://0.0.0.0:$port/Linear; Incorrect Response: 0.$errorProbability"
	./restartServiceAutomatically.sh http://godzilla.ime.usp.br:$port/Linear 0.$failStopProbability 0.$responseErrorProbability 0 LinearService.jar &
	port=$(($port+5))
	responseErrorProbability=$(($responseErrorProbability+5))
	sleep 40
done
