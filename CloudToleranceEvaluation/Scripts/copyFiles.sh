#! /bin/bash

source locations.sh

scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server1: &
scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server2: &
scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server3: &
scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server4: &
scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server5: &
scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server6: &
scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server7: &
scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server8: &
scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server9: &
scp ProxyEndpoint.jar ChoreographyEndpointService.jar MultipleMessagesChoreographyEndpointService.jar $server0: 

