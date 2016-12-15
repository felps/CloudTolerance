package br.ime.usp.improv.callback;


public class Worker implements Runnable{

		public Callable myBoss;
		public String wsdlURL;
		public Result result;

		// Worker gets a handle to the boss object via the Callable interface.
	    // There's no way this worker class can call any other method other than
	    // the one in Callable.
	    
		public void invokeService(Callable myBoss, Result result)
	    {
	    	System.out.println("Excuse me sir...");
	    	myBoss.callBackMethod(result);
	        // ERROR!
	        //myBoss.directMethod();
	    }

		public void run() {
			invokeService(myBoss, result);
		}

}
