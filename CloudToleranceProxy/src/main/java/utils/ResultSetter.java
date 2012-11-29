package utils;

public class ResultSetter {

	private Object[] result;
	private boolean wasSet = false;

	public synchronized Object[] getResult() throws InterruptedException {
//		System.out.println("Waiting for a notification on object " + this.toString());
		wait();
		return result;
	}

	public synchronized void setResult(Object[] result) {
		this.result = result;
		this.wasSet = true;
//		sleep();
//		System.out.println("Notifying completion on object " + this.toString());
		notify();
	}

	private void sleep() {
		try {
			Thread.sleep(150000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean wasSet() {
		return wasSet;
	}

}
