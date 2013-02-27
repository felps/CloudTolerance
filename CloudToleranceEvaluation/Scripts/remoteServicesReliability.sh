#! /bin/bash

serviceProvider=godzilla.ime.usp.br
serviceProviderPort=3016
serviceProviderUser=felipe
basePort=22000

	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:22100/Linear 1.0 0.0 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:22100/Linear 1.0 0.0 0 LinearService.jar &
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:23100/Linear 1.0 0.0 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:23100/Linear 1.0 0.0 0 LinearService.jar &
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:24100/Linear 1.0 0.0 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:24100/Linear 1.0 0.0 0 LinearService.jar &

for ((reliability=99; reliability>=90;reliability=reliability-1))
do

	port=$((basePort + reliability))

	echo
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:$port/Linear 0.$reliability 0.0 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:$port/Linear 0.$reliability 0.0 0 LinearService.jar &
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:$((port+1000))/Linear 0.$reliability 0.0 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:$((port+1000))/Linear 0.$reliability 0.0 0 LinearService.jar &
	echo "ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:$((port+2000))/Linear 0.$reliability 0.0 0 LinearService.jar &"
	ssh -p $serviceProviderPort $serviceProviderUser@$serviceProvider ./restartServiceAutomatically.sh http://$serviceProvider:$((port+2000))/Linear 0.$reliability 0.0 0 LinearService.jar &

	sleep 5
done
