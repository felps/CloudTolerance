package techniques;

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

	public Object[] invokeMethod(String serviceMethod, String parameterValue) {
		return invokeRetryMethod(serviceMethod, parameterValue, timeout,
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
			String parameterValue, int timeout, int retryAmmount) {

		Date currentDate = new Date();
		WsInvoker wsInvoker = currentWS.clone();

		ResultSetter results = new ResultSetter();
		wsInvoker.result = results;

		for (int i = 0; i < retryAmmount; i++) {

			synchronized (wsInvoker.result) {

			Thread invokingThread = createThread(wsInvoker, serviceMethod,
					parameterValue, results);

			long msec = currentDate.getTime();
			Object[] returnedObject = null;
			try {
					returnedObject = wsInvoker.result.getResult();
			} catch (InterruptedException e) {
				if (wsInvoker.result.wasSet()) {
					System.out.println("Funfou direito!");
					return returnedObject;
				}
			}

			if (wsInvoker.result.wasSet()) {
				System.out.println("Funfou mas no timeout!" + ((new Date()).getTime() - msec) );
				return returnedObject;
			}
			}
		}
		return null;
	}
}
