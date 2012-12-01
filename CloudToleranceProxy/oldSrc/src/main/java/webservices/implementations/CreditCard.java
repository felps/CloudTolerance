package webservices.implementations;

import javax.jws.WebMethod;
import javax.jws.WebService;

import proxy.GenericProxy;

@WebService
public class CreditCard implements webservices.interfaces.CreditCard {

	public GenericProxy proxy;
	
	@WebMethod
	public boolean issuePayment() {
		
		Object[] response = null;
		try {
			response = proxy.invokeMethod("issuePayment", null, null);
			return (Boolean) response[0];
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@WebMethod
	public boolean verifyCreditAvailability() {

		Object[] response;
		try {
			response = proxy.invokeMethod("verifyCreditAvailability", null, null);
			return (Boolean) response[0];
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	@WebMethod
	public boolean verifyPassword() {
	
		Object[] response;
		try {
			response = proxy.invokeMethod("verifyPassword", null, null);
			return (Boolean) response[0];
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
