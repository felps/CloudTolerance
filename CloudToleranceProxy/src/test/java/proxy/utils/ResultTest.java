package proxy.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ime.usp.improv.proxy.utils.Result;

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

	@BeforeClass
	public static void setUpClass() {
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("Beginng ResultSetter Test");
		System.out.println("----------------------");
		System.out.println("----------------------");
	}
	
	@AfterClass
	public static void tearDown(){
		StartTestWebServices.killAllServices();
	}
	
	@Before
	public void setUp() {
		resultSetter = new Result();
	}

	@Test
	public void shouldSetResultValueAndGetResultValue() throws TimeoutException {
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
	public void shouldWaitUntilValueIsSetAndGetResponse() throws TimeoutException {
		Producer producer =  new Producer();
		producer.resultSetter = resultSetter;
		new Thread(producer).start();
		int returnedValue = (Integer) resultSetter.getResultValue();
		assertEquals(15, returnedValue);
	}
}
