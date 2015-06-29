package evaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.xml.internal.ws.api.EndpointAddress;

import proxy.ChoreographyActor;
import proxy.ChoreographyEndpoint;
import proxy.ProxyEndpoint;
import proxy.choreography.BPMNTask;
import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;
import webservices.LinearService1;

public class ProxyRecoveryTest {

	private static Endpoint ep;
	private static Logger log;
	
	@BeforeClass
	public static void setUpClass(){
		LinearService1 ws = new LinearService1(0, 0);
		ep = Endpoint.create(ws);
		ep.publish("http://0.0.0.0:2401/Linear");
		log = Logger.getLogger(ProxyRecoveryTest.class);
		log.info("Initiating Proxy Recovery Evaluation");

		
	}
	
	@AfterClass
	public static void tearDownClass(){
	}
	@Test
	public void elapsedTimeToRecoverAProxy() throws TimeoutException, URISyntaxException, IOException, InterruptedException {
		int i, port = 3313;
		for(i=0;i<100;i++)
		singleLoggedInvokation(port+(3*i));
		
	}

	private void singleLoggedInvokation(int port) throws TimeoutException {
		String task1Wsdl = "http://127.0.0.1:"+ port +"/Task1?wsdl";
		String task2Wsdl = "http://127.0.0.1:"+ (port+1) +"/Task2?wsdl";
		
		BPMNTask task1 = new BPMNTask("Task1", "http://0.0.0.0:"+ port +"/", task2Wsdl, "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		BPMNTask task2 = new BPMNTask("Task2", "http://0.0.0.0:"+ (port+1) +"/", task1Wsdl, "addOne", "http://127.0.0.1:2401/Linear?wsdl");
		
		ChoreographyActor proxy1 = new ChoreographyEndpoint(task1);
		ChoreographyActor proxy2 = new ProxyEndpoint(task2);
		
		proxy1.setNextProxyUrl(proxy2.getPublishURL());
		proxy1.addOtherProxy(proxy2);
		
		
		proxy1.publishWS();
		
		
		String wsdlURL = task1Wsdl;
		WsInvoker wsInvoker = new WsInvoker(wsdlURL);
	
		long start = System.nanoTime();
		singleInvokation(wsInvoker);
		log.info("Time to recover: " + (System.nanoTime() - start)+ " ns");
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
