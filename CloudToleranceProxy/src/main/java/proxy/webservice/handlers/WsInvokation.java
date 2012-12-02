package proxy.webservice.handlers;

import org.apache.cxf.endpoint.Client;

import proxy.utils.Result;

public class WsInvokation {

	private String methodName;
	private Object[] methodParameters;
	private String wsEndpoint;
	private Client client;
	private WsInvokationThread executionThread;
	private Result resultSetter;

	public Result getResultSetter() {
		return resultSetter;
	}

	public WsInvokation(String wsdlURL, Client webServiceDynamicClient, String methodName, Object... args) {
		this.wsEndpoint = wsdlURL;
		this.methodName = methodName;
		this.methodParameters = args;
		this.client = webServiceDynamicClient;
		resultSetter = new Result();
	}

	public String getMethodName() {
		return methodName;
	}

	public Object[] getMethodParameters() {
		return methodParameters;
	}

	public String getWsEndpoint() {
		return wsEndpoint;
	}

	public WsInvokationThread getExecutingThread() {
		return executionThread;
	}
	
	public void perform(){
		WsInvokationThread invokation = new WsInvokationThread(client, resultSetter, methodName, methodParameters);
		invokation.executionThread = new Thread(invokation);
		invokation.executionThread.start(); 
	}
}
