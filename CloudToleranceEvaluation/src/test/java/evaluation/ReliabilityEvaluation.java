package evaluation;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.ChoreographyEndpoint;
import proxy.Proxy;
import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class ReliabilityEvaluation {

	private static final String WS_100_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21100/choreography?wsdl";
	private static final String WS_99_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21099/choreography?wsdl";
	private static final String WS_98_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21098/choreography?wsdl";
	private static final String WS_97_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21097/choreography?wsdl";
	private static final String WS_96_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21096/choreography?wsdl";
	private static final String WS_95_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21095/choreography?wsdl";
	private static final String WS_94_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21094/choreography?wsdl";
	private static final String WS_93_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21093/choreography?wsdl";
	private static final String WS_92_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21092/choreography?wsdl";
	private static final String WS_91_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21091/choreography?wsdl";
	private static final String WS_90_FAILSTOP_NO_FT = "http://godzilla.ime.usp.br:21090/choreography?wsdl";

	private static final String WS_100_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22100/choreography?wsdl";
	private static final String WS_99_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22099/choreography?wsdl";
	private static final String WS_98_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22098/choreography?wsdl";
	private static final String WS_97_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22097/choreography?wsdl";
	private static final String WS_96_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22096/choreography?wsdl";
	private static final String WS_95_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22095/choreography?wsdl";
	private static final String WS_94_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22094/choreography?wsdl";
	private static final String WS_93_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22093/choreography?wsdl";
	private static final String WS_92_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22092/choreography?wsdl";
	private static final String WS_91_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22091/choreography?wsdl";
	private static final String WS_90_FAILSTOP_WITH_FT= "http://godzilla.ime.usp.br:22090/choreography?wsdl";
	
	private static final String WS_100_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23100/choreography?wsdl";
	private static final String WS_99_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23099/choreography?wsdl";
	private static final String WS_98_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23098/choreography?wsdl";
	private static final String WS_97_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23097/choreography?wsdl";
	private static final String WS_96_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23096/choreography?wsdl";
	private static final String WS_95_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23095/choreography?wsdl";
	private static final String WS_94_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23094/choreography?wsdl";
	private static final String WS_93_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23093/choreography?wsdl";
	private static final String WS_92_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23092/choreography?wsdl";
	private static final String WS_91_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23091/choreography?wsdl";
	private static final String WS_90_FAULTY_RESPONSE_NO_FT= "http://godzilla.ime.usp.br:23090/choreography?wsdl";

	private static final String WS_100_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24100/choreography?wsdl";
	private static final String WS_99_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24099/choreography?wsdl";
	private static final String WS_98_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24098/choreography?wsdl";
	private static final String WS_97_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24097/choreography?wsdl";
	private static final String WS_96_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24096/choreography?wsdl";
	private static final String WS_95_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24095/choreography?wsdl";
	private static final String WS_94_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24094/choreography?wsdl";
	private static final String WS_93_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24093/choreography?wsdl";
	private static final String WS_92_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24092/choreography?wsdl";
	private static final String WS_91_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24091/choreography?wsdl";
	private static final String WS_90_FAULTY_RESPONSE_WITH_FT= "http://godzilla.ime.usp.br:24090/choreography?wsdl";


	private static Logger log;

	private class SingleInvokation implements Runnable {

		public Logger log;
		private WsInvoker invoker;
		public int id;

		private int singleLoggedInvokation(WsInvoker invoker) throws Exception {
			long startTime = System.currentTimeMillis();
			int result;
			try {
				result = singleInvokation(invoker);
				log.info(id + ", " + result + ", "
						+ (System.currentTimeMillis() - startTime));
				return result;
			} catch (Exception e) {
				log.info(id + ", error, "
						+ (System.currentTimeMillis() - startTime));
				throw new Exception("Invokation ID " + id + '\n'
						+ "Returned Value: ERROR" + '\n' + "Time it took: "
						+ (System.currentTimeMillis() - startTime));
			}
		}

		private int singleInvokation(WsInvoker invoker) throws Exception {
			WsInvokation returnedValue = invoker.invokeWebMethod(
					"startChoreograph", 0);
			if (returnedValue != null) {
				int result = (Integer) returnedValue.getResultSetter()
						.getResultValue();
				return result;
			} else
				throw new Exception("Did not get a reply");
		}

		public void run() {
			try {
				singleLoggedInvokation(invoker);
			} catch (Exception e) {
			}

		}

	} //end private class
	
	@BeforeClass
	public static void setUp() {
		log = Logger.getLogger(ReliabilityEvaluation.class);
		log.info("Initiating RELIABILITY Evaluation");
	}

	
//	@Test
	public void evaluateMultipleFailStopInvokationsWithoutFT()
			throws TimeoutException {

		HashMap<Integer, WsInvoker> invokers = new HashMap<Integer, WsInvoker>();
		// ArrayList<String> services = new ArrayList<String>();
		// 
		// services.add("http://godzilla.ime.usp.br:22090/Linear?wsdl");
		// String wsdl = "http://godzilla.ime.usp.br:21090/choreography?wsdl";
		// setUp(wsdl, wsdl, services);// */

		invokers.put(100, new WsInvoker(WS_100_FAILSTOP_NO_FT));
		invokers.put(99, new WsInvoker(WS_99_FAILSTOP_NO_FT));
		invokers.put(98, new WsInvoker(WS_98_FAILSTOP_NO_FT));
		invokers.put(97, new WsInvoker(WS_97_FAILSTOP_NO_FT));
		invokers.put(96, new WsInvoker(WS_96_FAILSTOP_NO_FT));
		invokers.put(95, new WsInvoker(WS_95_FAILSTOP_NO_FT));
		invokers.put(94, new WsInvoker(WS_94_FAILSTOP_NO_FT));
		invokers.put(93, new WsInvoker(WS_93_FAILSTOP_NO_FT));
		invokers.put(92, new WsInvoker(WS_92_FAILSTOP_NO_FT));
		invokers.put(91, new WsInvoker(WS_91_FAILSTOP_NO_FT));
		invokers.put(90, new WsInvoker(WS_90_FAILSTOP_NO_FT));

		// warm up invokation
		for (int key : invokers.keySet()) {
			warmUpInvokation(invokers.get(key));
		}

		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		log.info("Starting reliability evaluation. WITHOUT FT.");
		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		for (int key : invokers.keySet()) {
			log.info("Reliability set at " + key + '\n'
					+ "Starting evaluation with 1000 parallel requests");
			int invokeAmount = 1000;
			multipleInvokations(invokers.get(key), invokeAmount);
		}
	}

	@Test
	public void evaluateMultipleFailStopWithFT()
			throws TimeoutException {

		HashMap<Integer, WsInvoker> invokers = new HashMap<Integer, WsInvoker>();
//		 ArrayList<String> services = new ArrayList<String>();
//				
//		 services.add("http://godzilla.ime.usp.br:22090/Linear?wsdl");
//		 services.add("http://godzilla.ime.usp.br:23090/Linear?wsdl");
//		 services.add("http://godzilla.ime.usp.br:24090/Linear?wsdl");
//		 String wsdl = "http://godzilla.ime.usp.br:20090/choreography?wsdl";
//		 setUp(wsdl, wsdl, services);// */
//		 invokers.put(90, new WsInvoker(wsdl));

		 invokers.put(100, new WsInvoker(WS_100_FAILSTOP_WITH_FT));
		 invokers.put(99, new WsInvoker(WS_99_FAILSTOP_WITH_FT));
		 invokers.put(98, new WsInvoker(WS_98_FAILSTOP_WITH_FT));
		 invokers.put(97, new WsInvoker(WS_97_FAILSTOP_WITH_FT));
		 invokers.put(96, new WsInvoker(WS_96_FAILSTOP_WITH_FT));
		 invokers.put(95, new WsInvoker(WS_95_FAILSTOP_WITH_FT));
		 invokers.put(94, new WsInvoker(WS_94_FAILSTOP_WITH_FT));
		 invokers.put(93, new WsInvoker(WS_93_FAILSTOP_WITH_FT));
		 invokers.put(92, new WsInvoker(WS_92_FAILSTOP_WITH_FT));
		 invokers.put(91, new WsInvoker(WS_91_FAILSTOP_WITH_FT));
//		 invokers.put(90, new WsInvoker(WS_90_FAILSTOP_WITH_FT));

		// warm up invokation
		for (int key : invokers.keySet()) {
			warmUpInvokation(invokers.get(key));
		}

		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		log.info("  Starting FAIL STOP reliability evaluation. WITH FT.");
		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		for (int key : invokers.keySet()) {
			log.info("Reliability set at " + key + '\n'
					+ "Starting evaluation with 1000 parallel requests");
			int invokeAmount = 1000;
			multipleInvokations(invokers.get(key), invokeAmount);
		}
	}

//	@Test
	public void evaluateMultipleFaultyResponseInvokationsWithoutFT()
			throws TimeoutException {

		HashMap<Integer, WsInvoker> invokers = new HashMap<Integer, WsInvoker>();

		invokers.put(100, new WsInvoker(WS_100_FAULTY_RESPONSE_NO_FT));
		invokers.put(99, new WsInvoker(WS_99_FAULTY_RESPONSE_NO_FT));
		invokers.put(98, new WsInvoker(WS_98_FAULTY_RESPONSE_NO_FT));
		invokers.put(97, new WsInvoker(WS_97_FAULTY_RESPONSE_NO_FT));
		invokers.put(96, new WsInvoker(WS_96_FAULTY_RESPONSE_NO_FT));
		invokers.put(95, new WsInvoker(WS_95_FAULTY_RESPONSE_NO_FT));
		invokers.put(94, new WsInvoker(WS_94_FAULTY_RESPONSE_NO_FT));
		invokers.put(93, new WsInvoker(WS_93_FAULTY_RESPONSE_NO_FT));
		invokers.put(92, new WsInvoker(WS_92_FAULTY_RESPONSE_NO_FT));
		invokers.put(91, new WsInvoker(WS_91_FAULTY_RESPONSE_NO_FT));
		invokers.put(90, new WsInvoker(WS_90_FAULTY_RESPONSE_NO_FT));

		// warm up invokation
		for (int key : invokers.keySet()) {
			warmUpInvokation(invokers.get(key));
		}

		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		log.info("Starting reliability evaluation. WITHOUT FT.");
		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		for (int key : invokers.keySet()) {
			log.info("Reliability set at " + key + '\n'
					+ "Starting evaluation with 1000 parallel requests");
			int invokeAmount = 1000;
			multipleInvokations(invokers.get(key), invokeAmount);
		}
	}

//	@Test
	public void evaluateMultipleFaultyResponseWithFT()
			throws TimeoutException {

		HashMap<Integer, WsInvoker> invokers = new HashMap<Integer, WsInvoker>();
//		 ArrayList<String> services = new ArrayList<String>();
//				
//		 services.add("http://godzilla.ime.usp.br:25090/Linear?wsdl");
//		 services.add("http://godzilla.ime.usp.br:26090/Linear?wsdl");
//		 services.add("http://godzilla.ime.usp.br:27090/Linear?wsdl");
//		 String wsdl = "http://godzilla.ime.usp.br:20090/choreography?wsdl";
//		 setUp(wsdl, wsdl, services,false);// */
//		 invokers.put(90, new WsInvoker(wsdl));

		 invokers.put(100, new WsInvoker(WS_100_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(99, new WsInvoker(WS_99_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(98, new WsInvoker(WS_98_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(97, new WsInvoker(WS_97_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(96, new WsInvoker(WS_96_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(95, new WsInvoker(WS_95_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(94, new WsInvoker(WS_94_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(93, new WsInvoker(WS_93_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(92, new WsInvoker(WS_92_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(91, new WsInvoker(WS_91_FAULTY_RESPONSE_WITH_FT));
		 invokers.put(90, new WsInvoker(WS_90_FAULTY_RESPONSE_WITH_FT));

		// warm up invokation
		for (int key : invokers.keySet()) {
			warmUpInvokation(invokers.get(key));
		}

		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		log.info("  Starting FAIL STOP reliability evaluation. WITH FT.");
		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		for (int key : invokers.keySet()) {
			log.info("Reliability set at " + key + '\n'
					+ "Starting evaluation with 1000 parallel requests");
			int invokeAmount = 1000;
			multipleInvokations(invokers.get(key), invokeAmount);
		}
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
			invoke.invoker = invoker;
			invoke.log = log;
			invoke.id = i;
			invoke.run();
			sleepQuietly();
		}
	}

	private void sleepQuietly() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int singleInvokation(WsInvoker invoker) throws TimeoutException {
		try {
			WsInvokation returnedValue = invoker.invokeWebMethod(
					"startChoreograph", 0);
			int result = (Integer) returnedValue.getResultSetter()
					.getResultValue();
			return result;
		} catch (Exception e) {
			log.info("Exception gotten!");
		}
		return 0;
	}
	

	static void setUp(String wsdl, String endpoint, List<String> webServices) {
		setUp(wsdl, endpoint, webServices, true);
	}

	static void setUp(String wsdl, String endpoint, List<String> webServices, boolean reliable) {
		ChoreographyEndpoint chor = new ChoreographyEndpoint();

		chor.setNextProxyUrl(wsdl);
		chor.setWsMethodName("addOne");

		chor.setMyProxy(new Proxy());

		for (String currentEndpoint : webServices) {
			System.out.println("adding " + currentEndpoint);
			chor.getMyProxy().addWebService(currentEndpoint);

		}

		chor.getMyProxy().setUnreliableServices();
		
		System.out.println("Publishing Proxy at " + endpoint);
		Endpoint.publish(endpoint, chor);

		System.out.println("Done! Ready to warm up!");
	}

	static void setUp(String wsdl, int retryAmount, String endpoint,
			String webService) {
		ChoreographyEndpoint chor = new ChoreographyEndpoint();

		chor.setNextProxyUrl(wsdl);
		chor.setWsMethodName("addOne");

		chor.setMyProxy(new Proxy());

		System.out.println("adding " + webService);
		chor.getMyProxy().addWebService(webService);

		System.out.println("Publishing Proxy at " + endpoint);
		Endpoint.publish(endpoint, chor);

		System.out.println("Done! Ready to warm up!");
	}
}
