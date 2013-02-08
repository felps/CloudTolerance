package realwebservices;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;

import webservices.LinearService1;

import com.sun.xml.internal.ws.api.EndpointAddress;

public class LinearService1TestMainRaiseService {

	@BeforeClass
	public static void BeforeClass() throws InterruptedException {
		String[] args = new String[3];
		args[0] = 0.0 + "";
		args[1] = 0.0 + "";
		args[2] = "http://0.0.0.0:2304/linear";
		
		LinearService1.main(args);
//		Thread.sleep(20000);
	}

	@Test
	public void checkServiceAvailability() throws URISyntaxException, IOException {
		EndpointAddress ep = new EndpointAddress(
				"http://localhost:2304/linear?wsdl");
		ep.openConnection().getContent();
		System.out.println("Service is running already");
	}

	
}
