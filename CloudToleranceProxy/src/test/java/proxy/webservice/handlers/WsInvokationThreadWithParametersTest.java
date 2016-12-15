package proxy.webservice.handlers;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeoutException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ime.usp.improv.proxy.utils.Result;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvokationThread;
import proxy.utils.StartTestWebServices;

public class WsInvokationThreadWithParametersTest {

	Object serviceHandler;
	Client client;
	private String wsdlUrl = StartTestWebServices.WEATHER_WSDL;
	private WsInvokationThread invokation;
	private Result resultSetter;

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokationThreadWithParametersTest");
		System.out.println("----------------------");
		System.out.println("----------------------");

		StartTestWebServices.raiseWeatherService(0,0);
		Thread.sleep(4000);
		
		System.out.println("Done raising Web Service");
	}

	@AfterClass
	public static void tearDown(){
		StartTestWebServices.killAllServices();
	}
	
	@Before
	public void setUp() throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		client = dcf.createClient(wsdlUrl, this.getClass().getClassLoader());

		resultSetter = new Result();
	}

	@Test
	public void shouldInvokeManuallyGetTemperatureForecastMethod()
			throws Exception {

		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

		Client client = dcf.createClient(wsdlUrl, this.getClass()
				.getClassLoader());

		Object[] returnedValue;
		int returnedIntegerValue;

		returnedValue = client.invoke("getTemperatureForecast", "BSB");
		returnedIntegerValue = (Integer) returnedValue[0];
		assertEquals(25, returnedIntegerValue);

		returnedValue = client.invoke("getTemperatureForecast", "POA");
		returnedIntegerValue = (Integer) returnedValue[0];
		assertEquals(30, returnedIntegerValue);

	}

	@Test
	public void testSimpleInvokation() throws Exception {
		int returnedValue;

		invokation = new WsInvokationThread(client, resultSetter,
				"getTemperatureForecast", "BSB");
		returnedValue = (Integer) invokation.invoke()[0];
		assertEquals(25, returnedValue);

		invokation = new WsInvokationThread(client, resultSetter,
				"getTemperatureForecast", "POA");
		returnedValue = (Integer) invokation.invoke()[0];
		assertEquals(30, returnedValue);

	}

	@Test(timeout = 1000)
	public void testUnthreadedRunMethod() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, TimeoutException {
		int returnedValue;

		invokation = new WsInvokationThread(client, resultSetter,
				"getTemperatureForecast", "BSB");
		invokation.run();

		returnedValue = (Integer) resultSetter.getResultValue();
		assertEquals(25, returnedValue);

		resultSetter = new Result();

		invokation = new WsInvokationThread(client, resultSetter,
				"getTemperatureForecast", "POA");
		invokation.run();

		returnedValue = (Integer) resultSetter.getResultValue();
		assertEquals(30, returnedValue);

	}
	
	@Test(timeout = 1000)
	public void testUnthreadedRunMethodWithPreviuoslySetParameterArray() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, TimeoutException {
		int returnedValue;

		Object[] parms = new Object[1];
		parms[0] = "BSB";
		
		invokation = new WsInvokationThread(client, resultSetter,
				"getTemperatureForecast", parms);
		invokation.run();

		returnedValue = (Integer) resultSetter.getResultValue();
		assertEquals(25, returnedValue);
	}

	@Test(timeout = 500)
	public void testThreadedInvokation() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, TimeoutException {
		int returnedValue;

		invokation = new WsInvokationThread(client, resultSetter,
				"getTemperatureForecast", "BSB");
		new Thread(invokation).start();

		returnedValue = (Integer) resultSetter.getResultValue();
		assertEquals(25, returnedValue);

		resultSetter = new Result();

		invokation = new WsInvokationThread(client, resultSetter,
				"getTemperatureForecast", "POA");
		new Thread(invokation).start();

		returnedValue = (Integer) resultSetter.getResultValue();
		assertEquals(30, returnedValue);

	}

}
