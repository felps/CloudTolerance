package proxy;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import proxy.webservice.handlers.WsInvoker;

@WebService
public class ProxyEndpoint {

	private Proxy myProxy;
	private WsInvoker nextProxy;
	private String wsMethodName;
	private String nextProxyUrl;

	public static void main(String[] args) {
		if (args.length < 4){
			System.out
					.println("Usage: java -jar ProxyEndpoint <proxy publishable endpoint> <next proxy endpoint> <service method name> <services' WSDL url>");
			System.exit(0);
		}

		ProxyEndpoint proxy = new ProxyEndpoint();

		System.out.println("Next proxy: "+args[1]);
		proxy.nextProxyUrl = args[1];
		System.out.println("Service Method: "+args[2]);
		proxy.wsMethodName = args[2];

		proxy.myProxy = new Proxy();
		for (int i = 3; i < args.length; i++){
			System.out.println("Adding WS at: " + args[i]);
			proxy.myProxy.addWebService(args[i]);
			
		}

		System.out.println("Publishing Proxy at " + args[0]);
		Endpoint.publish(args[0], proxy);

		System.out.println("Done! Ready to warm up!");
	}
	
	@WebMethod
	@Oneway
	public void playRole(int parameter, int key) {
		
		int returnValue = 0;

		Object wsParameterValues = parameter;

		returnValue = (Integer) myProxy.invokeMethod(wsMethodName, wsParameterValues);

		informNextLink(returnValue, key);
	}

	private void informNextLink(int parameter, int key) {
		
		if (nextProxy == null)
			nextProxy = new WsInvoker(nextProxyUrl);
		
		nextProxy.invokeWebMethod("playRole", parameter, key);
	}

}
