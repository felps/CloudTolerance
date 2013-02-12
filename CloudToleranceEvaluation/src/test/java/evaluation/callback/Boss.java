package evaluation.callback;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import proxy.utils.Result;
import proxy.webservice.handlers.WsInvoker;

public class Boss implements Callable {

	private HashMap<Integer, Result> resultMap;
	public Logger log;

	public Boss(int workerAmount, String wsdl, Logger externalLog) throws InterruptedException {
		
		this.log=externalLog;
		
		int key = 0;
		resultMap = new HashMap<Integer, Result>();
		WsInvoker invoker = new WsInvoker(wsdl);

		// Boss creates many worker objects, and tells them to do some work.
		for (key = 0; key < workerAmount; key++) {
			Worker w1 = new Worker();

			Result resultSetter = new Result();
			resultMap.put(key, resultSetter);

			w1.result = resultSetter;
			w1.myBoss = this;
			w1.log = externalLog;
			w1.wsdl = wsdl;
			w1.invoker = invoker;
			new Thread(w1).start();

		}

		for (key = 0; key < workerAmount; key++)
			waitCallback(key);

		System.out.println("Whatever!");
	}

	public void waitCallback(int key) throws InterruptedException {
		try {
			resultMap.get(key).getResultValue();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int reportExecutionCompletion(long elapsedTime, int key) {
		System.out.println("What do you want?");
		
		return -1;
	}

	public void directMethod() {
		System.out.println("I'm out for coffee.");
	}

}
