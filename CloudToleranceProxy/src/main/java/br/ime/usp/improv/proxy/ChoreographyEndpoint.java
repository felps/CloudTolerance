package br.ime.usp.improv.proxy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import br.ime.usp.improv.proxy.choreography.BPMNTask;
import br.ime.usp.improv.proxy.utils.Result;

@WebService
@SOAPBinding(style = Style.RPC)

public class ChoreographyEndpoint extends ChoreographyActor {

	protected HashMap<Integer, Result> choreographyResults;
	protected static Integer nextAvailableKey = 0;

	public ChoreographyEndpoint() {
		super();
		choreographyResults = new HashMap<Integer, Result>();
	}

	public ChoreographyEndpoint(BPMNTask task) {
		super(task);
		choreographyResults = new HashMap<Integer, Result>();
		
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

		ChoreographyActor chor = new ChoreographyEndpoint();
		for (String string : Arrays.copyOfRange(args, 3, args.length)) {
			System.out.println("WSDL: "+ string);
		}
		
		chor.setUpWebService(nextProxyUrl, wsMethodName, Arrays.copyOfRange(args, 3, args.length));

		Endpoint.publish(publish, chor);
		System.out.println("Done! Ready to warm up!");
		
	}

	@WebMethod
	public int startChoreography(int parameter) {
		//System.out.println("Choreography Started...");
		int currentKey;

		synchronized (nextAvailableKey) {
			currentKey = nextAvailableKey++;
			choreographyResults.put(currentKey, new Result());
		}

		//System.out.println("Informing next link...");
		try {
			informNextLink(parameter, currentKey);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return getResponse(currentKey);
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

	@WebMethod
	public int playRole(int parameter, int key) {
		
		System.out.println("Actor: " + publishURL + ". Performing my role as proxy...");
		Object returnValue = null;
	
		Object wsParameterValues = parameter;
	
		returnValue = myProxy.invokeMethod(wsMethodName, wsParameterValues);
	
		choreographyResults.get(key).setResultValue(returnValue);
//		choreographyResults.get(key).setResultValue(parameter);
		
		return 1;
	}

}
