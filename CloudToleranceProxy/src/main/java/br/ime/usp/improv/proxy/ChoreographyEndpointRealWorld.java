package br.ime.usp.improv.proxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import br.ime.usp.improv.proxy.utils.Result;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvoker;

@WebService
public class ChoreographyEndpointRealWorld {

	protected List<Result> choreographyResults;
	protected static Integer nextAvailableKey = 0;

	public Proxy myProxy;
	public String wsMethodName;
	public String nextProxyUrl;
	public WsInvoker nextProxy;

	public ChoreographyEndpointRealWorld() {
		choreographyResults = new ArrayList<Result>();
	}

	public static void main(String[] args) {
		if (args.length < 4) {
			System.out
					.println("Usage: java -jar ChoreographyEndpoint <choreography publishable endpoint> <next proxy endpoint> <service method name> <services wsdl url>");
			System.exit(0);
		}

		System.out.println("Next proxy: " + args[1]);
		String nextProxyUrl = args[1];

		System.out.println("Service Method: " + args[2]);
		String wsMethodName = args[2];

		System.out.println("Publishing Proxy at " + args[0]);
		String publish = args[0];

		ChoreographyEndpointRealWorld chor = new ChoreographyEndpointRealWorld();
		for (String string : Arrays.copyOfRange(args, 3, args.length)) {
			System.out.println("WSDL: "+ string);
		}
		
		chor.setUpWebService(nextProxyUrl, wsMethodName, Arrays.copyOfRange(args, 3, args.length));

		Endpoint.publish(publish, chor);
		System.out.println("Done! Ready to warm up!");
		
	}

	private void setUpWebService(String nextProxyWSDL, String wsMethodName,
			String... webServices) {

		this.nextProxyUrl = nextProxyWSDL;

		this.wsMethodName = wsMethodName;

		myProxy = new Proxy();
		
		myProxy.setRealWorldEffects(true);
		
		for (String webServiceEndpoint : webServices) {
			System.out.println("Adding WS at: " + webServiceEndpoint);
			myProxy.addWebService(webServiceEndpoint);
		}

	}

	@WebMethod
	public int startChoreograph(int parameter) {
		System.out.println("Choreography Started...");
		if (nextProxy == null)
			nextProxy = new WsInvoker(nextProxyUrl);

		int currentKey;

		synchronized (nextAvailableKey) {
			currentKey = nextAvailableKey++;
			choreographyResults.add(new Result());
		}

		System.out.println("Informing next link...");
		informNextLink(parameter, currentKey);

		return getResponse(currentKey);
	}

	@WebMethod
	@Oneway
	public void playRole(int parameter, int key) {

		System.out.println("Performing my role...");
		Object returnValue = null;

		Object wsParameterValues = parameter;

		returnValue = myProxy.invokeMethod(wsMethodName, wsParameterValues);

		choreographyResults.get(key).setResultValue(returnValue);
	}

	private int getResponse(int key) {
		Result result = choreographyResults.get(key);
		try {
			return (Integer) result.getResultValue();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	protected void informNextLink(int parameter, int key) {
		nextProxy.invokeWebMethod("playRole", parameter, key);
	}

}
