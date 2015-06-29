package evaluation;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class MultipleProxies {

	private static final String CHOR_2_PROXIES = "http://192.168.1.1:2420/choreography?wsdl";
	private static final String CHOR_3_PROXIES = "http://192.168.1.1:2430/choreography?wsdl";
	private static final String CHOR_4_PROXIES = "http://192.168.1.1:2440/choreography?wsdl";
	private static final String CHOR_5_PROXIES = "http://192.168.1.1:2450/choreography?wsdl";
	private static final String CHOR_6_PROXIES = "http://192.168.1.1:2460/choreography?wsdl";
	private static final String CHOR_7_PROXIES = "http://192.168.1.1:2470/choreography?wsdl";
	private static final String CHOR_8_PROXIES = "http://192.168.1.1:2480/choreography?wsdl";
	//private static final String CHOR_9_PROXIES = "http://192.168.1.1:2490/choreography?wsdl";
	//private static final String CHOR_10_PROXIES = "http://192.168.1.1:2400/choreography?wsdl";

	private static Logger log;

	@BeforeClass
	public static void setUp() {
		log = Logger.getLogger(MultipleProxies.class);
		log.info("Initiating Multiple Proxies Evaluation");
	}

	@Test
	public void evaluate2proxies() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_2_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		int invokeAmount = setAmount();
		multipleInvokations(CHOR_2_PROXIES, invokeAmount);

	}

	private int setAmount() {
		return 1000;
	}

	@Test
	public void evaluate3proxies() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_3_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		int invokeAmount = setAmount();
		multipleInvokations(CHOR_3_PROXIES, invokeAmount);
	}

	@Test
	public void evaluate4proxies() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_4_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		int invokeAmount = setAmount();
		multipleInvokations(CHOR_4_PROXIES, invokeAmount);
	}

	 @Test
	public void evaluate5proxies() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_5_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		int invokeAmount = setAmount();
		multipleInvokations(CHOR_5_PROXIES, invokeAmount);
	}

	@Test
	public void evaluate6proxies() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_6_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		int invokeAmount = setAmount();
		multipleInvokations(CHOR_6_PROXIES, invokeAmount);
	}

	@Test
	public void evaluate7proxies() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_7_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		int invokeAmount = setAmount();
		multipleInvokations(CHOR_7_PROXIES, invokeAmount);
	}

	@Test
	public void evaluate8proxies() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_8_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		int invokeAmount = setAmount();
		multipleInvokations(CHOR_8_PROXIES, invokeAmount);
	}
/*
	// @Test
	public void evaluate9proxies() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_9_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		int invokeAmount = 100;
		multipleInvokations(CHOR_9_PROXIES, invokeAmount);
	}

	// @Test
	public void evaluate10proxies() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR_10_PROXIES);

		// warm up invokation
		warmUpInvokation(invoker);

		int invokeAmount = 100;
		multipleInvokations(CHOR_10_PROXIES, invokeAmount);
	}
*/
	private int singleLoggedInvokation(WsInvoker invoker)
			throws TimeoutException {
		long startTime = System.currentTimeMillis();
		int result = singleInvokation(invoker);
		log.info("Returned value: "
				+ result + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));
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
			System.out.println(result);
		} catch (Exception e) {
				log.warn("Don't panic! In this first warm up execution it is normal to have a timeout."
						+ '\n');
		}
		return result;
	}

	private void multipleInvokations(String wsdl, int invokeAmount)
			throws TimeoutException {

		WsInvoker invoker = new WsInvoker(wsdl);;
		
		for (int i = 0; i < invokeAmount; i++) {
			System.out.println("Evaluation number " + i + "WSDL: " + wsdl + '\n');
			singleLoggedInvokation(invoker);
		}
	}
}
