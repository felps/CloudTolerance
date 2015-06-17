package proxy;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.choreography.BPMNTask;
import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;
import webservices.LinearService1;

import com.sun.xml.internal.ws.api.EndpointAddress;

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
	}
	
	@Test(timeout=10000)
	public void shouldRaiseASecondProxyEndpointIfItDoesNotExist() throws TimeoutException, URISyntaxException, IOException, InterruptedException {
		BPMNTask task1 = new BPMNTask("Task1", "http://0.0.0.0:3311/task1", "http://127.0.0.1:3312/task2?wsdl", "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		BPMNTask task2 = new BPMNTask("Task2", "http://0.0.0.0:3312/task2", "http://127.0.0.1:3311/task1?wsdl", "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		
		ChoreographyActor proxy1 = new ChoreographyEndpoint(task1);
		ChoreographyActor newProxy = new ProxyEndpoint(task2);
		
		assertEquals("http://0.0.0.0:3311/task1", proxy1.getPublishURL());
		assertEquals("http://127.0.0.1:3312/task2?wsdl", proxy1.getNextProxyUrl());
		assertEquals("addOne", proxy1.getWsMethodName());

		proxy1.setNextProxyUrl(newProxy.getPublishURL());
		proxy1.addOtherProxy(newProxy);
		
		proxy1.publishWS();
//		newProxy.publishWS(newProxy.getPublishURL());

		String wsdlURL = "http://127.0.0.1:3311/task1?wsdl";

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

	//@Test(timeout=10000)
	public void shouldRaiseASecondAndThirdProxyEndpointIfItDoesNotExist() throws TimeoutException, URISyntaxException, IOException, InterruptedException {
		BPMNTask task1 = new BPMNTask("Task1", "http://0.0.0.0:3311/task1", "http://127.0.0.1:3312/task2?wsdl", "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		BPMNTask task2 = new BPMNTask("Task2", "http://0.0.0.0:3312/task2", "http://127.0.0.1:3311/task3?wsdl", "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		BPMNTask task3 = new BPMNTask("Task3", "http://0.0.0.0:3313/task3", "http://127.0.0.1:3311/task1?wsdl", "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		
		ChoreographyActor proxy1 = new ChoreographyEndpoint(task1);
		ChoreographyActor newProxy = new ProxyEndpoint(task2);
		
		assertEquals("http://0.0.0.0:3311/task1", proxy1.getPublishURL());
		assertEquals("http://127.0.0.1:3312/task2?wsdl", proxy1.getNextProxyUrl());
		assertEquals("addOne", proxy1.getWsMethodName());

		proxy1.setNextProxyUrl(newProxy.getPublishURL());
		proxy1.addOtherProxy(newProxy);
		
		proxy1.publishWS();
//		newProxy.publishWS(newProxy.getPublishURL());

		String wsdlURL = "http://127.0.0.1:3311/task1?wsdl";

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

	@SuppressWarnings("restriction")
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
