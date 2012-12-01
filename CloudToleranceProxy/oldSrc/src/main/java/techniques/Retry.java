package techniques;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import proxy.WsInvoker;
import utils.ResultSetter;

public class Retry implements FaultToleranceTechnique {

	private WsInvoker currentWS;
	private List<WsInvoker> invokersAvailable;
	private int retryAmmount;
	private int timeout;

	public Retry() {
		invokersAvailable = new ArrayList<WsInvoker>();
		setRetryParameters();
	}

	public void addAvailableInvoker(WsInvoker invokerAvailable) {
		invokersAvailable.add(invokerAvailable);
		currentWS = invokerAvailable;
	}

	public void addAvailableInvokers(List<WsInvoker> invokers) {
		invokersAvailable.addAll(invokersAvailable);
		currentWS = invokersAvailable.get(0);
	}

	public Object[] invokeMethod(String serviceMethod, String parameterValue, String parameterClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return invokeRetryMethod(serviceMethod, parameterValue, parameterClass, timeout,
				retryAmmount);
	}

	private void setRetryParameters() {

		retryAmmount = 2;
		timeout = 5000;
	}

	private Thread createThread(WsInvoker wsInvoker, String serviceMethod,
			String parameterValue, ResultSetter results) {
		wsInvoker.serviceMethod = serviceMethod;
		wsInvoker.paramValue = parameterValue;
		wsInvoker.result = results;

		try {
			Thread worker = new Thread(wsInvoker);
			worker.start();
			return worker;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object[] invokeRetryMethod(String serviceMethod,
			String parameterValue, String parameterClass, int timeout, int retryAmmount) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		WsInvoker wsInvoker = currentWS.clone();
		Object[] returnedValue = null;

		
		for (int i = 0; i < retryAmmount; i++) {

			if (singleTry(serviceMethod, parameterValue, wsInvoker)) {

				try {
					returnedValue = wsInvoker.result.getResult();
				} catch (InterruptedException e) {
				}
			}

		}

		return returnedValue;

	}

	public boolean singleTry(String serviceMethod, String parameterValue,
			WsInvoker wsInvoker) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		ResultSetter results = new ResultSetter();
		wsInvoker.result = results;
		
		wsInvoker.prepareForInvoke();
		synchronized (wsInvoker.result) {

			Thread invokingThread = createThread(wsInvoker, serviceMethod,
					parameterValue, results);

			return wsInvoker.result.wasSet();

		}
	}
}
