package evaluation;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeoutException;

import org.junit.BeforeClass;
import org.junit.Test;

import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;
import utils.Shell;

public class MultipleMessaging {

	public static final String WSDL_CHOR = "http://127.0.0.1:2100/choreography?wsdl";
	public static final String WSDL_PROXY1 = "http://127.0.0.1:2201/proxy?wsdl";
	
	@Test(timeout=40000)
	public void warmUpInvokation() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(WSDL_CHOR);
		invoker.setTimeout(40000);
		WsInvokation returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		assertEquals(2, returnedValue.getResultSetter().getResultValue());
	}

}
