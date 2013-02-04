package proxy.techniques;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.utils.StartTestWebServices;
import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class ResponseFailureHandlingCreditCardRetryTest {

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokationThreadNoParametersTest");
		System.out.println("----------------------");
		System.out.println("----------------------");

		StartTestWebServices.raiseCreditAndWeatherServices(0.0, 0.2);
		Thread.sleep(5000);

		System.out.println("Done creating Web Service proxies");
	}

	@Test(timeout = 10000)
	public void shouldInvokeFaultyMethodWithNoParameters()
			throws TimeoutException {
		Retry retry = new Retry();

		WsInvoker service = new WsInvoker(StartTestWebServices.CREDITCARD_WSDL);

		retry.addAvailableInvoker(service);
		retry.setRetryAmount(1);
		retry.setTimeout(500);

		for (int i = 0; i < 10; i++) {
			Object result = retry.invokeMethod("issuePayment");

			if (result == null)
				fail("Got null as an answer");
			assertTrue((Boolean) result);
		}
	}
}
