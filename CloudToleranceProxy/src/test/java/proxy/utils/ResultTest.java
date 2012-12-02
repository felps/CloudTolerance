package proxy.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ResultTest {

	Result resultSetter ;
	
	class Producer implements Runnable{
		public Result resultSetter;
		public void run() {
			try {
				Thread.sleep((long) Math.random()*5000);
				resultSetter.setResultValue(15);
			} catch (InterruptedException e) {
			}
		}
	}

	@Before
	public void setUp() {
		resultSetter = new Result();
	}

	@Test
	public void shouldSetResultValueAndGetResultValue() {
		resultSetter.setResultValue(10);
		int returnedValue = (Integer) resultSetter.getResultValue();
		
		assertEquals(10, returnedValue);
	}
	
	@Test
	public void ifTheValueWasSetThenWasSetVariableMustBeTrue(){
		resultSetter.setResultValue(10);
		assertTrue(resultSetter.wasSet());
	}
	
	@Test
	public void ifTheValueWasNotSetThenWasSetVariableMustBeFalse(){
		assertTrue(!resultSetter.wasSet());
	}

	@Test(timeout=5500)
	public void shouldWaitUntilValueIsSetAndGetResponse() {
		Producer producer =  new Producer();
		producer.resultSetter = resultSetter;
		new Thread(producer).start();
		int returnedValue = (Integer) resultSetter.getResultValue();
		assertEquals(15, returnedValue);
	}
}
