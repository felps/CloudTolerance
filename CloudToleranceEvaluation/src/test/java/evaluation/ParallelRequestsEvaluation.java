package evaluation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class ParallelRequestsEvaluation {

	private static final String CHOR_4_PROXIES = "http://127.0.0.1:2440/choreography?wsdl";

	private static Logger log;
	private static ThreadPoolExecutor poolManager;
	private static BlockingQueue<Runnable> workQueue;

	private class SingleInvokation implements Runnable {

		public Logger log;
		private WsInvoker invoker;
		public int id;

		private int singleLoggedInvokation(WsInvoker invoker)
				throws TimeoutException {
			long startTime = System.currentTimeMillis();
			int result = singleInvokation(invoker);
			log.info("Invokation ID " + id + '\n' +
					"Returned Value: " + result + '\n' + 
					"Time it took: " + (System.currentTimeMillis() - startTime));
			return result;
		}

		private int singleInvokation(WsInvoker invoker) throws TimeoutException {
			WsInvokation returnedValue = invoker.invokeWebMethod(
					"startChoreograph", 0);
			int result = (Integer) returnedValue.getResultSetter()
					.getResultValue();
			return result;
		}

		public void run() {
			try {
				singleLoggedInvokation(invoker);
			} catch (TimeoutException e) {
				log.error("Invokation ID "+ id + " Timedout");
			}

		}

	}

	@BeforeClass
	public static void setUp() {
		log = Logger.getLogger(ParallelRequestsEvaluation.class);
		log.info("Initiating Multiple Proxies Evaluation");
		TimeUnit unit = TimeUnit.MINUTES;
		workQueue = new ArrayBlockingQueue<Runnable>(100);
		poolManager = new ThreadPoolExecutor(100, 1000, 10, unit, workQueue);

	}


	 @Test
	public void evaluateMultipleClients() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_4_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		log.info("Starting evaluation with 1 parallel requests");
		int invokeAmount = 1;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 10 parallel requests");
		invokeAmount = 10;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 20 parallel requests");
		invokeAmount = 20;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 30 parallel requests");
		invokeAmount = 30;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 40 parallel requests");
		invokeAmount = 40;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 50 parallel requests");
		invokeAmount = 50;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 60 parallel requests");
		invokeAmount = 60;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 70 parallel requests");
		invokeAmount = 70;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 80 parallel requests");
		invokeAmount = 80;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 90 parallel requests");
		invokeAmount = 90;
		multipleInvokations(invoker, invokeAmount);
		log.info("Starting evaluation with 100 parallel requests");
		invokeAmount = 100;
		multipleInvokations(invoker, invokeAmount);
		
	}

	private int singleLoggedInvokation(WsInvoker invoker)
			throws TimeoutException {
		long startTime = System.currentTimeMillis();
		int result = singleInvokation(invoker);
		log.info("Time it took: " + (System.currentTimeMillis() - startTime));
		return result;
	}

	private int singleInvokation(WsInvoker invoker) throws TimeoutException {
		WsInvokation returnedValue = invoker.invokeWebMethod(
				"startChoreograph", 0);
		int result = (Integer) returnedValue.getResultSetter().getResultValue();
		return result;
	}

	private int warmUpInvokation(WsInvoker invoker) {
		int result = 0;
		try {
			result = singleInvokation(invoker);
		} catch (TimeoutException e) {
			try {
				log.warn("Don't panic! In this first warm up execution it is normal to have a timeout."
						+ '\n'
						+ "We'll wait another couple of minutes just to be sure. Grab a cookie.");
				Thread.sleep(120000);
			} catch (InterruptedException e1) {
			}
		}
		return result;
	}

	private void multipleInvokations(WsInvoker invoker, int invokeAmount)
			throws TimeoutException {
		for (int i = 0; i < invokeAmount; i++) {
			SingleInvokation invoke = new SingleInvokation();
			poolManager.execute(invoke);
		}
	}
}
