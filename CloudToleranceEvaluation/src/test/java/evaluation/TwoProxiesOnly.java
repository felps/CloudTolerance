package evaluation;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeoutException;

import org.junit.Test;

import br.ime.usp.improv.proxy.webservice.handlers.WsInvokation;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvoker;

public class TwoProxiesOnly {

	public static final String WSDL_CHOR = "http://godzilla.ime.usp.br:2100/choreography?wsdl";
	public static final String WSDL_PROXY1 = "http://godzilla.ime.usp.br:2201/proxy?wsdl";
	
	/*
	 * Script to start local test: TwoProxiesOnly.sh
	 */
	
	@Test(timeout=40000)
	public void warmUpInvokation() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(WSDL_CHOR);
		invoker.setTimeout(40000);
		WsInvokation returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		assertEquals(2, returnedValue.getResultSetter().getResultValue());
	}

	@Test(timeout=40000)
	public void multipleInvokations() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(WSDL_CHOR);
		invoker.setTimeout(40000);
		
		for(int i=0; i<100; i++){
			long startTime = System.currentTimeMillis();
			WsInvokation returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
			assertEquals(2, returnedValue.getResultSetter().getResultValue());
			System.out.println("Time it took: " + (System.currentTimeMillis() - startTime));
		}
	}

}
