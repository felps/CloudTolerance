package proxy;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;

import utils.ResultSetter;

public class WsInvokationTest {

	private static final String CREDIT_CARD_WSDL_ENDPOINT = "http://127.0.0.1:2302/creditcard?wsdl";
	private static final String CREDIT_CARD_WS_ENDPOINT = "http://127.0.0.1:2302/creditcard";
	private static final String WEATHER_WSDL_ENDPOINT = "http://127.0.0.1:2402/weather?wsdl";
	private static final String wEATHER_WS_ENDPOINT = "http://127.0.0.1:2402/weather";

	private WsInvokation creditCardHandler;

	@Test 
	public void verifyCreditCardServiceAvailability() throws IOException {
		// Create a URL for the desired page
		URL url = new URL(CREDIT_CARD_WSDL_ENDPOINT);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String str;
		String output = "";
		while ((str = in.readLine()) != null) {
			System.out.println(str);
			output += str;
		}
		assertTrue("Should be able to read a wsdl from "
				+ CREDIT_CARD_WSDL_ENDPOINT
				+ ", perhaps you forgot to raise the service?",
				output.length() != 0);
	}

	@Test
	public void verifyWeatherServiceAvailability() throws IOException {
		// Create a URL for the desired page
		URL url = new URL(WEATHER_WSDL_ENDPOINT);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String str;
		String output = "";
		while ((str = in.readLine()) != null) {
			System.out.println(str);
			output += str;
		}
		assertTrue("Should be able to read a wsdl from "
				+ WEATHER_WSDL_ENDPOINT
				+ ", perhaps you forgot to raise the service?",
				output.length() != 0);
	}

	@Test
	public void shouldInvokeManuallyIssuePaymentMethod() throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(CREDIT_CARD_WSDL_ENDPOINT, this
				.getClass().getClassLoader());

		Object creditCard = Thread.currentThread().getContextClassLoader()
				.loadClass("webservices.IssuePayment").newInstance();
		
		ResultSetter results = new ResultSetter();
		results.result = null;
		for (int i = 0; i < 10; i++) {
			Object[] returnedValue = client.invoke("issuePayment", creditCard);

			System.out.println(returnedValue[0]);
			if ((Boolean) returnedValue[0])
				System.out.println("Teste");
		}
	}

	@Test
	public void shouldInvokeIssuePaymentMethod() throws Exception {

		WsInvokation creditCard = new WsInvokation();
		ResultSetter returnedValues = new ResultSetter();

		creditCard.wsdlUrl = CREDIT_CARD_WSDL_ENDPOINT;
		creditCard.wsdlClazzName = "webservices.IssuePayment";
		creditCard.serviceMethod = "issuePayment";
		creditCard.paramMethod = null;
		creditCard.result = returnedValues;

		creditCard.prepareForInvoke();
		creditCard.run();

		assertEquals(returnedValues.result[0], true);
	}

	@Test
	public void shouldSetupWeatherStringInput() throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(WEATHER_WSDL_ENDPOINT, this.getClass()
				.getClassLoader());
		
		Object weather = Thread.currentThread().getContextClassLoader()
				.loadClass("webservices.GetTemperatureForecast").newInstance();
		
		ResultSetter results = new ResultSetter();
		results.result = null;
		
		String paramMethod = "setArg0";
		Object paramValue = "BSB";
		String paramClassName = "String";
		
		Method m = weather.getClass().getMethod(paramMethod, String.class);
		// Thread.currentThread().getContextClassLoader().loadClass(paramClassName));
		m.invoke(weather, "BSB");
		
		m = weather.getClass().getMethod("getArg0", null);
		
		Object response = m.invoke(weather, m.getParameterTypes());
		assertEquals("Should have set the input as \"BSB\".","BSB", ((String) response) );
	}
	@Test
	public void shouldInvokeManuallyGetWeatherForecastMethod() throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(WEATHER_WSDL_ENDPOINT, this.getClass()
				.getClassLoader());

		Object weather = Thread.currentThread().getContextClassLoader()
				.loadClass("webservices.GetTemperatureForecast").newInstance();

		ResultSetter results = new ResultSetter();
		String paramValue = "BSB";
		results.result = null;

		Object[] returnedResult = client.invoke("getTemperatureForecast", paramValue);

		System.out.println("Teste");
		int intResponse = (Integer) returnedResult[0];
		assertEquals("Should have returned 25 for BSB location", 25,  intResponse  );
	}
	
	@Test
	public void shouldInvokeGetTemperatureForBSB() throws Exception {

		WsInvokation weather = new WsInvokation();
		ResultSetter returnedValues = new ResultSetter();

		weather.wsdlUrl = WEATHER_WSDL_ENDPOINT;
		weather.wsdlClazzName = "webservices.GetTemperatureForecast";
		weather.serviceMethod = "getTemperatureForecast";
		weather.paramValue = "BSB";
		weather.result = returnedValues;

		weather.prepareForInvoke();
		weather.run();

		assertEquals("Should have returned 25 for BSB location", 25, returnedValues.result[0]);
	}

}
