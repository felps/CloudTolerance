package proxy;

import java.util.List;

import proxy.techniques.FaultToleranceTechnique;
import proxy.webservice.handlers.WsInvoker;

public class Proxy {

	private FaultToleranceTechnique technique;
	private List<WsInvoker> invokerList;
	
	public void addWebService(String wsdlEndpoint, String wsClazzName) {
	}
	
	public Object invokeMethod(String wsMethodName, Object wsParameterValue, String wsParameterType){
		return null;
	}
}
