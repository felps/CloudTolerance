package proxy;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.xml.ws.Endpoint;

public class MultipleMessagesChoreography extends ChoreographyEndpoint {
	
	
	public static int messagesExchanged;
	private String myURL;

	public MultipleMessagesChoreography() {
		super();
	}

	public static void main(String[] args) {
		if (args.length < 5) {
			System.out
					.println("Usage: java -jar ChoreographyEndpoint <choreography publishable endpoint> <next proxy endpoint> <messages exchanged> <service method name> <services wsdl url>");
			System.exit(0);
		}

		messagesExchanged = Integer.parseInt(args[2]);
		System.out.println("[Multiple at "+ args[0] + "]Messages to be exchanged: " + messagesExchanged);

		MultipleMessagesChoreography chor = new MultipleMessagesChoreography();
		chor.myURL = args[0];

		System.out.println("[Multiple at "+ chor.myURL + "]Next proxy: " + args[1]);
		chor.nextProxyUrl = args[1];
		System.out.println("[Multiple at "+ chor.myURL + "]Service Method: " + args[3]);
		chor.wsMethodName = args[3];

		chor.myProxy = new Proxy();
		for (int i = 4; i < args.length; i++){
			System.out.println("[Multiple at "+ chor.myURL + "]Adding WS at: " + args[i]);
			chor.myProxy.addWebService(args[i]);
		}

		System.out.println("[Multiple at "+ chor.myURL + "]Publishing Proxy at " + args[0]);
		Endpoint.publish(args[0], chor);
		
		System.out.println("Done! Ready to warm up!");
		

	}

	@WebMethod
	@Oneway
	@Override
	public void playRole(int parameter, int key) {

		Object returnValue = null;
		int integerReturnedValue; 

		Object wsParameterValues = parameter;

		System.out.println("[Multiple at "+ myURL + "] Invoking method " + wsMethodName + " with integer parameter: " + parameter);
		returnValue = myProxy.invokeMethod(wsMethodName, wsParameterValues);

		integerReturnedValue = (Integer) returnValue;
		
		if( integerReturnedValue >= messagesExchanged){
			
			System.out.println("[Multiple at "+ myURL + "] " + integerReturnedValue + "out of " + messagesExchanged + " expected messages..."+'\n'+"Message exchange amount reached!");
			choreographyResults.get(key).setResultValue(returnValue);
		}
		else{
			System.out.println("[Multiple at "+ myURL + "] Up to now, " + integerReturnedValue + " messages exchanged");
			System.out.println("Invoking proxy again, parameter " + integerReturnedValue);
			try {
				informNextLink(integerReturnedValue, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
