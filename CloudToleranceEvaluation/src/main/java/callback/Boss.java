package callback;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class Boss implements Callable {

	private HashMap<Integer, Result> resultMap;

	public Boss(int workerAmount) throws InterruptedException {
		int key = 0;
		resultMap = new HashMap<Integer, Result>();

		// Boss creates many worker objects, and tells them to do some work.
		for (key = 0; key < workerAmount; key++) {
			Worker w1 = new Worker();
			
			Result resultSetter = new Result();
			resultMap.put(key, resultSetter);
			
			w1.result=resultSetter;
			w1.myBoss = this;
			new Thread(w1).start();

		}
		
		// Notice, we're passing a reference of the boss to the worker.
		for(key=0; key<workerAmount; key++)
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

	public void callBackMethod(Result result) {
		System.out.println("What do you want?");
		result.setResultValue(null);
	}

	public void directMethod() {
		System.out.println("I'm out for coffee.");
	}
}
