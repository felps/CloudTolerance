package proxy.utils;

public class Result {

	private Object resultValue;
	private boolean wasSet;
	
	public Object getResultValue() {
		if(!wasSet)
			waitUntilIsSet();
		
		return resultValue;
	}

	private void waitUntilIsSet() {
		try {
			wait();
		} catch (InterruptedException e) {
		}
	}

	public boolean wasSet() {
		return wasSet;
	}
	
	public void setResultValue(Object newValue){
		if(!wasSet)
		{
			resultValue = newValue;
			notifyAll();
		}
	}
	
	
}
