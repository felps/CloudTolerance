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

public class RetryFailSafeTest {

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
		retry.addAvailableInvoker(new WsInvoker(
				StartTestWebServices.CREDITCARD_WSDL));
		assertEquals(1, retry.getInvokerList().size());
	}

	@Test
	public void shouldAutomaticallySetCurrentWsIfListIsEmpty() {
		Retry retry = new Retry();
		assertTrue(retry.getInvokerList().isEmpty());

		WsInvoker availableInvoker = new WsInvoker(
				StartTestWebServices.CREDITCARD_WSDL);
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

//	@Test
	public void shouldInvokeMethodWithParameters() throws TimeoutException,
			InterruptedException {

		
		WsInvoker weatherInvoker = new WsInvoker(StartTestWebServices.WEATHER_WSDL);

		WsInvokation invokation = weatherInvoker.invokeWebMethod(
				"getTemperatureForecast", "BSB");

		int integerValue = (Integer) invokation.getResultSetter()
				.getResultValue();

		assertEquals(25, integerValue);
		
//		Retry retry = new Retry();
//		retry.addAvailableInvoker(weatherInvoker);
//		retry.setRetryAmount(1);
//
//
//		Object result = retry.invokeMethod("getTemperatureForecast", "BSB");
//
//		if (result == null)
//			fail("Got null as an answer for BSB");
//
//		int numericalValue = (Integer) result;
//		assertEquals(25, numericalValue);
//
//		result = retry.invokeMethod("getTemperatureForecast", "POA");
//		if (result == null)
//			fail("Got null as an answer for POA");
//
//		numericalValue = (Integer) result;
//		assertEquals(30, numericalValue);
	}

}
