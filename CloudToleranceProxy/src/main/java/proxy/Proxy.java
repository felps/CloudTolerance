package proxy;

import java.util.ArrayList;
import java.util.List;

import proxy.techniques.FaultToleranceTechnique;
import proxy.webservice.handlers.WsInvoker;

public class Proxy {

	private FaultToleranceTechnique technique;
	private List<WsInvoker> invokerList;
	
	public Proxy() {
		invokerList = new ArrayList<WsInvoker>();
	}
	
	public void addWebService(String wsdlEndpoint) {
		WsInvoker newService = new WsInvoker(wsdlEndpoint);
		invokerList.add(newService);
		technique.addAvailableInvoker(newService);
	}
	
	public Object invokeMethod(String wsMethodName, Object... wsParameterValues){
		return technique.invokeMethod(wsMethodName, wsParameterValues);
	}
}
