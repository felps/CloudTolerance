package proxy.techniques;

import java.util.List;

import proxy.webservice.handlers.WsInvoker;

public interface FaultToleranceTechnique {

	public void addAvailableInvoker(WsInvoker availableInvoker);
	
	public void addAvailableInvokers(List<WsInvoker> availableInvokers);
	
	public void invokeMethod(String serviceMethod, Object parameterValue, String parameterType);
	
}
