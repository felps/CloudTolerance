package evaluation;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;

import java.util.concurrent.TimeoutException;

import org.junit.*;

import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;


public class MultipleMessages {

	public static final String CHOR4MSGS 	= "http://127.0.0.1:2102/choreography?wsdl";
	public static final String CHOR8MSGS 	= "http://127.0.0.1:2103/choreography?wsdl";
	public static final String CHOR16MSGS 	= "http://127.0.0.1:2104/choreography?wsdl";
	public static final String CHOR32MSGS 	= "http://127.0.0.1:2105/choreography?wsdl";
	public static final String CHOR64MSGS 	= "http://127.0.0.1:2106/choreography?wsdl";
	public static final String CHOR128MSGS 	= "http://127.0.0.1:2107/choreography?wsdl";
	public static final String CHOR256MSGS 	= "http://127.0.0.1:2108/choreography?wsdl";
	public static final String CHOR512MSGS 	= "http://127.0.0.1:2109/choreography?wsdl";
	public static final String CHOR1024MSGS	= "http://127.0.0.1:2110/choreography?wsdl";
	private static Logger log; 

	
	@BeforeClass
	public static void setUp() {
		log = Logger.getLogger(MultipleMessages.class);
		log.info("Initiating Multiple Messages Evaluation");
	}
	
	@Test
	public void warmUpInvokation() throws TimeoutException {
		WsInvoker invoker = new WsInvoker(CHOR4MSGS);
		invoker.setTimeout(40000);
		WsInvokation returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
		invoker = new WsInvoker(CHOR4MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
		invoker = new WsInvoker(CHOR8MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
		invoker = new WsInvoker(CHOR16MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
		invoker = new WsInvoker(CHOR32MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
		invoker = new WsInvoker(CHOR64MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
		invoker = new WsInvoker(CHOR128MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
		invoker = new WsInvoker(CHOR256MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
		invoker = new WsInvoker(CHOR512MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
		invoker = new WsInvoker(CHOR1024MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info(returnedValue.getResultSetter().getResultValue());
		
	}

//	@Test
	public void multipleMessagesEvaluation() throws TimeoutException{
		log.info("Starting Multiple Messages Evaluation");
		multipleInvokations(CHOR4MSGS);
		multipleInvokations(CHOR8MSGS);
		multipleInvokations(CHOR16MSGS);
		multipleInvokations(CHOR32MSGS);
		multipleInvokations(CHOR64MSGS);
		multipleInvokations(CHOR128MSGS);
		multipleInvokations(CHOR256MSGS);
		multipleInvokations(CHOR512MSGS);
		multipleInvokations(CHOR1024MSGS);
	}
	
	private void multipleInvokations(String wsdl) throws TimeoutException {
		WsInvoker invoker = new WsInvoker(wsdl);
		invoker.setTimeout(40000);
		
		for(int i=0; i<100; i++){
			long startTime = System.currentTimeMillis();
			WsInvokation returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
			log.info("Returned value: " + returnedValue.getResultSetter().getResultValue());
			log.info("Time it took: " + (System.currentTimeMillis() - startTime));
		}
	}

}

