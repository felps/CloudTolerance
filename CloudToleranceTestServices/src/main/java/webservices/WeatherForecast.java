package webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style=Style.RPC)
public class WeatherForecast extends FaultyWebService {

	public WeatherForecast(double failStop, double faultyResponse) {
		this.setFailstopProbability(failStop);
		this.setFaultyResponseProbability(faultyResponse);
	}

	public static void main(String[] args) {
		if (args.length != 2)
			System.out
					.println("USAGE: <WSname> <WS fail-stop probability> <WS faulty response probability>");

		double failStop = Double.parseDouble(args[0]);
		double faultyResponse = Double.parseDouble(args[1]);

		WeatherForecast ws = new WeatherForecast(failStop, faultyResponse);

		Endpoint.publish("http://0.0.0.0:2402/weather", ws);

		System.out.println("Web service up and Running! \n"
				+ "Fail-stop probability:       " + failStop + "\n"
				+ "Faulty Response probability: " + faultyResponse);
	}

	@WebMethod
	public int getTemperatureForecast(String location) {
		emulateFailStopFailures();
		if (willReturnWrongAnswer()) {
			return getRandomIntegerAnswer();
		}

		if (location.equalsIgnoreCase("BSB"))
			return 25;
		else
			return 30;
	}
}
