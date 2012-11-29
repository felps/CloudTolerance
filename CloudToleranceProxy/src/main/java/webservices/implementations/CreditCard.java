package webservices.implementations;

import javax.jws.WebMethod;
import javax.jws.WebService;

import proxy.GenericProxy;

@WebService
public class CreditCard implements webservices.interfaces.CreditCard {

	public GenericProxy proxy;
	
	@WebMethod
	public boolean issuePayment() {
		
		Object[] response = proxy.invokeMethod("issuePayment", null);
		return (Boolean) response[0];
		
	}

	@WebMethod
	public boolean verifyCreditAvailability() {

		Object[] response = proxy.invokeMethod("verifyCreditAvailability", null);
		return (Boolean) response[0];
	
	}

	@WebMethod
	public boolean verifyPassword() {
	
		Object[] response = proxy.invokeMethod("verifyPassword", null);
		return (Boolean) response[0];
	
	}

}
