package proxy.techniques;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.webservice.handlers.WsInvoker;
import webservices.LinearService1;

public class RetryCreditCardFailSafeTest {

	private Endpoint ep;

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokationThreadNoParametersTest");
		System.out.println("----------------------");
		System.out.println("----------------------");
	}

	@Before
	public void seTup() {
		LinearService1 ws = new LinearService1(0.3, 0);

		ep = Endpoint.create(ws);
		ep.publish("http://0.0.0.0:2401/Linear");

		waitAWhile();

	}

	@After
	public void tearDown() {
		ep.stop();
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

		WsInvoker service = new WsInvoker("http://127.0.0.1:2401/Linear?wsdl");

		retry.addAvailableInvoker(service);
		retry.setRetryAmount(10);
		retry.setTimeout(10000);

		Object result = retry.invokeMethod("three");

		if (result == null)
			fail("Got null as an answer");
		assertEquals((Integer) 3, (Integer) result);

	}

	private void waitAWhile() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
