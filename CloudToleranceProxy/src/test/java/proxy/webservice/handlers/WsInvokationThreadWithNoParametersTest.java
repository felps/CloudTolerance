package proxy.webservice.handlers;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.utils.Result;
import proxy.utils.StartTestWebServices;

public class WsInvokationThreadWithNoParametersTest {

	Object serviceHandler;
	Client client;
	private String wsdlUrl = StartTestWebServices.CREDITCARD_WSDL;
	private WsInvokationThread invokation;
	private Result resultSetter;

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokationThreadNoParametersTest");
		System.out.println("----------------------");
		System.out.println("----------------------");

		StartTestWebServices.raiseCreditService(0, 0);
		Thread.sleep(5000);

	}

	@AfterClass
	public static void tearDown() {
		StartTestWebServices.killAllServices();
	}

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, InterruptedException {

		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		client = dcf.createClient(wsdlUrl);

		resultSetter = new Result();
	}

	@Test
	public void shouldInvokeManuallyIssuePaymentMethod() throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsdlUrl, this.getClass()
				.getClassLoader());

		for (int i = 0; i < 10; i++) {
			Object[] returnedValue = client.invoke("issuePayment");

			System.out.println(returnedValue[0]);
			if ((Boolean) returnedValue[0])
				System.out.println("Teste");
		}
	}

	@Test
	public void shouldRepeatedlyManuallyInvokeIssuePaymentMethod()
			throws Exception {
		for (int i = 0; i < 10; i++) {

			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory
					.newInstance();
			Client client = dcf.createClient(wsdlUrl, this.getClass()
					.getClassLoader());

			Object[] returnedValue = client.invoke("issuePayment");

			System.out.println(returnedValue[0]);
			assertTrue((Boolean) returnedValue[0]);
		}
	}

	@Test
	public void testInvoke() throws Exception {
		invokation = new WsInvokationThread(client, resultSetter,
				"issuePayment");
		assertTrue((Boolean) invokation.invoke()[0]);
	}

	@Test(timeout = 5000)
	public void testRun() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, TimeoutException {
		invokation = new WsInvokationThread(client, resultSetter,
				"issuePayment");
		new Thread(invokation).start();
		assertTrue((Boolean) resultSetter.getResultValue());
	}

}
