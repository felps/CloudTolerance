package proxy.factory;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.Proxy;
import proxy.ProxyEndpoint;

public class ProxyFactoryTest {

	private static File	configFile	= new File("inputConfigTest.txt");

	@BeforeClass
	public static void beforeClass() {
		try {
			configFile.createNewFile();
			configFile.setReadable(true);

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(configFile.getName())));

			String myConfig = 	"MyURL: " + "http://localhost:3301/proxy1" + '\n' 
								+ "NextProxy: " + "http://localhost:3302/proxy2" + '\n'
								+ "WS:   http://localhost:3301/ws11" + '\n' 
								+ "WS:   http://localhost:3301/ws12" + '\n' 
								+ "WS:   http://localhost:3301/ws13" + "\n\n";;

//			System.out.println(myConfig);
			out.print(myConfig);

			String otherProxy;
			for (int i = 2; i < 6; i++) {
				otherProxy = "Proxy:\n" + "MyURL: http://localhost:330" + i + "/proxy" + i + '\n'
						+ "NextProxy: http://localhost:330" + (i + 1) + "/proxy" + (i + 1) + '\n'
						+ "WS:   http://localhost:330" + i + "/ws" + i + "1" + '\n' 
						+ "WS:   http://localhost:330" + i + "/ws" + i + "2" + '\n' 
						+ "WS:   http://localhost:330" + i + "/ws" + i + "3" + "\n\n";
				out.println(otherProxy);
//				System.out.println(otherProxy);
			}

			out.close();

		} catch (IOException e) {
			System.out.println("Exception caught: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterClass() {
		configFile.delete();
	}

	@Test
	public void thisTestMustCreateAConfigurationFile() throws FileNotFoundException {
		assertTrue(configFile.exists());
		assertTrue(configFile.canRead());

	}

	@Test
	public void shouldReadTheProxyInformationFromConfigurationFile() throws IOException {
		ProxyFactory factory = new ProxyFactory();

		ProxyEndpoint proxy = factory.createProxy(configFile);

		assertEquals("http://localhost:3301/proxy1", proxy.getPublishURL());
		assertEquals("http://localhost:3302/proxy2", proxy.getNextProxyUrl());
		for (int i = 0; i < 2; i++) {
			assertEquals("http://localhost:3301/ws1" + (i+1), proxy.getProvidingWebService(i));
		}

	}

	@Test
	public void shouldReadAllTheOtherProxiesUrlFromConfigurationFile() throws IOException {
		ProxyFactory factory = new ProxyFactory();

		ProxyEndpoint proxy = factory.createProxy(configFile);

		assertEquals(4, proxy.getOtherProxies().size());
		
		for (int i = 0; i < proxy.getOtherProxies().size(); i++) {
			assertEquals("http://localhost:330" + (i+2) + "/proxy" + (i+2), proxy.getOtherProxies(i).getPublishURL());
		}

	}

}
