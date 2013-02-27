package proxy.techniques;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;

import proxy.utils.Result;
import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class Active implements FaultToleranceTechnique {

	private List<WsInvoker> webServicePool;
	Logger log;
	private long timeout;

	public Active(Logger log) {
		webServicePool = new ArrayList<WsInvoker>();
		this.log = log; 
	}

	public Active() {
		webServicePool = new ArrayList<WsInvoker>();
	}

	public boolean setServicePoolSize() {
		return false;
	}

	public void addAvailableInvoker(WsInvoker availableInvoker) {
		webServicePool.add(availableInvoker);
	}

	public void addAvailableInvokers(List<WsInvoker> availableInvokers) {
		webServicePool.addAll(availableInvokers);
	}

	public void removeInvoker(WsInvoker unavailableInvoker) {
		if (webServicePool.contains(unavailableInvoker))
			webServicePool.remove(unavailableInvoker);
	}

	public Object invokeMethod(String wsMethodName, Object... wsParameterValues) {
		Object result;
		Result resultSetter;

		if (timeout > 0)
			resultSetter = new Result(timeout);
		else
			resultSetter = new Result();
		
		for (WsInvoker invoker : webServicePool) {
			invoker.setTimeout(timeout);
			singleTry(invoker, wsMethodName, wsParameterValues, resultSetter);
		}

		try {
			return resultSetter.getResultValue();
		} catch (TimeoutException e) {
			System.out.println("ERROR: Invokation of "+wsMethodName  + " timed out.");
			e.printStackTrace();
		}

		return null;
	}

	private void singleTry(WsInvoker webService, String wsMethodName,
			Object[] wsParameterValues, Result resultSetter) {
		WsInvokation invokation;
		if (wsParameterValues != null && wsParameterValues.length > 0)
			System.out.println("currentWS.invokeWebMethod(" + wsMethodName
					+ ", (" + wsParameterValues[0].getClass().getName() + ")"
					+ wsParameterValues[0].toString() + ");");

		webService.invokeWebMethod(resultSetter, wsMethodName,
				wsParameterValues);
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
