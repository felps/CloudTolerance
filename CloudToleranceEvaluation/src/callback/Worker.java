package callback;

public class Worker implements Runnable{

		public Callable myBoss;
	    // Worker gets a handle to the boss object via the Callable interface.
	    // There's no way this worker class can call any other method other than
	    // the one in Callable.
		public Result result;
	    
		public void invokeService(Callable myBoss, Result result)
	    {
	    	System.out.println("Excuse me sir...");
	    	sleep();
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

		@Override
		public void run() {
			invokeService(myBoss, result);
		}

}
