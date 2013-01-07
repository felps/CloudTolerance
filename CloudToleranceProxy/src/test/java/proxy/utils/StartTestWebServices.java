package proxy.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.sun.xml.internal.ws.api.EndpointAddress;

public class StartTestWebServices {
	public static final String WEATHER_WSDL = "http://127.0.0.1:2402/weather?wsdl";
	public static final String CREDITCARD_WSDL = "http://127.0.0.1:2302/creditcard?wsdl";

	private static ArrayList<Thread> webServices = new ArrayList<Thread>();
	
	class RunCreditService implements Runnable {

		String[] args;

		public RunCreditService(double failStopProbability,
				double faultyResponseProbability) {
			args = new String[2];
			args[0] = "" + failStopProbability; // fail-stop probability
			args[1] = "" + faultyResponseProbability; // faulty response
														// probability
		}

		public void run() {
			realwebservices.CreditCard.main(args);
		}

	}

	class RunWeatherService implements Runnable {

		String[] args;

		public RunWeatherService(double failStopProbability,
				double faultyResponseProbability) {
			args = new String[2];
			args[0] = "" + failStopProbability; // fail-stop probability
			args[1] = "" + faultyResponseProbability; // faulty response
														// probability
		}

		public void run() {

			realwebservices.WeatherForecast.main(args);
		}

	}

	public static void raiseCreditAndWeatherServices(
			double failStopProbability, double faultyResponseProbability)
			throws InterruptedException {
		raiseCreditService(failStopProbability, faultyResponseProbability);
		raiseWeatherService(failStopProbability, faultyResponseProbability);
	}

	public static void raiseCreditService(double failStopProbability,
			double faultyResponseProbability) throws InterruptedException {
		if (checkForExistingService(CREDITCARD_WSDL))
			return;

		StartTestWebServices helper = new StartTestWebServices();

		RunCreditService creditServiceInitiliazer = helper.new RunCreditService(
				failStopProbability, faultyResponseProbability);
		Thread thread = new Thread(creditServiceInitiliazer);
		webServices.add(thread);
		thread.start();
		
	}

	public static void raiseWeatherService(double failStopProbability,
			double faultyResponseProbability) throws InterruptedException {
		if (checkForExistingService(WEATHER_WSDL))
			return;

		StartTestWebServices helper = new StartTestWebServices();

		RunWeatherService weatherServiceInitiliazer = helper.new RunWeatherService(
				failStopProbability, faultyResponseProbability);
		Thread thread = new Thread(weatherServiceInitiliazer);
		webServices.add(thread);
		thread.start();

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
	
	public static void killAllServices(){
//		for (Thread thread : webServices) {
//			thread.stop();
//			thread.destroy();
//			webServices.remove(thread);
//		}
	}
}
