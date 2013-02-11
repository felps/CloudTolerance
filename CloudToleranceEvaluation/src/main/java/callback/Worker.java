package callback;

import proxy.webservice.handlers.WsInvoker;

public class Worker implements Runnable{

		public Callable myBoss;
		public String wsdlURL;
		public Result result;
		private String methodName;
		private Object[] args;

		// Worker gets a handle to the boss object via the Callable interface.
	    // There's no way this worker class can call any other method other than
	    // the one in Callable.
	    
		public void invokeService(Callable myBoss, Result result)
	    {
	    	System.out.println("Excuse me sir...");
	    	WsInvoker invoker;
	    	invoker = new WsInvoker(wsdlURL);
	    	Object returnValue = invoker.invokeWebMethod(methodName, args);
	    	result.setResultValue(returnValue);
	    	myBoss.callBackMethod(result);
	        // ERROR!
	        //myBoss.directMethod();
	    }

		private void sleep() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {
			invokeService(myBoss, result);
		}

}
