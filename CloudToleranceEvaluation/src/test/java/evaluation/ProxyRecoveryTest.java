package evaluation;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.ChoreographyEndpoint;
import proxy.ProxyEndpoint;
import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;
import webservices.LinearService1;

public class ProxyRecoveryTest {

	private static Logger log;
	ChoreographyEndpoint chor;
	ProxyEndpoint proxy1;
	ProxyEndpoint proxy2;

	@BeforeClass
	public static void setUpEnvironment() throws InterruptedException {
		log = Logger.getLogger(ProxyRecoveryTest.class);
		log.info("Initiating Proxy Recovery Evaluation");
		TimeUnit unit = TimeUnit.MINUTES;
		/*
		 * System.out.println("----------------------");
		 * System.out.println("----------------------");
		 * System.out.println("Beginng WsInvokationThreadNoParametersTest");
		 * System.out.println("----------------------");
		 * System.out.println("----------------------");
		 * 
		 * StartTestWebServices.raiseCreditAndWeatherServices(0.0, 0.0);
		 * Thread.sleep(5000);
		 * 
		 * System.out.println("Done creating Web Service proxies");
		 */
	}

	@Before
	public void raiseChoreography() {

		LinearService1 ws = new LinearService1(0, 0);

		Endpoint.publish("http://0.0.0.0:2401/Linear", ws);

		// # 2430 --> 2431
		// java -jar ChoreographyEndpointService.jar
		// http://0.0.0.0:2430/choreography http://127.0.0.1:2431/proxy?wsdl
		// addOne http://godzilla.ime.usp.br:25100/Linear?wsdl &
		String[] args1 = { "http://0.0.0.0:2430/choreography",
				"http://127.0.0.1:2431/proxy?wsdl", "addOne",
				"http://127.0.0.1:2401/Linear?wsdl" };
		chor = new ChoreographyEndpoint();
		chor.main(args1);

		// # 2431 --> 2432
		// java -jar ChoreographyEndpointService.jar
		// http://0.0.0.0:2431/choreography http://127.0.0.1:2432/proxy?wsdl
		// addOne http://godzilla.ime.usp.br:26100/Linear?wsdl &
		String[] args2 = { "http://0.0.0.0:2431/proxy",
				"http://127.0.0.1:2432/proxy?wsdl", "addOne",
				"http://127.0.0.1:2401/Linear?wsdl" };
		proxy1 = new ProxyEndpoint();
		proxy1.publishWS(args2);
		// # 2432 --> 2430
		// java -jar ProxyEndpoint.jar http://0.0.0.0:2432/proxy
		// http://$server1:2430/choreography?wsdl addOne
		// http://godzilla.ime.usp.br:27100/Linear?wsdl &
		String[] args3 = { "http://0.0.0.0:2432/proxy",
				"http://127.0.0.1:2430/choreography?wsdl", "addOne",
				"http://127.0.0.1:2401/Linear?wsdl" };
		proxy2 = new ProxyEndpoint();
		proxy2.publishWS(args3);

		//(new java.util.Scanner(System.in)).nextLine();
	}

	@Test
	public void shouldThereBeNoProxyAvailableAsNextCreateOneLocally()
			throws TimeoutException {
		WsInvoker testRun = new WsInvoker(
				"http://127.0.0.1:2430/choreography?wsdl");

		// warm up invokation
		warmUpInvokation(testRun);
		int result = singleInvokation(testRun);
		assertEquals("Failed to execute warm-up invokation", 3, result);

		
		// kill proxy2
		proxy2.stopService();

		warmUpInvokation(testRun);
		//result = singleInvokation(testRun);
		//assertEquals("Failed to either raise or connect new proxy", 3, result);

	}

	private int singleInvokation(WsInvoker invoker) throws TimeoutException {
		WsInvokation returnedValue = invoker.invokeWebMethod(
				"startChoreography", 0);
		int result = (Integer) returnedValue.getResultSetter().getResultValue();
		return result;
	}

	private int warmUpInvokation(WsInvoker invoker) {
		int result = 0;
		try {
			result = singleInvokation(invoker);
			System.out.println(result);
		} catch (Exception e) {
			log.warn("Don't panic! In this first warm up execution it is normal to have a timeout." + '\n');
		}
		return result;
	}
}
