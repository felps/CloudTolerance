package webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class WeatherForecast extends FaultyWebService {

	public static void main(String[] args) {
		raise(args);
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
