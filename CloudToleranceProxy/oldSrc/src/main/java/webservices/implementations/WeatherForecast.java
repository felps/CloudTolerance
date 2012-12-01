package webservices.implementations;

import javax.jws.WebMethod;

import proxy.GenericProxy;

public class WeatherForecast implements webservices.interfaces.WeatherForecast {

	public GenericProxy proxy;
	
	@WebMethod
	public int getTemperatureForecast(String location) {

		Object[] response;
		try {
			response = proxy.invokeMethod("getTemperatureForecast", location, null);
			
			Object returnedValue = response[0];
			int returnedIntegerValue = ((Integer) returnedValue);
			
			return returnedIntegerValue;

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
		return -274;
	}

}
