package proxy;

import java.util.List;

import techniques.FaultToleranceTechnique;

public class GenericProxy {

	public FaultToleranceTechnique technique;
	public String configFile;
	public String wsGroupName;
	public List<WsInvoker> invokersArchetype;
	
	public boolean addWebService(String wsdlURL) {
		WsInvoker invoker = new WsInvoker();
		try {
			invoker.prepareForInvoke();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invokersArchetype.add(invoker);
	}

	public void setupInvokation(WsInvoker service, String serviceName, String method,
			String packageName, String arg0) {

		service.wsdlClazzName = packageName + "." + serviceName;
		service.serviceMethod = method;
		service.paramValue = arg0;
		
	}
	
	public Object[] invokeMethod(List<WsInvoker> availableInvokersSet, String serviceMethod,
			String parameterValue) {
		technique.addAvailableInvokers(availableInvokersSet);
		return technique.invokeMethod(serviceMethod, parameterValue);
	}

	

}
