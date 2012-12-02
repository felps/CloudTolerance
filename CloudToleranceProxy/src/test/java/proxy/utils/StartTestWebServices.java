package proxy.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.cxf.endpoint.EndpointImpl;

import com.sun.xml.internal.ws.api.EndpointAddress;

public class StartTestWebServices {

	class RunCreditService implements Runnable {

		public void run() {
			String[] args;
			args = new String[2];
			args[0] = "0.0"; // fail-stop probability
			args[1] = "0.0"; // faulty response probability

			realwebservices.CreditCard.main(args);
		}

	}

	class RunWeatherService implements Runnable {

		public void run() {
			String[] args;
			args = new String[2];
			args[0] = "0.0"; // fail-stop probability
			args[1] = "0.0"; // faulty response probability

			realwebservices.WeatherForecast.main(args);
		}

	}

	public static void raiseCreditAndWeatherServices()
			throws InterruptedException {
		raiseCreditService();
		raiseWeatherService();
	}

	public static void raiseCreditService() throws InterruptedException {
		if (checkForExistingService("http://127.0.0.1:2302/creditcard?wsdl"))
			return;

		StartTestWebServices helper = new StartTestWebServices();

		RunCreditService creditServiceInitiliazer = helper.new RunCreditService();
		new Thread(creditServiceInitiliazer).start();
	}

	public static void raiseWeatherService() throws InterruptedException {
		if (checkForExistingService("http://127.0.0.1:2402/weather?wsdl"))
			return;
		
		StartTestWebServices helper = new StartTestWebServices();

		RunWeatherService weatherServiceInitiliazer = helper.new RunWeatherService();
		new Thread(weatherServiceInitiliazer).start();

	}

	private static boolean checkForExistingService(String endpoint) {
		try {
			EndpointAddress ep = new EndpointAddress(endpoint);
			ep.openConnection().getContent();
			System.out.println("Service is running already");
			return true;
		} catch (IOException e) {
			System.out.println("No service is running");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return false;
	}
}
