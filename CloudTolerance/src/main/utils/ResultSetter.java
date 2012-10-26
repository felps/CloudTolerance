package utils;

public class ResultSetter {

	private Object[] result;
	private boolean wasSet = false;
	
	public Object[] getResult() {
		return result;
	}
	
	public void setResult(Object[] result) {
		this.result = result;
		this.wasSet = true;
		notifyAll();
	}

	public boolean wasSet() {
		return wasSet;
	}
	
}
