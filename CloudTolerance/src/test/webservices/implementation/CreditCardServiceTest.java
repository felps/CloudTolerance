package webservices.implementation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.GenericProxy;

import webservices.implementations.CreditCard;

public class CreditCardServiceTest {

	CreditCard credit = new CreditCard();
	
	@Before
	public void setUp() {
		if(credit.proxy == null)
			credit.proxy = new GenericProxy();
		credit.proxy.addWebService("http://127.0.0.1:2302/creditcard?wsdl", "webservices.IssuePayment");
		credit.proxy.addWebService("http://127.0.0.1:2302/creditcard?wsdl", "webservices.VerifyCreditAvailability");
		credit.proxy.addWebService("http://127.0.0.1:2302/creditcard?wsdl", "webservices.VerifyPassword");
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
