package proxy.techniques;

import java.util.List;

import proxy.webservice.handlers.WsInvoker;

public class Retry implements FaultToleranceTechnique {

	private WsInvoker currentWS;
	private int retryAmount;
	private long timeout;
	private List<WsInvoker> invokerList;
	
	public void addAvailableInvoker(WsInvoker availableInvoker) {
	}

	public void addAvailableInvokers(List<WsInvoker> availableInvokers) {
	}

	public void invokeMethod(String serviceMethod, Object parameterValue,
			String parameterType) {
	}


}
