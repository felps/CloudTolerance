package proxy.techniques;

import java.util.List;
import proxy.*;
import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class Active implements FaultToleranceTechnique {

	private List<WsInvoker> webServicePool;
	private List<WsInvokation> pendingInvokations;
	
	
	public Active(List<WsInvoker> proxyInvokerList) {
		webServicePool.addAll(proxyInvokerList);
	}

	public void addAvailableInvoker(WsInvoker availableInvoker) {
	}

	public void addAvailableInvokers(List<WsInvoker> availableInvokers) {
	}

	public void invokeMethod(String serviceMethod, Object parameterValue,
			String parameterType) {
	}
	
	public boolean setServicePoolSize() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
