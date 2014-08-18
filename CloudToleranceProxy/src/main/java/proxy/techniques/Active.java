package proxy.techniques;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import proxy.utils.Result;
import proxy.webservice.handlers.WsInvoker;

public class Active implements FaultToleranceTechnique {

	private HashSet<WsInvoker> webServicePool;
	Logger log;
	private long timeout;

	public Active(Logger log) {
		webServicePool = new HashSet<WsInvoker>();
		this.log = log;
	}

	public Active() {
		webServicePool = new HashSet<WsInvoker>();
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
		System.out.println("WebService Pool size: " + webServicePool.size());

		for (WsInvoker invoker : webServicePool) {
			invoker.setTimeout(timeout);
			System.out.println("Single try at " + invoker.toString());
			singleTry(invoker, wsMethodName, wsParameterValues, resultSetter);
		}

		try {
			return resultSetter.getResultValue();
		} catch (TimeoutException e) {
			System.out.println("ERROR: Invokation of " + wsMethodName
					+ " timed out.");
			log.info("ERROR: Invokation of " + wsMethodName + " timed out.");
			e.printStackTrace();
		}

		return null;
	}

	private void singleTry(WsInvoker webService, String wsMethodName,
			Object[] wsParameterValues, Result resultSetter) {
		if (wsParameterValues != null && wsParameterValues.length > 0)
			System.out.println("ACTIVE: currentWS.invokeWebMethod("
					+ wsMethodName + ", ("
					+ wsParameterValues[0].getClass().getName() + ")"
					+ wsParameterValues[0].toString() + ");");

		try {
			webService.invokeWebMethod(resultSetter, wsMethodName,
					wsParameterValues);
		} catch (RuntimeException e) {

		}
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
