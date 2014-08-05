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

public class ResponseFailureHandlingWeatherRetryTest {

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokationThreadNoParametersTest");
		System.out.println("----------------------");
		System.out.println("----------------------");

		StartTestWebServices.raiseCreditAndWeatherServices(0.2, 0.0);
		Thread.sleep(5000);

		System.out.println("Done creating Web Service proxies");
	}

	@Test
	public void shouldInvokeFaultyMethodWithParameters()
			throws TimeoutException, InterruptedException {

		WsInvoker weatherInvoker = new WsInvoker(
				StartTestWebServices.WEATHER_WSDL);

		if(weatherInvoker == null)
			fail("Null WsInvoker");
		
		Retry retry = new Retry(15);
		retry.addAvailableInvoker(weatherInvoker);
		retry.setTimeout(1000);
		retry.setRetryAmount(1);

		for (int i = 0; i < 10; i++) {

			Object result = retry.invokeMethod("getTemperatureForecast", "BSB");

			if (result == null)
				fail("Got null as an answer for BSB");

			int numericalValue = (Integer) result;
			assertEquals(25, numericalValue);

			result = retry.invokeMethod("getTemperatureForecast", "POA");
			if (result == null)
				fail("Got null as an answer for POA");

			numericalValue = (Integer) result;
			assertEquals(30, numericalValue);

		}
	}

}
