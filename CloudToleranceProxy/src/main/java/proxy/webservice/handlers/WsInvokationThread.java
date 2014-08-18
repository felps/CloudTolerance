package proxy.webservice.handlers;

import org.apache.cxf.endpoint.Client;

import proxy.utils.Result;

public class WsInvokationThread implements Runnable {

	public String methodName;
	public Object[] methodParameters;
	public Thread executionThread;
	private Client client;
	private Result resultSetter;

	public WsInvokationThread(Client invokerClient, Result resultSetter,
			String methodName, Object... methodParameter) {
		this.client = invokerClient;
		this.methodName = methodName;
		this.methodParameters = methodParameter;
		this.resultSetter = resultSetter;
	}

	public Object[] invoke() throws Exception {
		Object[] response = null;

//			System.out.println("Invoking...");
			response = client.invoke(methodName, methodParameters);
//			System.out.println("Done!");
		
		return response;
	}

	public void run() {
		Object[] response = null;
		try {
			response = invoke();
		} catch (Exception e) {
			resultSetter.setResultValue(null);
			throw new RuntimeException("Error invoking method "+ methodName);
		}
		synchronized (resultSetter) {
			if (response != null)
				resultSetter.setResultValue(response[0]);
			else
				resultSetter.setResultValue(null);
		}
	}
}
