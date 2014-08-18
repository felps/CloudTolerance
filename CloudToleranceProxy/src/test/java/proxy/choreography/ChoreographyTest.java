package proxy.choreography;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;
import webservices.LinearService1;

public class ChoreographyTest {

	private static Logger log;

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
		LinearService1 ws = new LinearService1(0, 0);
		Endpoint.publish("http://0.0.0.0:2401/Linear", ws);
		Thread.sleep(5000);
	}
	
	@Test
	public void shouldValidateIfLastLinkMatchesEndpoint() {
		
	}
	
	@Test
	public void shouldEnactAChoreography() throws TimeoutException {
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

		chor = new Choreography();

		chor.addTask(task1);
		chor.addTask(task2);
		chor.addTask(task3);
		assertEquals(3, chor.getProcessingTasks().size());

		chor.setEndpoint(task1);
		assertEquals(2, chor.getProcessingTasks().size());

		Choreography.enact(chor);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WsInvoker wsInvoker = new WsInvoker("http://0.0.0.0:2430/task?wsdl");
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
