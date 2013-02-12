package evaluation;

import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;
import evaluation.callback.Boss;
import evaluation.callback.Worker;

public class ParallelRequestsEvaluation {

	public static final String CHOR 		= "http://127.0.0.1:2102/choreography?wsdl";
	private static Logger log ;

	@BeforeClass
	public static void setUp() {
		log = Logger.getLogger(ParallelRequestsEvaluation.class);
		log.info("Initiating Parallel Requests Evaluation");
	}

	@Test
	public void warmUpInvokation() throws TimeoutException {
		long startTime;

		startTime = System.currentTimeMillis();
		WsInvoker invoker = new WsInvoker(CHOR);
		invoker.setTimeout(60000);
		WsInvokation returnedValue = invoker.invokeWebMethod(
				"startChoreograph", 0);
		log.info("single invokation" 
				+ "Returned value: "
				+ returnedValue.getResultSetter().getResultValue() + '\n'
				+ "Time it took: "
				+ (System.currentTimeMillis() - startTime));
	}

	@Test
	public void multipleClientsEvaluation() throws InterruptedException {
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("Starting Parallel Requests Evaluation");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		log.info("-------------------------------------");
		int clients = 100;
		log.info("Starting evaluation with 4 messages");
		parallelInvokations(clients);

	}

//
//	@Test
//    public void shouldRunCorrectly() throws InterruptedException
//    {
//        Boss b;
//        
//        b = new Boss(1,CHOR, log);
//        b.directMethod();
//        
//        System.out.println("Agora com 10 Threads");
//        b = new Boss(10, CHOR, log);
//        b.directMethod();
//    }
	
	private void parallelInvokations(int clients) throws InterruptedException {
		Boss b;
        
        b = new Boss(clients ,CHOR, log);
        b.directMethod();
	}


}
