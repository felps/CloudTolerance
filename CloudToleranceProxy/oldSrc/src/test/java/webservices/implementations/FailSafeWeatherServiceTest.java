package webservices.implementations;

import static org.junit.Assert.*;

import realwebservices.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webservices.implementations.*;
import webservices.implementations.CreditCard;

import proxy.GenericProxy;

public class FailSafeWeatherServiceTest {

	private static WeatherForecast weather = new WeatherForecast();

	class RunWeatherService implements Runnable {

		public void run() {
			String[] args;
			args = new String[2];
			args[0] = "0.0"; // fail-stop probability
			args[1] = "0.0"; // faulty response probability

			realwebservices.WeatherForecast.main(args);
		}

	}

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {

		FailSafeWeatherServiceTest helper = new FailSafeWeatherServiceTest();
		RunWeatherService weatherServiceInitiliazer = helper.new RunWeatherService();
		new Thread(weatherServiceInitiliazer).start();

		Thread.sleep(5000);

		System.out.println("Creating Web Service proxies");
		if (weather.proxy == null)
			weather.proxy = new GenericProxy();
		weather.proxy.addWebService("http://127.0.0.1:2402/weather?wsdl",
				"realwebservices.GetTemperatureForecast");
		System.out.println("Done creating Web Service proxies");
	}

	@Test
	public void shouldGetCorrectValueForBsbInput() {
		int returnedValue = weather.getTemperatureForecast("BSB");
		assertEquals(
				"The getTemperatureForecast should always return 25 for parameter BSB",
				25, returnedValue);
	}

	@Test
	public void shouldGetCorrectValueForOtherInputs() {
		int returnedValue = weather.getTemperatureForecast("POA");
		assertEquals(
				"The issue payment should always return 30 for any parameter different from BSB",
				30, returnedValue);
	}

}
