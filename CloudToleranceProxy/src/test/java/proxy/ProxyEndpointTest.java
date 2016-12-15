package proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.xml.ws.api.EndpointAddress;

import br.ime.usp.improv.proxy.ChoreographyActor;
import br.ime.usp.improv.proxy.ChoreographyEndpoint;
import br.ime.usp.improv.proxy.ProxyEndpoint;
import br.ime.usp.improv.proxy.choreography.BPMNTask;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvokation;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvoker;
import webservices.LinearService1;

	

public class ProxyEndpointTest {
	private static Endpoint ep;
	
	@BeforeClass
	public static void setUpClass(){
		LinearService1 ws = new LinearService1(0, 0);
		ep = Endpoint.create(ws);
		ep.publish("http://0.0.0.0:2401/Linear");
		
	}
	
	@AfterClass
	public static void tearDownClass(){
		if(ep!=null)
			ep.stop();
	}
	
	@Test(timeout=10000)
	public void shouldRaiseASecondProxyEndpointIfItDoesNotExist() throws TimeoutException, URISyntaxException, IOException, InterruptedException {
		String task1wsdl = "http://127.0.0.1:3311/Task1?wsdl";
		String task2wsdl = "http://127.0.0.1:3312/Task2?wsdl";

		BPMNTask task1 = new BPMNTask("Task1", "http://0.0.0.0:3311/", task2wsdl, "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		BPMNTask task2 = new BPMNTask("Task2", "http://0.0.0.0:3312/", task1wsdl, "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		
		ChoreographyActor proxy1 = new ChoreographyEndpoint(task1);
		ChoreographyActor newProxy = new ProxyEndpoint(task2);
		
		assertEquals("http://0.0.0.0:3311/Task1", proxy1.getPublishURL());
		assertEquals(task2wsdl, proxy1.getNextProxyUrl());
		assertEquals("addOne", proxy1.getWsMethodName());

		proxy1.setNextProxyUrl(newProxy.getPublishURL());
		proxy1.addOtherProxy(newProxy);
		
		proxy1.publishWS();
//		newProxy.publishWS(newProxy.getPublishURL());

		String wsdlURL = task1wsdl;

		try{
			checkServiceAvailability(wsdlURL);
		} catch (Exception e){
			fail("WS not available");
		}
		
		WsInvoker wsInvoker = new WsInvoker(wsdlURL);
		warmUpInvokation(wsInvoker);
		
		Thread.sleep(1000);
		assertEquals(2, singleInvokation(wsInvoker));
		
	}

	@Test(timeout=10000)
	public void shouldRaiseASecondAndThirdProxyEndpointIfItDoesNotExist() throws TimeoutException, URISyntaxException, IOException, InterruptedException {
		String task1Wsdl = "http://127.0.0.1:3313/Task1?wsdl";
		String task2Wsdl = "http://127.0.0.1:3314/Task2?wsdl";
		String task3wsdl = "http://127.0.0.1:3315/Task3?wsdl";
		
		BPMNTask task1 = new BPMNTask("Task1", "http://0.0.0.0:3313/", task2Wsdl, "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		BPMNTask task2 = new BPMNTask("Task2", "http://0.0.0.0:3314/", task3wsdl, "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		BPMNTask task3 = new BPMNTask("Task3", "http://0.0.0.0:3315/", task1Wsdl, "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		
		ChoreographyActor proxy1 = new ChoreographyEndpoint(task1);
		ChoreographyActor proxy2 = new ProxyEndpoint(task2);
		ChoreographyActor proxy3 = new ProxyEndpoint(task3);
		
		assertEquals("http://0.0.0.0:3313/Task1", proxy1.getPublishURL());
		assertEquals(task2Wsdl, proxy1.getNextProxyUrl());
		assertEquals("addOne", proxy1.getWsMethodName());

		proxy1.setNextProxyUrl(proxy2.getPublishURL());
		proxy1.addOtherProxy(proxy2);
		proxy1.addOtherProxy(proxy3);
		
		
		proxy1.publishWS();
		//proxy2.publishWS(proxy2.getPublishURL());

		String wsdlURL = task1Wsdl;

		try{
			checkServiceAvailability(wsdlURL);
		} catch (Exception e){
			fail("WS not available");
		}
		
		WsInvoker wsInvoker = new WsInvoker(wsdlURL);
		warmUpInvokation(wsInvoker);
		
		Thread.sleep(1000);
		assertEquals(3, singleInvokation(wsInvoker));
		
	}

	public boolean checkServiceAvailability(String wsdlURL) throws URISyntaxException, IOException {
		EndpointAddress ep = new EndpointAddress(
				wsdlURL); 
		ep.openConnection().getContent();
		return true;
	}

	private int singleInvokation(WsInvoker invoker) throws TimeoutException {
		WsInvokation returnedValue = invoker.invokeWebMethod(
				"startChoreography", 0);
		int result = (Integer) returnedValue.getResultSetter().getResultValue();
		return result;
	}
	
	private int warmUpInvokation(WsInvoker invoker) {
		int result = 0;
		try { 
			result = singleInvokation(invoker);
			System.out.println(result);
		} catch (Exception e) {
			System.err.println("Don't panic! In this first warm up execution it is normal to have a timeout." + '\n');
		}
		return result;
	}

}
