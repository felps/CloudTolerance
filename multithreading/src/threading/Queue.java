package threading;

public class Queue {

	// A correct implementation of a producer and consumer.
	Object[] response;
	boolean valueSet = false;

	synchronized Object[] get() {
		if (!valueSet)
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("InterruptedException caught");
			}
		System.out.println("Got: " + response);
		valueSet = false;
		notify();
		return response;
	}

	synchronized void put(Object[] n) {
		if (valueSet)
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("InterruptedException caught");
			}
		this.response = n;
		valueSet = true;
		System.out.println("Put: " + n);
		notify();
	}
}