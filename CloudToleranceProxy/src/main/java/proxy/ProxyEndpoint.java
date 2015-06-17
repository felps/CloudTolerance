package proxy;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import proxy.choreography.BPMNTask;

@WebService
@SOAPBinding(style = Style.RPC)

public class ProxyEndpoint extends ChoreographyActor{

	public ProxyEndpoint() {
		super();
		otherProxies = new ArrayList<ChoreographyActor>();

		myProvidingWebServices = new ArrayList<String>();
	}

	public ProxyEndpoint(BPMNTask task) {
		super(task);
		otherProxies = new ArrayList<ChoreographyActor>();
		
	}

	public static void main(String[] args) {
		if (args.length < 4) {
			System.out
					.println("Usage: java -jar ProxyEndpoint <proxy publishable endpoint> <next proxy endpoint> <service method name> <services' WSDL url>");
			System.exit(0);
		}

		ProxyEndpoint proxy = new ProxyEndpoint();

		proxy.startWS(args);

		System.out.println("Done! Ready to warm up!");
	}

	private void startWS(String[] args) {

		System.out.println("Next proxy: " + args[1]);
		this.nextProxyUrl = args[1];
		System.out.println("Service Method: " + args[2]);
		this.wsMethodName = args[2];

		this.myProxy = new Proxy();
		myProxy.getCurrentTechnique().setTimeout(1000);
		for (int i = 3; i < args.length; i++) {
			System.out.println("Adding WS at: " + args[i]);
			this.myProxy.addWebService(args[i]);

		}

		publishURL = args[0];
		System.out.println("Publishing Proxy at " + publishURL);
		this.publishWS(publishURL);
	}

	@WebMethod
	public int playRole(int parameter, int key) {

		System.out.println("Actor: " + publishURL + ". Performing my role as proxy...");
		System.out.println("Invoking "+wsMethodName);
		int returnValue = 0;

		Object wsParameterValues = parameter;

		Object returnedValue = myProxy.invokeMethod(wsMethodName, wsParameterValues);
		if (returnedValue != null) {
			returnValue = (Integer) returnedValue;
		} 

		try {
			informNextLink(returnValue, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 1;
	}

}
