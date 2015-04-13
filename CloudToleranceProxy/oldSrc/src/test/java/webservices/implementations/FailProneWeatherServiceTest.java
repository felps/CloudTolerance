package webservices.implementations;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import proxy.GenericProxy;

public class FailProneWeatherServiceTest {

	private static WeatherForecast weather = new WeatherForecast();

	class RunWeatherService implements Runnable {

		public void run() {
			String[] args;
			args = new String[2];
			args[0] = "0.0"; // fail-stop probability
			args[1] = "0.20"; // faulty response probability

			realwebservices.WeatherForecast.main(args);
		}

	}

	@BeforeClass
	public static void setEnvironment() throws InterruptedException {

		FailProneWeatherServiceTest helper = new FailProneWeatherServiceTest();
		RunWeatherService weatherServiceInitiliazer = helper.new RunWeatherService();
		new Thread(weatherServiceInitiliazer).start();

		Thread.sleep(5000);

		System.out.println("Creating Web Service proxies");
		if (weather.proxy == null)
			weather.proxy = new GenericProxy();
		weather.proxy.addWebService("http://godzilla.ime.usp.br:2402/weather?wsdl",
				"realwebservices.GetTemperatureForecast");
		System.out.println("Done creating Web Service proxies");
	}

	@Test
	public void shouldEventuallyGetIncorrectValueForBsbInput() {
		
		int counter = 0;
		for(int i=0; i<100; i++){
			if (weather.getTemperatureForecast("BSB") != 25){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		fail("The getTemperatureForecast should eventually return a wrong answer (different from 25))");

	}

	@Test
	public void shouldEventuallyGetCorrectValueForBsbInput() {
		
		int counter = 0;
		for(int i=0; i<100; i++){
			if (weather.getTemperatureForecast("BSB") == 25){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		fail("The getTemperatureForecast should eventually return a wrong answer (different from 25))");

	}

	@Test
	public void shouldEventuallyGetIncorrectValueForPoaInput() {
		
		int counter = 0;
		for(int i=0; i<100; i++){
			if (weather.getTemperatureForecast("POA") != 30){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		fail("The getTemperatureForecast should eventually return a wrong answer (different from 30))");

	}

	@Test
	public void shouldEventuallyGetCorrectValueForPoaInput() {
		
		int counter = 0;
		for(int i=0; i<100; i++){
			if (weather.getTemperatureForecast("POA") == 30){
				System.out.println("Got it after " + counter + " tries.");
				return;
			}
			else counter++;
		}
		
		fail("The getTemperatureForecast should eventually return a wrong answer (different from 30))");

	}
}
