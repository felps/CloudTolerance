#! /bin/bash

serviceProvider=godzilla.ime.usp.br
serviceProviderPort=3016
serviceProviderUser=felipe
basePort=22000

for ((failStopProbability=0; failStopProbability<=10;failStopProbability++))
do

	port=$((basePort + 100 -failStopProbability))

	echo
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$port/Linear 0.0$failStopProbability 0.0 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$port/Linear 0.0$failStopProbability 0.0 0 LinearService.jar &
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$((port+1000))/Linear 0.0$failStopProbability 0.0 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$((port+1000))/Linear 0.0$failStopProbability 0.0 0 LinearService.jar &
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$((port+2000))/Linear 0.0$failStopProbability 0.0 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$((port+2000))/Linear 0.0$failStopProbability 0.0 0 LinearService.jar &

	sleep 5
done

basePort=25000

for ((incorrectResponseProbability=0; incorrectResponseProbability<=10;incorrectResponseProbability++))
do

	port=$((basePort + 100 -incorrectResponseProbability))

	echo
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$port/Linear 0.0 0.0$incorrectResponseProbability 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$port/Linear 0.0 0.0$incorrectResponseProbability 0 LinearService.jar &
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$((port+1000))/Linear 0.0 0.0$incorrectResponseProbability 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$((port+1000))/Linear 0.0 0.0$incorrectResponseProbability 0 LinearService.jar &
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$((port+2000))/Linear 0.0 0.0$incorrectResponseProbability 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://0.0.0.0:$((port+2000))/Linear 0.0 0.0$incorrectResponseProbability 0 LinearService.jar &

	sleep 5
done
