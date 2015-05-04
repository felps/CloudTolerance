package proxy.techniques;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.webservice.handlers.WsInvoker;
import webservices.LinearService1;

public class WsFailureHandlingRetryTest {

	private static Endpoint	ep;

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokationThreadNoParametersTest");
		System.out.println("----------------------");
		System.out.println("----------------------");

	}

	@BeforeClass
	public static void setUp() {
		LinearService1 ws = new LinearService1(0.3, 0);
		waitAWhile();

		ep = Endpoint.create(ws);
		ep.publish("http://0.0.0.0:2401/Linear");

	}


	private static void waitAWhile() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(timeout = 10000)
	public void shouldInvokeFaultyMethodWithNoParameters() throws TimeoutException {
		Retry retry = new Retry();

		WsInvoker service = new WsInvoker("http://0.0.0.0:2401/Linear?wsdl");

		retry.addAvailableInvoker(service);
		retry.setRetryAmount(10);
		retry.setTimeout(500);

		for (int i = 0; i < 10; i++) {
			Object result = retry.invokeMethod("three");

			if (result == null)
				fail("Got null as an answer");

			int integerValue = (Integer) result;
			assertEquals(3, integerValue);
		}
	}
}
