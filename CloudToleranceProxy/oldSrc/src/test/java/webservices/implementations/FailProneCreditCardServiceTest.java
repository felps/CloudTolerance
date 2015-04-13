package webservices.implementations;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import proxy.GenericProxy;
import webservices.implementations.CreditCard;

public class FailProneCreditCardServiceTest {

	private static CreditCard credit = new CreditCard();

	class RunCreditService implements Runnable{

		public void run() {
			String[] args;
			args = new String[2];
			args[0]="0.0"; // fail-stop probability
			args[1]="20.0"; // faulty response probability
			
			realwebservices.CreditCard.main(args);
		}
		
	}

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {

		FailProneCreditCardServiceTest helper = new FailProneCreditCardServiceTest();
		RunCreditService creditServiceInitiliazer = helper.new RunCreditService();
		new Thread(creditServiceInitiliazer).start();
		
		Thread.sleep(5000);
		
		System.out.println("Creating Web Service proxies");
		if(credit.proxy == null)
			credit.proxy = new GenericProxy();
		credit.proxy.addWebService("http://godzilla.ime.usp.br:2302/creditcard?wsdl", "realwebservices.IssuePayment");
		credit.proxy.addWebService("http://godzilla.ime.usp.br:2302/creditcard?wsdl", "realwebservices.VerifyCreditAvailability");
		credit.proxy.addWebService("http://godzilla.ime.usp.br:2302/creditcard?wsdl", "realwebservices.VerifyPassword");
		System.out.println("Done creating Web Service proxies");
	}
	
	@Test
	public void issuePaymentShouldEventuallyReturnWrongAnswer() {

		int counter = 0;
		for(int i=0; i<100; i++){
			if (!credit.issuePayment()){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		fail("The issue payment should eventually return a wrong answer (false)");

	}

	@Test
	public void issuePaymentShouldEventuallyReturnRightAnswer() {

		int counter = 0;
		for(int i=0; i<100; i++){
			if (credit.issuePayment()){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		fail("The issue payment should eventually return a correct answer (true)");

	}

	@Test
	public void verifyCreditShouldEventuallyReturnAWrongAnswer() throws InterruptedException {

		int counter = 0;
		for(int i=0; i<100; i++){
			if (!credit.verifyCreditAvailability()){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		fail("The verify credit availability should eventually return a wrong answer (false)");
	}

	@Test
	public void verifyCreditShouldEventuallyReturnACorrectAnswer() throws InterruptedException {

		int counter = 0;
		for(int i=0; i<100; i++){
			if (credit.verifyCreditAvailability()){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		fail("The verify credit availability should eventually return a correct answer (true)");
	}

	@Test
	public void verifyPasswordShouldEventuallyReturnWrongAnswer() {
		int counter = 0;
		
		for(int i=0; i<100; i++){
			if (!credit.verifyPassword()){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		fail("The verify password should eventually return a wrong answer (false)");
	}

	@Test
	public void verifyPasswordShouldEventuallyReturnCorrectAnswer() {
		int counter = 0;
		
		for(int i=0; i<100; i++){
			if (credit.verifyPassword()){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		fail("The verify password should eventually return a correct answer (true)");
	}

}
