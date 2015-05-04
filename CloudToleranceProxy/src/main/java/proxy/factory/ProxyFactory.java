package proxy.factory;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import proxy.ChoreographyActor;
import proxy.ProxyEndpoint;

public class ProxyFactory {

	public ChoreographyActor createProxy(File configFile) throws IOException {
		Scanner fileReader = new Scanner(configFile);
		if (!configFile.exists() || !configFile.canRead()) {
			fileReader.close();
			throw new IOException("Cannot read configuration file");
		}
		
		if (!fileReader.hasNextLine()) {
			fileReader.close();
			throw new IOException("Empty configuration file");
		}

		ChoreographyActor proxy = generateProxyEndpointFromFile(fileReader);
		while(fileReader.hasNextLine()){
			proxy.addOtherProxy(generateProxyEndpointFromFile(fileReader));
		}
		fileReader.close();
		return proxy;
	}

	private ChoreographyActor generateProxyEndpointFromFile(Scanner fileReader) {
		ChoreographyActor proxy = new ProxyEndpoint();

		String line = fileReader.nextLine();
		while (fileReader.hasNextLine() && !line.contentEquals("Proxy:")) {
			String label;
			if (line.contains(": ")) {
				String token;
				label = line.split(": ")[0].trim();
				token = line.split(": ")[1].trim();

				if (label.contains("MyURL")) {
					proxy.setPublishURL(token.trim());
				}
				else 
				if (label.contains("NextProxy")) {
					proxy.setNextProxyUrl(token);
				}
				else
				if (line.contains("WS: ")) {
					proxy.addProvidingWebService(token);
				}
			}
			line = fileReader.nextLine();
		}
		
		return proxy;
	}
}