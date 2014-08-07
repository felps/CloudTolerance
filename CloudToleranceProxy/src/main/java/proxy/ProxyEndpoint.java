package proxy;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import proxy.webservice.handlers.WsInvoker;

@WebService
public class ProxyEndpoint {

	private WsInvoker nextProxyInvoker;
	private ProxyEndpoint nextProxy;
	public Proxy myProxy;
	public String wsMethodName;
	public String nextProxyUrl;
	private Endpoint ep;

	public static void main(String[] args) {
		if (args.length < 4){
			System.out
					.println("Usage: java -jar ProxyEndpoint <proxy publishable endpoint> <next proxy endpoint> <service method name> <services' WSDL url>");
			System.exit(0);
		}

		ProxyEndpoint proxy = new ProxyEndpoint();

		proxy.publishWS(args);

		System.out.println("Done! Ready to warm up!");
	}

	public void publishWS(String[] args) {
		
		System.out.println("Next proxy: "+args[1]);
		this.nextProxyUrl = args[1];
		System.out.println("Service Method: "+args[2]);
		this.wsMethodName = args[2];

		this.myProxy = new Proxy();
		for (int i = 3; i < args.length; i++){
			System.out.println("Adding WS at: " + args[i]);
			this.myProxy.addWebService(args[i]);
			
		}

		System.out.println("Publishing Proxy at " + args[0]);
		this.ep = Endpoint.create(this);
		this.ep.publish(args[0]);
	}
	
	@WebMethod
	@Oneway
	public void playRole(int parameter, int key) {
		
		int returnValue = 0;

		Object wsParameterValues = parameter;

		Object returnedValue = myProxy.invokeMethod(wsMethodName, wsParameterValues);
		if(returnedValue !=null)
			returnValue = (Integer) returnedValue;
		else return;
		
		informNextLink(returnValue, key);
	}

	private void informNextLink(int parameter, int key) {
		if (nextProxyInvoker == null)
			nextProxyInvoker = new WsInvoker(nextProxyUrl);
		
		nextProxyInvoker.invokeWebMethod("playRole", parameter, key);
	}
	
	public void stopService(){
		ep.stop();
	}

}
