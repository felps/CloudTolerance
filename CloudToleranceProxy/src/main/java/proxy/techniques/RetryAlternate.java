package proxy.techniques;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class RetryAlternate implements FaultToleranceTechnique {

	private List<WsInvoker> invokerList;
	private WsInvoker currentWS;

	public List<WsInvoker> getInvokerList() {
		return invokerList;
	}

	public void setInvokerList(List<WsInvoker> invokerList) {
		this.invokerList = invokerList;
	}

	public WsInvoker getCurrentWS() {
		return currentWS;
	}

	public void setCurrentWS(WsInvoker currentWS) {
		this.currentWS = currentWS;
	}

	private long timeout;

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public RetryAlternate() {
		invokerList = new ArrayList<WsInvoker>();
	}

	public void addAvailableInvoker(WsInvoker availableInvoker) {
		invokerList.add(availableInvoker);

		if (currentWS == null)
			currentWS = invokerList.get(0);
	}

	public void addAvailableInvokers(List<WsInvoker> availableInvokers) {
		invokerList.addAll(availableInvokers);

		if (currentWS == null && (availableInvokers.size() > 0))
			currentWS = availableInvokers.get(0);
	}

	public void removeInvoker(WsInvoker unavailableInvoker) {
		if (invokerList.contains(unavailableInvoker)) {
			invokerList.remove(unavailableInvoker);
		}
	}

	public Object invokeMethod(String wsMethodName, Object... wsParameterValues) {
		Object result;
		if (timeout > 0)
			currentWS.setTimeout(timeout);
		for (WsInvoker invoker : invokerList) {
			currentWS = invoker;
			result = singleTry(wsMethodName, wsParameterValues);
			if (result != null)
				return result;
			else
				System.out.println("Got null as an answer. Trying next resource");
		}
		return null;
	}

	private Object singleTry(String wsMethodName, Object[] wsParameterValues) {
		WsInvokation invokation;
		try {
			if (wsParameterValues != null && wsParameterValues.length > 0)
				System.out.println("RETRY: currentWS.invokeWebMethod(" + wsMethodName
						+ ", ("+wsParameterValues[0].getClass().getName()+")"+ wsParameterValues[0].toString() + ");");
			invokation = currentWS.invokeWebMethod(wsMethodName,
					wsParameterValues);

			return invokation.getResultSetter().getResultValue();
		} catch (TimeoutException e) {
			System.out.println("Timeout");
		}
		return null;
	}

}
