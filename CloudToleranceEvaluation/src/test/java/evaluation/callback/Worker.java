package evaluation.callback;

import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import br.ime.usp.improv.proxy.utils.Result;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvokation;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvoker;

public class Worker implements Runnable {

	public Callable myBoss;
	public String wsdlURL;
	public Result result;
	public Logger log;
	public String wsdl;
	public WsInvoker invoker;
	private int key;

	// Worker gets a handle to the boss object via the Callable interface.
	// There's no way this worker class can call any other method other than
	// the one in Callable.

	public void invokeService(Callable myBoss) {
		log.info("[Worker] Invoking service at " + wsdl);
		long elapsedTime = -1;
		try {
			elapsedTime = singleInvokation();
		} catch (TimeoutException e) {
			log.error("Timeout Exception: Invoking WS at " + wsdl);
		}
		myBoss.reportExecutionCompletion(elapsedTime, key);
	}

	public void run() {
		invokeService(myBoss);
	}

	private long singleInvokation() throws TimeoutException {
		long startTime = System.currentTimeMillis();
		WsInvokation returnedValue = invoker.invokeWebMethod(
				"startChoreograph", 0);
		log.info("WSDL: " + wsdl + '\n' + "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: " + (System.currentTimeMillis() - startTime));
		returnedValue.getResultSetter().getResultValue();
		return System.currentTimeMillis() - startTime;
	}

}
