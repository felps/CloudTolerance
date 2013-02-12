package proxy;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import proxy.utils.Result;
import proxy.webservice.handlers.WsInvoker;

@WebService
public class ChoreographyEndpoint {

	protected List<Result> choreographyResults;
	protected static Integer nextAvailableKey = 0;

	public Proxy myProxy;
	public String wsMethodName;
	public String nextProxyUrl;
	public WsInvoker nextProxy;

	public static void main(String[] args) {
		if (args.length < 4) {
			System.out
					.println("Usage: java -jar ChoreographyEndpoint <choreography publishable endpoint> <next proxy endpoint> <service method name> <services wsdl url>");
			System.exit(0);
		}

		ChoreographyEndpoint chor = new ChoreographyEndpoint();

		chor.nextProxyUrl = args[1];
		chor.wsMethodName = args[2];

		chor.myProxy = new Proxy();
		for (int i = 3; i < args.length; i++)
			chor.myProxy.addWebService(args[i]);

		Endpoint.publish(args[0], chor);

	}

	@WebMethod
	public int startChoreograph(int parameter) {
		System.out.println("Choreography Started...");
		if (nextProxy == null)
			nextProxy = new WsInvoker(nextProxyUrl);

		int currentKey;

		synchronized (nextAvailableKey) {
			currentKey = nextAvailableKey++;
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

	private void informNextLink(int parameter, int key) {
		nextProxy.invokeWebMethod("playRole", parameter, key);
	}

}
