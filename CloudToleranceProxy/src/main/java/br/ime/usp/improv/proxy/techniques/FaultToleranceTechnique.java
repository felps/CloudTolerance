package br.ime.usp.improv.proxy.techniques;

import java.util.List;

import br.ime.usp.improv.proxy.webservice.handlers.WsInvoker;

public interface FaultToleranceTechnique {

	public void addAvailableInvoker(WsInvoker availableInvoker);
	
	public void addAvailableInvokers(List<WsInvoker> availableInvokers);
	
	public void removeInvoker(WsInvoker unavailableInvoker);
	
	public long getTimeout();
	
	public void setTimeout(long timeout) ;
	
	public Object invokeMethod(String wsMethodName, Object... wsParameterValues);
	

	
}
