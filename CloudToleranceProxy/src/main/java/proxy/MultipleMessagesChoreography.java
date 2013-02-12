package proxy;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.xml.ws.Endpoint;

public class MultipleMessagesChoreography extends ChoreographyEndpoint {
	
	
	private int messagesExchanged;

	public MultipleMessagesChoreography(int messagesExchanged) {
		super();
		messagesExchanged = this.messagesExchanged;
	}

	public static void main(String[] args) {
		if (args.length < 5) {
			System.out
					.println("Usage: java -jar ChoreographyEndpoint <choreography publishable endpoint> <next proxy endpoint> <messages exchanged> <service method name> <services wsdl url>");
			System.exit(0);
		}

		MultipleMessagesChoreography chor = new MultipleMessagesChoreography(Integer.parseInt(args[2]));

		System.out.println("Next proxy: " + args[1]);
		chor.nextProxyUrl = args[1];
		System.out.println("Messages to be exchanged: " + args[2]);
		System.out.println("Service Method: " + args[3]);
		chor.wsMethodName = args[3];

		chor.myProxy = new Proxy();
		for (int i = 4; i < args.length; i++){
			System.out.println("Adding WS at: " + args[i]);
			chor.myProxy.addWebService(args[i]);
		}

		System.out.println("Publishing Proxy at " + args[0]);
		Endpoint.publish(args[0], chor);
		
		System.out.println("Done! Ready to warm up!");
		

	}

	@WebMethod
	@Oneway
	@Override
	public void playRole(int parameter, int key) {

		Object returnValue = null;

		Object wsParameterValues = parameter;

		returnValue = myProxy.invokeMethod(wsMethodName, wsParameterValues);

		if(key >= messagesExchanged)
			choreographyResults.get(0).setResultValue(returnValue);
		else
			startChoreograph((Integer) returnValue);
	}
}
