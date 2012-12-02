package proxy.webservice.handlers;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.utils.Result;

public class WsInvokationThreadWithNoParametersTest {

	Object serviceHandler;
	Client client;
	private String wsdlUrl = "http://127.0.0.1:2302/creditcard?wsdl";
	private String wsdlClazzName;
	private WsInvokationThread invokation;
	private Result resultSetter;
	

	class RunCreditService implements Runnable{

		public void run() {
			String[] args;
			args = new String[2];
			args[0]="0.0"; // fail-stop probability
			args[1]="0.0"; // faulty response probability
			
			realwebservices.CreditCard.main(args);
		}
		
	}

	
	@BeforeClass
	public static void setEnvironment() throws InterruptedException {

		WsInvokationThreadWithNoParametersTest helper = new WsInvokationThreadWithNoParametersTest();
		RunCreditService creditServiceInitiliazer = helper.new RunCreditService();
		new Thread(creditServiceInitiliazer).start();
		Thread.sleep(5000);
		System.out.println("Done creating Web Service proxies");
	}
	
	@Before
	public void setUp() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		wsdlClazzName = "realwebservices.IssuePayment";
		
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		client = dcf.createClient(wsdlUrl);

		serviceHandler = Thread.currentThread().getContextClassLoader()
				.loadClass(wsdlClazzName).newInstance();

		resultSetter = new Result();
	}
	
	@Test
	public void shouldInvokeManuallyIssuePaymentMethod() throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsdlUrl, this
				.getClass().getClassLoader());

		Object creditCard = Thread.currentThread().getContextClassLoader()
				.loadClass("realwebservices.IssuePayment").newInstance();
		
		for (int i = 0; i < 10; i++) {
			Object[] returnedValue = client.invoke("issuePayment", creditCard);

			System.out.println(returnedValue[0]);
			if ((Boolean) returnedValue[0])
				System.out.println("Teste");
		}
	}
	
	@Test
	public void testInvoke() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		invokation = new WsInvokationThread(client, resultSetter, "issuePayment");
		assertTrue((Boolean) invokation.invoke()[0]);
	}

	@Test(timeout=5000)
	public void testRun() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		invokation = new WsInvokationThread(client, resultSetter, "issuePayment");
		new Thread(invokation).start();
		assertTrue((Boolean) resultSetter.getResultValue());
	}

}
