package webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class LinearService1 extends FaultyWebService {

	public LinearService1(double failStop, double faultyResponse) {
		this.setFailstopProbability(failStop);
		this.setFaultyResponseProbability(faultyResponse);
	}

	public static void main(String[] args) {
		if (args.length != 3){
			System.out
					.println("USAGE: <WSname> <WS fail-stop probability> <WS faulty response probability> <endpoint>");
			System.exit(0);
		}

		double failStop = Double.parseDouble(args[0]);
		double faultyResponse = Double.parseDouble(args[1]);

		LinearService1 ws = new LinearService1(failStop, faultyResponse);

		Endpoint.publish(args[2], ws);

		System.out.println("Web service up and Running! \n"
				+ "Fail-stop probability:       " + failStop + "\n"
				+ "Faulty Response probability: " + faultyResponse);
	}

	@WebMethod
	public int addOne(int number) {
		emulateFailStopFailures();
		if (willReturnWrongAnswer()) {
			return getRandomIntegerAnswer();
		}

		return number + 1;

	}
}
