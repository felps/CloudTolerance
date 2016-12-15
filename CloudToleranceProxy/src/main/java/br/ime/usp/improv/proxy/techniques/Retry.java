package br.ime.usp.improv.proxy.techniques;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import br.ime.usp.improv.proxy.webservice.handlers.WsInvokation;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvoker;

public class Retry implements FaultToleranceTechnique, Serializable {

	private List<WsInvoker> invokerList;
	private WsInvoker currentWS;
	private int retryAmount = 3;

	public Retry(int amount){
		retryAmount = amount;
		invokerList = new ArrayList<WsInvoker>();
	}

	public Retry() {
		invokerList = new ArrayList<WsInvoker>();
	}

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

	public int getRetryAmount() {
		return retryAmount;
	}

	public void setRetryAmount(int retryAmount) {
		this.retryAmount = retryAmount;
	}

	private long timeout;

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
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
		for (int i = 0; i < retryAmount; i++) {
			result = singleTry(wsMethodName, wsParameterValues);
			if (result != null){
				System.out.println("Success! Method " + wsMethodName + " returned value: " + result);
				return result;
			} else
				System.out.println("Got null as an answer from " + currentWS.getWsdlURL());
		}
		return null;
	}

	private Object singleTry(String wsMethodName, Object[] wsParameterValues) {
		WsInvokation invokation;
		try {
//			if (wsParameterValues != null && wsParameterValues.length > 0)
//				System.out.println("RETRY: currentWS.invokeWebMethod(" + wsMethodName
//						+ ", ("+wsParameterValues[0].getClass().getName()+")"+ wsParameterValues[0].toString() + ") at " +currentWS.getWsdlURL());
			invokation = currentWS.invokeWebMethod(wsMethodName,
					wsParameterValues);

			return invokation.getResultSetter().getResultValue();
		} catch (TimeoutException e) {
			System.out.println("Timeout");
		}
		return null;
	}

}
