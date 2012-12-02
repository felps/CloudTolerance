package proxy.techniques;

import java.util.List;
import java.util.concurrent.TimeoutException;

import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class Retry implements FaultToleranceTechnique {

	private WsInvoker currentWS;
	private int retryAmount;
	private long timeout;
	private List<WsInvoker> invokerList;
	
	public void addAvailableInvoker(WsInvoker availableInvoker) {
		invokerList.add(availableInvoker);
		if(invokerList.isEmpty())
			currentWS = availableInvoker;
	}

	public void addAvailableInvokers(List<WsInvoker> availableInvokers) {
		invokerList.addAll(availableInvokers);

		if(invokerList.isEmpty() && ( availableInvokers.size() > 0 ))
			currentWS = availableInvokers.get(0);
	}

	public void removeInvoker(WsInvoker unavailableInvoker) {
		if(invokerList.contains(unavailableInvoker)){
			invokerList.remove(unavailableInvoker);
		}
	}
	
	public Object invokeMethod(String wsMethodName, Object[] wsParameterValues)  {
		for(int i = 0; i < retryAmount; i++){
			try {
				WsInvokation invoke = currentWS.invokeWebMethod(wsMethodName, timeout, wsParameterValues);
				return invoke.getResultSetter().getResultValue();
			} catch (TimeoutException e) {
			}
		}
		return null;
	}



}
