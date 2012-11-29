package webservices.implementations;

import static org.junit.Assert.*;

import realwebservices.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webservices.implementations.*;
import webservices.implementations.CreditCard;

import proxy.GenericProxy;

public class FailSafeCreditCardServiceTest {

	private static CreditCard credit = new CreditCard();

	class RunCreditService implements Runnable{

		public void run() {
			String[] args;
			args = new String[2];
			args[0]="0.0"; // fail-stop probability
			args[1]="0.0"; // faulty response probability
			
			realwebservices.CreditCard.main(args);
		}
		
	}

	class RunWeatherService implements Runnable{

		public void run() {
			String[] args;
			args = new String[2];
			args[0]="0.0"; // fail-stop probability
			args[1]="0.0"; // faulty response probability
			
			realwebservices.WeatherForecast.main(args);
		}
		
	}

	
	@BeforeClass
	public static void setEnvironment() throws InterruptedException {

		FailSafeCreditCardServiceTest helper = new FailSafeCreditCardServiceTest();
		RunCreditService creditServiceInitiliazer = helper.new RunCreditService();
		new Thread(creditServiceInitiliazer).start();
		
		RunWeatherService weatherServiceInitiliazer = helper.new RunWeatherService();
		new Thread(weatherServiceInitiliazer).start();

		Thread.sleep(5000);
		
		System.out.println("Creating Web Service proxies");
		if(credit.proxy == null)
			credit.proxy = new GenericProxy();
		credit.proxy.addWebService("http://127.0.0.1:2302/creditcard?wsdl", "realwebservices.IssuePayment");
		credit.proxy.addWebService("http://127.0.0.1:2302/creditcard?wsdl", "realwebservices.VerifyCreditAvailability");
		credit.proxy.addWebService("http://127.0.0.1:2302/creditcard?wsdl", "realwebservices.VerifyPassword");
		System.out.println("Done creating Web Service proxies");
	}
	
	@Test
	public void testIssuePayment() {
		boolean returnedValue = credit.issuePayment();
		assertEquals("The issue payment should always return true", true, returnedValue);
	}

	@Test
	public void testVerifyCreditAvailability() {
		boolean returnedValue = credit.verifyCreditAvailability();
		assertEquals("The issue payment should always return true", true, returnedValue);
	}

	@Test
	public void testVerifyPassword() {
		boolean returnedValue = credit.verifyPassword();
		assertEquals("The issue payment should always return true", true, returnedValue);
	}

}
