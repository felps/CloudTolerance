package proxy;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import proxy.choreography.BPMNTask;
import proxy.techniques.Retry;
import proxy.webservice.handlers.WsInvoker;

@WebService
public class ProxyEndpoint {

	private WsInvoker nextProxyInvoker;
	private ProxyEndpoint nextProxy;
	public Proxy myProxy;
	public String wsMethodName;
	public String nextProxyUrl;
	private Endpoint ep;
	private String publishURL;
	private ArrayList<String> myProvidingWebServices;
	private ArrayList<ProxyEndpoint> otherProxies;

	public String getPublishURL() {
		return publishURL;
	}
	
	public void setPublishURL(String url) {
		publishURL = url;
	}

	public ProxyEndpoint() {
		otherProxies = new ArrayList<ProxyEndpoint>();

		myProvidingWebServices = new ArrayList<String>();
	}

	public ProxyEndpoint(BPMNTask task) {
		otherProxies = new ArrayList<ProxyEndpoint>();
		myProvidingWebServices = new ArrayList<String>();
		wsMethodName = task.getMethodName();
		nextProxyUrl = task.getNextLink();
		myProxy = new Proxy();
		for (String wsdlFile : task.getProvidingServicesWsdlList()) {
			myProxy.addWebService(wsdlFile);
		}
		ep = Endpoint.create(this);
	}


	public static void main(String[] args) {
		if (args.length < 4){
			System.out
			.println("Usage: java -jar ProxyEndpoint <proxy publishable endpoint> <next proxy endpoint> <service method name> <services' WSDL url>");
			System.exit(0);
		}

		ProxyEndpoint proxy = new ProxyEndpoint();

		proxy.startWS(args);

		System.out.println("Done! Ready to warm up!");
	}

	private void startWS(String[] args) {

		System.out.println("Next proxy: "+args[1]);
		this.nextProxyUrl = args[1];
		System.out.println("Service Method: "+args[2]);
		this.wsMethodName = args[2];

		this.myProxy = new Proxy();
		for (int i = 3; i < args.length; i++){
			System.out.println("Adding WS at: " + args[i]);
			this.myProxy.addWebService(args[i]);

		}

		publishURL = args[0];
		System.out.println("Publishing Proxy at " + publishURL);
		this.publishWS(publishURL);
	}

	
	@WebMethod(exclude=true)
	public void publishWS(String publishURL) {
		this.ep = Endpoint.create(this);
		this.ep.publish(publishURL);
	}
	
	@WebMethod
	public void playRole(int parameter, int key) {

		int returnValue = 0;

		Object wsParameterValues = parameter;

		Object returnedValue = myProxy.invokeMethod(wsMethodName, wsParameterValues);
		if(returnedValue !=null) {
			returnValue = (Integer) returnedValue;
		} else return;

		informNextLink(returnValue, key);
	}

	private void informNextLink(int parameter, int key) {

		WsInvoker nextLink;
		nextLink = new WsInvoker(nextProxyUrl);

		Retry retry = new Retry(3);

		retry.setTimeout(500);

		retry.addAvailableInvoker(nextLink);
		retry.setCurrentWS(nextLink);

		Object result = retry.invokeMethod("playRole", parameter, key);
		if (result==null){
			System.out
					.println("Danger Will Robinson! Danger Will Robinson! Danger Will Robinson! Recovery is needed...");
		}
	}

	public void stopService(){
		ep.stop();
	}

	public String getNextProxyUrl() {
		return nextProxyUrl;
	}
	
	public void setNextProxyUrl(String newUrl) {
		nextProxyUrl = newUrl;
	}

	public ArrayList<ProxyEndpoint> getOtherProxies() {
		return otherProxies;
	}

	public ProxyEndpoint getOtherProxies(int index) {
		return otherProxies.get(index);
	}

	public void addOtherProxy(ProxyEndpoint newProxy) {
		otherProxies.add(newProxy);
	}

	public String getProvidingWebService(int index) {
		return myProvidingWebServices.get(index);
	}

	public void addProvidingWebService(String wsEndpoint) {
		myProvidingWebServices.add(wsEndpoint);
	}
}
