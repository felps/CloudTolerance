package webservices.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public interface CreditCard {

	@WebMethod
	public boolean issuePayment() ;

	@WebMethod
	public boolean verifyCreditAvailability() ;
	
	@WebMethod
	public boolean verifyPassword();

}
