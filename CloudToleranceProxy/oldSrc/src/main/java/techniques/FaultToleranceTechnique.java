package techniques;

import java.util.List;

import proxy.WsInvoker;

public interface FaultToleranceTechnique {
	
	
	public void addAvailableInvoker(WsInvoker invokerAvailable);
	
	public void addAvailableInvokers(List<WsInvoker> invokersAvailable);
	
	public Object[] invokeMethod(String serviceMethod,
			String parameterValue, String parameterClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException;

}
