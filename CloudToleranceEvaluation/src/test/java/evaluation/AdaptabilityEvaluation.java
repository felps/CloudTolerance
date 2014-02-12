package evaluation;

import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.Proxy;
import proxy.ProxyEndpoint;

public class AdaptabilityEvaluation {

	private static final String WS_98_FAILSTOP = "http://godzilla.ime.usp.br:22098/Linear?wsdl";
	private static final String WS_95_FAILSTOP = "http://godzilla.ime.usp.br:22095/Linear?wsdl";
	private static final String WS_90_FAILSTOP = "http://godzilla.ime.usp.br:22090/Linear?wsdl";

	private static Logger log;
	private static Endpoint ep;
	private static VoidProxy voidProxy;
	private static int key = 0;


	
	@BeforeClass
	public static void setUp() {
		log = Logger.getLogger(AdaptabilityEvaluation.class);
		log.info("Initiating RELIABILITY Evaluation");

		voidProxy = new VoidProxy(log);
		ep = Endpoint.publish("http://127.0.0.1:30000/void", voidProxy);
	}
	
	@AfterClass
	public static void tearDown() {
		ep.stop();
	}

	
	@Test
	public void evaluateAdaptation() throws TimeoutException, InterruptedException {

		int amount = 50;
		String currentWS, endpoint;

		ProxyEndpoint proxy = new ProxyEndpoint();

		System.out.println("Next proxy: http://127.0.0.1:30000/void");
		proxy.nextProxyUrl = "http://127.0.0.1:30000/void?wsdl";
		System.out.println("Service Method: addOne");
		proxy.wsMethodName = "addOne";

		proxy.myProxy = new Proxy();

		System.out.println("Adding WS at: " + WS_98_FAILSTOP);
		proxy.myProxy.addWebService(WS_98_FAILSTOP);
		System.out.println("Adding WS at: " + WS_95_FAILSTOP);
		proxy.myProxy.addWebService(WS_95_FAILSTOP);

		System.out.println("Done! Ready to warm up!");
		// warm up invokation
		proxy.playRole(0, key++);

		Thread.sleep(4500 + 50*amount);
		log.info("\n\n\n\nEnded Warm-up.\n\n\n\n");
		voidProxy.invoked = 0;
		
		log.info("--------------------------------------------");
		log.info("--------------------------------------------");
		log.info("Starting adaptability evaluation.");
		log.info("--------------------------------------------");
		log.info("--------------------------------------------");

		
		log.info("Starting evaluation with 1000 parallel requests and two WSs (95 + 98)");
		
		performRequests(proxy, amount);
		Thread.sleep(4500 + 50*amount);
		log.info("\n\n\n\nVoid proxy Invoked " + voidProxy.invoked + " times.\n\n\n\n");
		voidProxy.invoked = 0;
		
		log.info("Starting evaluation with 1000 parallel requests and three WSs (90 + 95 + 98)");
		
		System.out.println("adding " + WS_90_FAILSTOP);
		proxy.myProxy.addWebService(WS_90_FAILSTOP);
		
		performRequests(proxy, amount);
		Thread.sleep(4500 + 50*amount);

		log.info("\n\n\n\nVoid proxy Invoked " + voidProxy.invoked + " times.\n\n\n\n");
		voidProxy.invoked = 0;

		System.out.println("Dropping " + WS_98_FAILSTOP);
		proxy.myProxy.dropWebService(WS_98_FAILSTOP);

		log.info("Starting evaluation with 1000 parallel requests and two WSs (90 + 95)");
		performRequests(proxy, amount);
		Thread.sleep(4500 + 50*amount);

		log.info("\n\n\n\nVoid proxy Invoked " + voidProxy.invoked + " times.\n\n\n\n");
		voidProxy.invoked = 0;

		System.out.println("Dropping " + WS_95_FAILSTOP);
		proxy.myProxy.dropWebService(WS_95_FAILSTOP);

		log.info("Starting evaluation with 1000 parallel requests and a single WSs (90)");
		performRequests(proxy, amount);
		Thread.sleep(4500 + 50*amount);

		log.info("\n\n\n\nVoid proxy Invoked " + voidProxy.invoked + " times.\n\n\n\n");
		voidProxy.invoked = 0;
		
		
		

	}

	private void performRequests(ProxyEndpoint proxy, int amount) {
		for (int i = 1; i <= amount; i++) {
			proxy.playRole(0, key++);
		}
	}

}
