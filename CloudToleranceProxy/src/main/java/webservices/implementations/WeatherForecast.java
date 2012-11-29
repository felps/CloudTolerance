package webservices.implementations;

import javax.jws.WebMethod;

import proxy.GenericProxy;

public class WeatherForecast implements webservices.interfaces.WeatherForecast {

	public GenericProxy proxy;
	
	@WebMethod
	public int getTemperatureForecast(String location) {

		Object[] response = proxy.invokeMethod("getTemperatureForecast", location);
		
		Object returnedValue = response[0];
		int returnedIntegerValue = ((Integer) returnedValue);
		
		return returnedIntegerValue;
		
	}

}
