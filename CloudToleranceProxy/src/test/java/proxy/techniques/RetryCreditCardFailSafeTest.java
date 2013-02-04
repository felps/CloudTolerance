package proxy.techniques;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.*;

import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.utils.StartTestWebServices;
import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class RetryCreditCardFailSafeTest {

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokationThreadNoParametersTest");
		System.out.println("----------------------");
		System.out.println("----------------------");
		
		StartTestWebServices.raiseCreditAndWeatherServices(0.0, 0.0);
		Thread.sleep(5000);

		System.out.println("Done creating Web Service proxies");
}

	@AfterClass
	public static void tearDown() {
//		StartTestWebServices.killAllServices();
	}

	@Test
	public void shouldStartWithNoServicesAndNullCurrentWS() {
		Retry retry = new Retry();
		assertEquals(0, retry.getInvokerList().size());
		assertEquals(null, retry.getCurrentWS());
	}

	@Test
	public void shouldBeAbleToAddServices() {
		Retry retry = new Retry();
		assertEquals(0, retry.getInvokerList().size());
		retry.addAvailableInvoker(mock(WsInvoker.class));
		assertEquals(1, retry.getInvokerList().size());
	}

	@Test
	public void shouldAutomaticallySetCurrentWsIfListIsEmpty() {
		Retry retry = new Retry();
		assertTrue(retry.getInvokerList().isEmpty());

		WsInvoker availableInvoker = mock(WsInvoker.class);
		retry.addAvailableInvoker(availableInvoker);

		assertEquals(availableInvoker, retry.getCurrentWS());
		
	}

	@Test(timeout = 10000)
	public void shouldInvokeMethodWithNoParameters() throws TimeoutException {
		Retry retry = new Retry();

		WsInvoker service = new WsInvoker(StartTestWebServices.CREDITCARD_WSDL);

		retry.addAvailableInvoker(service);
		retry.setRetryAmount(1);
		retry.setTimeout(10000);

		Object result = retry.invokeMethod("issuePayment");

		if (result == null)
			fail("Got null as an answer");
		assertTrue((Boolean) result);

	}

}
