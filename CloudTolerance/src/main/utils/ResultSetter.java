package utils;

public class ResultSetter {

	private Object[] result;
	private boolean wasSet = false;

	public Object[] getResult() {
		return result;
	}

	public synchronized void setResult(Object[] result) {
		this.result = result;
		this.wasSet = true;
		notify();
	}

	public boolean wasSet() {
		return wasSet;
	}

}
