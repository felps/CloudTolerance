package techniques;

import java.util.Date;
import java.util.List;
import java.util.Set;

import proxy.WsInvoker;
import utils.ResultSetter;

public class Retry implements FaultToleranceTechnique{

	private WsInvoker currentWS;
	private List<WsInvoker> invokersAvailable;
	private int retryAmmount;
	private int timeout;

	public void setAvailableInvokers(List<WsInvoker> invokers) {
		invokersAvailable = invokers;
		currentWS = invokersAvailable.get(0);
	}
	
	public Object[] invokeMethod(String serviceMethod, String parameterValue) {
		setRetryParameters();
		return invokeRetryMethod(serviceMethod,
				parameterValue, timeout, retryAmmount) ;
	}

	private void setRetryParameters() {

		retryAmmount = 10;
		timeout = 15000;
	}

	private void createThread(WsInvoker wsInvoker, String serviceMethod,
			String parameterValue) {
		// TODO Auto-generated method stub
		wsInvoker.serviceMethod = serviceMethod;
		wsInvoker.paramValue = parameterValue;

		try {
			wsInvoker.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object[] invokeRetryMethod(String serviceMethod,
			String parameterValue, int timeout, int retryAmmount) {
		Date currentDate = new Date();
		WsInvoker wsInvoker = currentWS.clone();

		ResultSetter results = new ResultSetter();
		currentWS.result = results;

		synchronized (results) {
			for (int i = 0; i < retryAmmount; i++) {
			
				createThread(wsInvoker, serviceMethod, parameterValue);

			    long msec = currentDate.getTime();
				try {
					
					this.wait(timeout);
				} catch (InterruptedException e) {
					if (results.wasSet()) {
						return results.getResult();
					} else
						if(currentDate.getTime() < msec + (timeout*0.8))
						System.out.println("Unforeseen awakening");
				}
			}
		}
		return null;
	}

}
