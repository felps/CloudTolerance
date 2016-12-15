package br.ime.usp.improv.proxy.webservice.handlers;

import org.apache.cxf.endpoint.Client;

import br.ime.usp.improv.proxy.utils.Result;

public class WsInvokation {

	private String methodName;
	private Object[] methodParameters;
	private String wsEndpoint;
	private Client client;
	private WsInvokationThread executionThread;
	private Result resultSetter;
	private long timeout = -1;

	public Result getResultSetter() {
		return resultSetter;
	}
	
	public long getTimeout(){
		return timeout;
	}

	public WsInvokation(String wsdlURL, Client webServiceDynamicClient,
			String methodName, Object... args) {
		this.wsEndpoint = wsdlURL;
		this.methodName = methodName;
		this.methodParameters = args;
		this.client = webServiceDynamicClient;
		resultSetter = new Result();
	}
	
	public WsInvokation(Result resultSetter, String wsdlURL, Client webServiceDynamicClient,
			String methodName, Object... args) {
		this.wsEndpoint = wsdlURL;
		this.methodName = methodName;
		this.methodParameters = args;
		this.client = webServiceDynamicClient;
		this.resultSetter = resultSetter;
	}

	public WsInvokation(String wsdlURL, Client webServiceDynamicClient,
			long timeout, String methodName, Object... args) {
		this.wsEndpoint = wsdlURL;
		this.methodName = methodName;
		this.methodParameters = args;
		this.client = webServiceDynamicClient;
		this.timeout = timeout;
		resultSetter = new Result(timeout);
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

	public void perform() throws RuntimeException {
		WsInvokationThread invokation = new WsInvokationThread(client,
				resultSetter, methodName, methodParameters);
		invokation.executionThread = new Thread(invokation);
		invokation.executionThread.setContextClassLoader(this.getClass().getClassLoader());
		try{
			System.out.println("Running thread " +  invokation.executionThread.getId() +" to execute query on invoker "  +client);
		invokation.executionThread.start();
		}
		catch (RuntimeException e){
			throw e;
		}
	}
}
