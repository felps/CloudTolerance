package proxy.webservice.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.utils.StartTestWebServices;

public class WsInvokerTest {

	private String creditWsdlURL = StartTestWebServices.CREDITCARD_WSDL;
	private String weatherWsdlURL = StartTestWebServices.WEATHER_WSDL;
	private WsInvoker weatherInvoker;
	private WsInvoker creditCardInvoker;

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokerTest");
		System.out.println("----------------------");
		System.out.println("----------------------");

		StartTestWebServices.raiseCreditAndWeatherServices(0.0, 0.0);
		Thread.sleep(5000);

		System.out.println("Done creating Web Service proxies");
	}

	@AfterClass
	public static void tearDown(){
		StartTestWebServices.killAllServices();
	}
	
	@Test(timeout = 5000)
	public void testInvokeWebMethodWithoutParameters() throws TimeoutException {
		creditCardInvoker = new WsInvoker(creditWsdlURL);

		WsInvokation invokation = creditCardInvoker
				.invokeWebMethod("issuePayment");

		assertTrue((Boolean) invokation.getResultSetter().getResultValue());
	}

	@Test(timeout = 5000)
	public void testInvokeWebMethodWithParametersPreviouslySet() throws TimeoutException {
		weatherInvoker = new WsInvoker(weatherWsdlURL);

		Object[] parms = new String[1];
		parms[0] = "BSB";

		WsInvokation invokation = weatherInvoker.invokeWebMethod(
				"getTemperatureForecast", parms);

		int integerValue = (Integer) invokation.getResultSetter()
				.getResultValue();

		assertEquals(25, integerValue);
	}

	@Test(timeout = 5000)
	public void testInvokeWebMethodWithParametersSetOnCall() throws TimeoutException {
		WsInvoker weatherInvoker = new WsInvoker(StartTestWebServices.WEATHER_WSDL);

		WsInvokation invokation = weatherInvoker.invokeWebMethod(
				"getTemperatureForecast", "BSB");

		int integerValue = (Integer) invokation.getResultSetter()
				.getResultValue();

		assertEquals(25, integerValue);
	}

}
