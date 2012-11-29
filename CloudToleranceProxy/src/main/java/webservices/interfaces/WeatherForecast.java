package webservices.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public interface WeatherForecast {

	@WebMethod
	public int getTemperatureForecast(String location);

}
