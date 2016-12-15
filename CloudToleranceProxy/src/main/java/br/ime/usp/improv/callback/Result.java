package br.ime.usp.improv.callback;

import java.util.concurrent.TimeoutException;


public class Result {

	private Object resultValue;
	private boolean wasSet;
	private long timeout = -1;

	public Result() {
	}

	public Result(long timeout) {
		this.timeout = timeout;
	}

	public Object getResultValue() throws TimeoutException {
		if (!wasSet)
			waitUntilIsSet();

		if(wasSet)
			return resultValue;
		else
			throw new TimeoutException();
	}

	private synchronized void waitUntilIsSet() {
		try {
			if (timeout == -1)
				wait();
			else
				wait(timeout);
		} catch (InterruptedException e) {
		}
	}

	public boolean wasSet() {
		return wasSet;
	}

	public synchronized void setResultValue(Object newValue) {
		if (!wasSet) {
			resultValue = newValue;
			wasSet = true;
			notify();
		}
	}

}