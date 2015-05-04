package proxy.webservice.handlers;

import static org.junit.Assert.*;

import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.utils.Result;
import webservices.LinearService1;

public class WsInvokationThreadWithNoParametersTest {

	Object serviceHandler;
	Client client;
	private String wsdlUrl = "http://127.0.0.1:2401/Linear?wsdl";
	private WsInvokationThread invokation;
	private Result resultSetter;
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
	public void setUp() throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, InterruptedException {
		
		LinearService1 ws = new LinearService1(0.3, 0);

		ep = Endpoint.create(ws);
		ep.publish("http://0.0.0.0:2401/Linear");

		waitAWhile();

		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		client = dcf.createClient(wsdlUrl);

		resultSetter = new Result();
	}


	@After
	public void tearDown() {
	//	ep.stop();
	}
	
	@Test
	public void shouldInvokeManuallyThreeMethod() throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsdlUrl, this.getClass()
				.getClassLoader());

		for (int i = 0; i < 10; i++) {
			Object[] returnedValue = client.invoke("three");

			assertEquals(3, returnedValue[0]);
		}
	}

	@Test
	public void shouldRepeatedlyManuallyInvokeThreeMethod()
			throws Exception {
		for (int i = 0; i < 10; i++) {

			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory
					.newInstance();
			Client client = dcf.createClient(wsdlUrl, this.getClass()
					.getClassLoader());

			Object[] returnedValue = client.invoke("three");

			System.out.println(returnedValue[0]);
			assertEquals(3, returnedValue[0]);
		}
	}

	@Test
	public void testInvoke() throws Exception {
		invokation = new WsInvokationThread(client, resultSetter,
				"three");
		assertEquals(3, invokation.invoke()[0]);
	}

	@Test(timeout = 5000)
	public void testRun() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, TimeoutException {
		invokation = new WsInvokationThread(client, resultSetter,
				"three");
		new Thread(invokation).start();
		assertEquals(3, resultSetter.getResultValue());
	}

	private void waitAWhile() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
