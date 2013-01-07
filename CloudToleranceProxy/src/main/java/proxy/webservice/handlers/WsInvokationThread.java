package proxy.webservice.handlers;

import java.lang.reflect.InvocationTargetException;

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

	public Object[] invoke() {
		Object[] response = null;

		try {
			System.out.println("Invoking...");
			response = client.invoke(methodName, methodParameters);
			System.out.println("Done!");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public void run() {
		Object[] response;
		response = invoke();
		synchronized (resultSetter) {
			if (response != null)
				resultSetter.setResultValue(response[0]);
			else
				resultSetter.setResultValue(null);
		}
	}
}
