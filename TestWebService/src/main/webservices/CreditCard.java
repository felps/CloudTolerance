package webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class CreditCard extends FaultyWebService {

	public static void main(String[] args) {
		raise(args);
	}

	@WebMethod
	public boolean issuePayment() {
		emulateFailStopFailures();
		if (willReturnWrongAnswer()) {
			return getRandomBooleanAnswer();
		}

		return true;

	}

	@WebMethod
	public boolean verifyCreditAvailability() {
		emulateFailStopFailures();
		if (willReturnWrongAnswer()) {
			return getRandomBooleanAnswer();
		}
		return true;

	}

	@WebMethod
	public boolean verifyPassword() {
		emulateFailStopFailures();
		if (willReturnWrongAnswer()) {
			return getRandomBooleanAnswer();
		}

		return true;
	}
}
