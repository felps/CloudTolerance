package proxy.webservice.handlers;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.utils.Result;

public class WsInvokationThreadWithParametersTest {

	Object serviceHandler;
	Client client;
	private String wsdlUrl = "http://127.0.0.1:2402/weather?wsdl";
	private WsInvokationThread invokation;
	private Result resultSetter;

	class RunWeatherService implements Runnable {

		public void run() {
			String[] args;
			args = new String[2];
			args[0] = "0.0"; // fail-stop probability
			args[1] = "0.0"; // faulty response probability

			realwebservices.WeatherForecast.main(args);
		}

	}

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {

		WsInvokationThreadWithParametersTest helper = new WsInvokationThreadWithParametersTest();
		RunWeatherService weatherServiceInitiliazer = helper.new RunWeatherService();
		new Thread(weatherServiceInitiliazer).start();
		Thread.sleep(4000);
		System.out.println("Done creating Web Service proxies");
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
	public void testSimpleInvokation() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
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
			IllegalAccessException, ClassNotFoundException {
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

	@Test(timeout = 500)
	public void testThreadedInvokation() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
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
