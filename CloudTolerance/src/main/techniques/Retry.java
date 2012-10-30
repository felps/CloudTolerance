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
			String parameterValue) {
		wsInvoker.serviceMethod = serviceMethod;
		wsInvoker.paramValue = parameterValue;

		try {
			Thread worker = new Thread(wsInvoker);
			worker.run();
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

			Thread invokingThread = createThread(wsInvoker, serviceMethod,
					parameterValue);

			long msec = currentDate.getTime();
			synchronized (invokingThread) {
				try {
					invokingThread.wait(timeout);
				} catch (InterruptedException e) {
					if (wsInvoker.result.wasSet()) {
						System.out.println("Funfou direito!");
						return wsInvoker.result.getResult();
					} else if (currentDate.getTime() < msec + (timeout * 0.8))
						System.out.println("Unforeseen awakening");
				}

				if (wsInvoker.result.wasSet()) {
					System.out.println("Funfou mas no timeout!");
					return wsInvoker.result.getResult();
				}
			}
		}
		return null;
	}

}
