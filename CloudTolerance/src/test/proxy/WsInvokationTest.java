package proxy;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;

import utils.ResultSetter;

public class WsInvokationTest {

	private static final String WSDL_ENDPOINT = "http://127.0.0.1:2302/creditcard?wsdl";
	private static final String WS_ENDPOINT = "http://127.0.0.1:2302/creditcard";

	private WsInvokation creditCardHandler;

	@Test
	public void verifyTestConditions() throws IOException {
		// Create a URL for the desired page
		URL url = new URL(WSDL_ENDPOINT);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String str;
		String output = "";
		while ((str = in.readLine()) != null) {
			System.out.println(str);
			output += str;
		}
		assertTrue("Should be able to read a wsdl from " + WSDL_ENDPOINT,
				output.length() != 0);
	}

	@Test
	public void shouldInvokeManuallyIssuePaymentMethod() throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(WSDL_ENDPOINT, this.getClass()
				.getClassLoader());

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
		
		creditCard.wsdlUrl = WSDL_ENDPOINT;
		creditCard.wsdlClazzName = "webservices.IssuePayment";
		creditCard.serviceMethod = "issuePayment";
		creditCard.paramMethod = null;
		creditCard.result = returnedValues;
		
		creditCard.prepareForInvoke();
		creditCard.run();

		assertEquals(returnedValues.result[0], true);
	}

}
