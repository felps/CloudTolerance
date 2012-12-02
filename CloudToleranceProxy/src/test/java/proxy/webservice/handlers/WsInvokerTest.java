package proxy.webservice.handlers;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.utils.StartTestWebServices;

public class WsInvokerTest {

	private String creditWsdlURL = "http://127.0.0.1:2302/creditcard?wsdl";
	private String weatherWsdlURL = "http://127.0.0.1:2402/weather?wsdl";
	private WsInvoker weatherInvoker;
	private WsInvoker creditCardInvoker;

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokerTest");
		System.out.println("----------------------");
		System.out.println("----------------------");

		StartTestWebServices.raiseCreditAndWeatherServices();
		Thread.sleep(5000);

		System.out.println("Done creating Web Service proxies");
	}

	@Test(timeout = 5000)
	public void testInvokeWebMethodWithoutParameters() {
		creditCardInvoker = new WsInvoker(creditWsdlURL);

		WsInvokation invokation = creditCardInvoker
				.invokeWebMethod("issuePayment");

		assertTrue((Boolean) invokation.getResultSetter().getResultValue());
	}

	@Test(timeout = 5000)
	public void testInvokeWebMethodWithParametersPreviouslySet() {
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
	public void testInvokeWebMethodWithParametersSetOnCall() {
		weatherInvoker = new WsInvoker(weatherWsdlURL);

		WsInvokation invokation = weatherInvoker.invokeWebMethod(
				"getTemperatureForecast", "BSB");

		int integerValue = (Integer) invokation.getResultSetter()
				.getResultValue();

		assertEquals(25, integerValue);
	}

}
