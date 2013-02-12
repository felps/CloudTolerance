package proxy.techniques;

import java.util.ArrayList;
import java.util.List;

import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class Active implements FaultToleranceTechnique {

	private List<WsInvoker> webServicePool;
	private List<WsInvokation> pendingInvokations;
	
	
	public Active() {
		webServicePool = new ArrayList<WsInvoker>();
	}
	
	public boolean setServicePoolSize() {
		return false;
	}

	public void addAvailableInvoker(WsInvoker availableInvoker) {
	}

	public void addAvailableInvokers(List<WsInvoker> availableInvokers) {
	}

	public void removeInvoker(WsInvoker unavailableInvoker) {
	}

	public Object invokeMethod(String wsMethodName, Object... wsParameterValues) {
		return null;
	}
}
