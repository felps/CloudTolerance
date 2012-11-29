package proxy;

import java.util.ArrayList;
import java.util.List;

import techniques.FaultToleranceTechnique;
import techniques.Retry;

public class GenericProxy {

	public FaultToleranceTechnique technique;
	public String configFile;
	public String wsGroupName;
	public List<WsInvoker> invokersArchetype;
	
	public GenericProxy() {
		invokersArchetype = new ArrayList<WsInvoker>();
		technique = new Retry();
	}
	
	public boolean addWebService(String wsdlURL, String wsServiceName) {
		WsInvoker invoker = new WsInvoker();
		invoker.wsdlUrl=wsdlURL;
		invoker.wsdlClazzName = wsServiceName;
	
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
		
		technique.addAvailableInvoker(invoker);
		return invokersArchetype.add(invoker);
	}

	public void setupInvokation(WsInvoker service, String serviceName, String method,
			String packageName, String arg0) {

		service.wsdlClazzName = packageName + "." + serviceName;
		service.serviceMethod = method;
		service.paramValue = arg0;
		
	}
	
	public Object[] invokeMethod(String serviceMethod,
			String parameterValue) {
		return technique.invokeMethod(serviceMethod, parameterValue);
	}

	

}
