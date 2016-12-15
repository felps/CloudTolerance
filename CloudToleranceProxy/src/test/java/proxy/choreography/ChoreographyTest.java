package proxy.choreography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ime.usp.improv.proxy.choreography.BPMNTask;
import br.ime.usp.improv.proxy.choreography.Choreography;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvokation;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvoker;
import webservices.LinearService1;

public class ChoreographyTest {

	private static Logger log;
	private static Endpoint ep;
	
	@BeforeClass
	public static void setUpEnvironment() throws InterruptedException {
		log = Logger.getLogger(ChoreographyTest.class);
		log.info("Initiating Proxy Recovery Evaluation");
		TimeUnit unit = TimeUnit.MINUTES;

		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng WsInvokationThreadNoParametersTest");
		System.out.println("----------------------");
		System.out.println("----------------------");
	}
	
	@BeforeClass
	public static void setUp(){
		LinearService1 ws = new LinearService1(0, 0);
		ep = Endpoint.create(ws);
		ep.publish("http://0.0.0.0:2401/Linear");
	}
	
	@AfterClass
	public static void tearDown() {
		if(ep!=null)
			ep.stop();
	}
	
//	@Test
	public void shouldRemoveEndpointFromProcessingTasks(){
		
	}
	
	
	@Test
	public void shouldValidateIfChoreographyTasksEndpointsAreValid() {
		Choreography chor;
		BPMNTask task1, task2, task3;

		task1 = new BPMNTask("Task1", "http://0.0.0.0:2430/task",
				"http://127.0.0.1:2431/task?wsdl", "addOne",
				"http://127.0.0.1:2401/Linear?wsdl");
		task2 = new BPMNTask("Task2", "http://0.0.0.0:2431/task",
				"http://127.0.0.1:2432/task?wsdl", "addOne",
				"http://127.0.0.1:2401/Linear?wsdl");
		task3 = new BPMNTask("Task3", "http://0.0.0.0:2432/task",
				"http://127.0.0.1:2430/task?wsdl", "addOne",
				"http://127.0.0.1:2401/Linear?wsdl");

		// Valid choreography
		chor = new Choreography();

		chor.addTask(task1);
		chor.addTask(task2);
		chor.addTask(task3);
		chor.setEndpoint(task1);

		assertTrue(chor.validate());
		
		
		// Choreography missing task linking to endpoint
		chor = new Choreography();

		chor.addTask(task1);
		chor.addTask(task2);
		chor.setEndpoint(task1);

		assertFalse(chor.validate());

		// Choreography missing task endpoint points to
		chor = new Choreography();

		chor.addTask(task2);
		chor.addTask(task3);
		chor.setEndpoint(task3);
		
		assertFalse(chor.validate());

		// Choreography missing task linking two processing tasks
		chor = new Choreography();

		chor.addTask(task1);
		chor.addTask(task3);
		chor.setEndpoint(task1);

		assertFalse(chor.validate());
				
		
	}
	
//	@Test
	public void shouldEnactAChoreography() throws TimeoutException {
		Choreography chor;
		BPMNTask task1, task2, task3;

		String name;
		String publishEndpoint;
		String nextLink;
		String methodName = "addOne";
		String wsdlFile = "http://127.0.0.1:2401/Linear?wsdl";

		
		name = "Task1";
		publishEndpoint = "http://0.0.0.0:2430/task";
		nextLink = "http://127.0.0.1:2431/task?wsdl";
		task1 = new BPMNTask(name, publishEndpoint,
				nextLink, methodName,
				wsdlFile);
		
		name = "Task2";
		publishEndpoint = "http://0.0.0.0:2431/task";
		nextLink = "http://127.0.0.1:2432/task?wsdl";
		task2 = new BPMNTask(name, publishEndpoint,
				nextLink, methodName,
				wsdlFile);
		
		name = "Task3";
		publishEndpoint = "http://0.0.0.0:2432/task";
		nextLink = "http://127.0.0.1:2430/task?wsdl";
		task3 = new BPMNTask(name, publishEndpoint,
				nextLink, methodName,
				wsdlFile);
		
		chor = new Choreography();

		chor.addTask(task1);
		chor.addTask(task2);
		chor.addTask(task3);
		assertEquals(3, chor.getProcessingTasks().size());

		chor.setEndpoint(task1);
		assertEquals(2, chor.getProcessingTasks().size());

		chor.enact();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WsInvoker wsInvoker = new WsInvoker("http://127.0.0.1:2430/task?wsdl");
		warmUpInvokation(wsInvoker);
		
		assertEquals(singleInvokation(wsInvoker), 3);
		
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
			log.warn("Don't panic! In this first warm up execution it is normal to have a timeout." + '\n');
		}
		return result;
	}

}
