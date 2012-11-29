package realwebservices;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class CreditCard extends FaultyWebService {

	public CreditCard(double failStop, double faultyResponse) {
		this.setFailstopProbability(failStop);
		this.setFaultyResponseProbability(faultyResponse);
	}

	public static void main(String[] args) {
		if (args.length != 2)
			System.out
					.println("USAGE: <WSname> <WS fail-stop probability> <WS faulty response probability>");

		double failStop = Double.parseDouble(args[0]);
		double faultyResponse = Double.parseDouble(args[1]);

		CreditCard ws = new CreditCard(failStop, faultyResponse);

		Endpoint.publish("http://0.0.0.0:2302/creditcard", ws);

		System.out.println("Web service up and Running! \n"
				+ "Fail-stop probability:       " + failStop + "\n"
				+ "Faulty Response probability: " + faultyResponse);
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
