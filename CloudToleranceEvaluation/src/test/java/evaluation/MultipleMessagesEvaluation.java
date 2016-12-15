package evaluation;

import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ime.usp.improv.proxy.webservice.handlers.WsInvokation;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvoker;

public class MultipleMessagesEvaluation {

	public static final String CHOR4MSGS 	= "http://127.0.0.1:2101/choreography?wsdl";
	public static final String CHOR8MSGS 	= "http://127.0.0.1:2102/choreography?wsdl";
	public static final String CHOR16MSGS 	= "http://127.0.0.1:2103/choreography?wsdl";
	public static final String CHOR32MSGS 	= "http://127.0.0.1:2104/choreography?wsdl";
	public static final String CHOR64MSGS 	= "http://127.0.0.1:2105/choreography?wsdl";
	public static final String CHOR128MSGS 	= "http://127.0.0.1:2106/choreography?wsdl";
	public static final String CHOR256MSGS 	= "http://127.0.0.1:2107/choreography?wsdl";
	public static final String CHOR512MSGS 	= "http://127.0.0.1:2108/choreography?wsdl";
	public static final String CHOR1024MSGS = "http://127.0.0.1:2109/choreography?wsdl";
	private static Logger log;

	@BeforeClass
	public static void setUp() {
		log = Logger.getLogger(MultipleMessagesEvaluation.class);
		log.info("Initiating Multiple Messages Evaluation");
	}

	@Test
	public void warmUpInvokation() throws TimeoutException {
		long startTime;

		startTime = System.currentTimeMillis();
		WsInvoker invoker = new WsInvoker(CHOR4MSGS);
		invoker.setTimeout(60000);
		WsInvokation returnedValue = invoker.invokeWebMethod(
				"startChoreograph", 0);
		log.info("4 Messages " 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		invoker = new WsInvoker(CHOR8MSGS);
		invoker.setTimeout(90000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info("8 Messages " 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		invoker = new WsInvoker(CHOR16MSGS);
		invoker.setTimeout(120000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info("16 Messages " 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		invoker = new WsInvoker(CHOR32MSGS);
		invoker.setTimeout(180000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info("32 Messages " 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		invoker = new WsInvoker(CHOR64MSGS);
		invoker.setTimeout(240000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info("64 Messages " 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		invoker = new WsInvoker(CHOR128MSGS);
		invoker.setTimeout(360000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info("128 Messages " 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		invoker = new WsInvoker(CHOR256MSGS);
		invoker.setTimeout(360000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info("256 Messages " 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		invoker = new WsInvoker(CHOR512MSGS);
		invoker.setTimeout(600000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info("512 Messages " 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		invoker = new WsInvoker(CHOR1024MSGS);
		invoker.setTimeout(40000);
		returnedValue = invoker.invokeWebMethod("startChoreograph", 0);
		log.info("1024 Messages " 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));
	}

	@Test
	public void multipleMessagesEvaluation() throws TimeoutException {
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("Starting Multiple Messages Evaluation");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		int invokes = 100;
		log.info("Starting evaluation with 4 messages");
		multipleInvokations(CHOR4MSGS, invokes);

		log.info("Starting evaluation with 8 messages");
		multipleInvokations(CHOR8MSGS, invokes);
		log.info("Starting evaluation with 16 messages");
		multipleInvokations(CHOR16MSGS, invokes);
		log.info("Starting evaluation with 32 messages");
		multipleInvokations(CHOR32MSGS, invokes);
		log.info("Starting evaluation with 64 messages");
		multipleInvokations(CHOR64MSGS, invokes);
		log.info("Starting evaluation with 128 messages");
		multipleInvokations(CHOR128MSGS, invokes);
		log.info("Starting evaluation with 256 messages");
		multipleInvokations(CHOR256MSGS, invokes);
		log.info("Starting evaluation with 512 messages");
		multipleInvokations(CHOR512MSGS, invokes);
		log.info("Starting evaluation with 1024 messages");
		multipleInvokations(CHOR1024MSGS, invokes);
	}

	private void multipleInvokations(String wsdl, int invokationAmount)
			throws TimeoutException {
		WsInvoker invoker = new WsInvoker(wsdl);
		invoker.setTimeout(240000);

		for (int i = 0; i < invokationAmount; i++) {
			long startTime = System.currentTimeMillis();
			WsInvokation returnedValue = invoker.invokeWebMethod(
					"startChoreograph", 0);
			log.info("Evaluation number " + i + "WSDL: " + wsdl + '\n'
					+ "Returned value: "
					+ returnedValue.getResultSetter().getResultValue() + '\n'
					+ "Time it took: "
					+ (System.currentTimeMillis() - startTime));
		}
	}

}
