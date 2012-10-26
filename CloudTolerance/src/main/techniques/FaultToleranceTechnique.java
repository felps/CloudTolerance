package techniques;

import java.util.List;

import proxy.WsInvoker;

public interface FaultToleranceTechnique {
	
	public void setAvailableInvokers(List<WsInvoker> invokersAvailable);
	
	public Object[] invokeMethod(String serviceMethod,
			String parameterValue);

}
