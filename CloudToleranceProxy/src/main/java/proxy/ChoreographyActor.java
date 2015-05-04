package proxy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.xml.ws.Endpoint;

import proxy.choreography.BPMNTask;
import proxy.techniques.Retry;
import proxy.webservice.handlers.WsInvoker;

import com.sun.xml.internal.ws.api.EndpointAddress;

public abstract class ChoreographyActor {

	protected ArrayList<ChoreographyActor>	otherProxies;

	protected WsInvoker						nextProxyInvoker;
	protected Proxy							myProxy;
	protected String						wsMethodName;
	protected ProxyEndpoint					nextProxy;
	protected String						nextProxyUrl;
	protected Endpoint						ep;
	protected String						publishURL;
	protected ArrayList<String>				myProvidingWebServices;

	private long							timeout = 10000;

	public ChoreographyActor() {
		ep = Endpoint.create(this);
		myProvidingWebServices = new ArrayList<String>();
		myProxy = new Proxy();
		otherProxies = new ArrayList<ChoreographyActor>();
	}

	public ChoreographyActor(BPMNTask task) {
		otherProxies = new ArrayList<ChoreographyActor>();
		myProvidingWebServices = new ArrayList<String>();
		myProxy = new Proxy();
		wsMethodName = task.getMethodName();
		nextProxyUrl = task.getNextLink();
		publishURL = task.getEndpoint();
		for (String wsdlFile : task.getProvidingServicesWsdlList()) {
			myProxy.addWebService(wsdlFile);
		}
		ep = Endpoint.create(this);
	}

	public ProxyEndpoint getNextProxy() {
		return nextProxy;
	}

	public void setNextProxy(ProxyEndpoint nextProxy) {
		this.nextProxy = nextProxy;
	}

	public void setNextProxy(String nextProxyUrl) {
		// TODO
	}

	public String getWsMethodName() {
		return wsMethodName;
	}

	public void setWsMethodName(String wsMethodName) {
		this.wsMethodName = wsMethodName;
	}

	public Endpoint getEp() {
		return ep;
	}

	public void setEp(Endpoint ep) {
		this.ep = ep;
	}

	public ArrayList<String> getMyProvidingWebServices() {
		return myProvidingWebServices;
	}

	public void setMyProvidingWebServices(ArrayList<String> myProvidingWebServices) {
		this.myProvidingWebServices = myProvidingWebServices;
	}

	public void setOtherProxies(ArrayList<ChoreographyActor> otherProxies) {
		this.otherProxies = otherProxies;
	}

	@WebMethod
	@Oneway
	public abstract void playRole(int parameter, int key);

	public Proxy getMyProxy() {
		return myProxy;
	}

	public void setMyProxy(Proxy myProxy) {
		this.myProxy = myProxy;
	}

	public String getPublishURL() {
		return publishURL;
	}

	public void setPublishURL(String url) {
		publishURL = url;
	}

	@WebMethod(exclude = true)
	public void publishWS(String publishURL) {
		ep = Endpoint.create(this);
		Endpoint.publish(publishURL, this);
	}

	public void stopService() {
		ep.stop();
	}

	public String getNextProxyUrl() {
		return nextProxyUrl;
	}

	public void setNextProxyUrl(String newUrl) {
		nextProxyUrl = newUrl;
	}

	public ArrayList<ChoreographyActor> getOtherProxies() {
		return otherProxies;
	}

	public ChoreographyActor getOtherProxies(int index) {
		return otherProxies.get(index);
	}

	public void addOtherProxy(ChoreographyActor choreographyActor) {
		otherProxies.add(choreographyActor);
	}

	public String getProvidingWebService(int index) {
		return myProvidingWebServices.get(index);
	}

	public void addProvidingWebService(String wsEndpoint) {
		myProvidingWebServices.add(wsEndpoint);
	}

	public void publishWS() {
		publishWS(publishURL);

	}

	protected void addProvidingWebServices(List<String> providingWebServices) {
		for (String webServiceURL : providingWebServices) {
			addProvidingWebService(webServiceURL);
		}
	}

	protected List<String> getProvidingWebServices() {
		return myProvidingWebServices;
	}

	protected void informNextLink(int parameter, int key) throws URISyntaxException, IOException {

		nextProxyInvoker = getNextProxyInvoker();
		Retry retry = new Retry(3);

		retry.setTimeout(timeout);

		retry.addAvailableInvoker(nextProxyInvoker);
		retry.setCurrentWS(nextProxyInvoker);

		Object result = retry.invokeMethod("playRole", parameter, key);
		while (result == null || nextProxyInvoker == null) {
			result = retry.invokeMethod("playRole", parameter, key);
			
		}
	}

	private void redeployNextProxy() {
		System.err.println("Recovery is needed... Redeploying next proxy");
		for (ChoreographyActor erroredProxy : otherProxies) {
			if (erroredProxy.publishURL.contentEquals(nextProxyUrl)) {
				ProxyEndpoint recoveredProxy = new ProxyEndpoint();
				System.out.println("Replacing missing proxy at " + erroredProxy.publishURL + " with new proxy at \"" + publishURL+"recovered\"");
				recoveredProxy.setPublishURL(publishURL + "recovered");
				recoveredProxy.setNextProxyUrl(erroredProxy.getNextProxyUrl());
				recoveredProxy.addProvidingWebServices(erroredProxy.getMyProvidingWebServices());
				setNextProxyUrl(recoveredProxy.getPublishURL());
				otherProxies.remove(erroredProxy);
				otherProxies.add(recoveredProxy);
				recoveredProxy.publishWS();

			}
		}
	}

	@SuppressWarnings("restriction")
	protected boolean checkServiceAvailability(String wsdlURL) throws URISyntaxException, IOException {
		EndpointAddress ep = new EndpointAddress(wsdlURL);
		ep.openConnection().getContent();
		return true;
	}

	protected WsInvoker getNextProxyInvoker() {
		try {
			System.out.println("Contacting proxy at " + nextProxyUrl + "?wsdl");
			if (nextProxyInvoker == null && checkServiceAvailability(nextProxyUrl+ "?wsdl")) {
				nextProxyInvoker = new WsInvoker(nextProxyUrl+ "?wsdl");
				nextProxyInvoker.setTimeout(timeout);
			}
		} catch (URISyntaxException e1) {
			System.out.println("URI Syntax Exception");
			redeployNextProxy();
		} catch (IOException e1) {
			System.out.println("IO Exception");
			redeployNextProxy();
		}
		
		
		try {
			if (checkServiceAvailability(nextProxyUrl))
				return new WsInvoker(nextProxyUrl);
		} catch (Exception e) {
			System.out.println("Next proxy is not online, recovery needed");

		}
		return null;
	}

	protected void setUpWebService(String nextProxyWSDL, String wsMethodName, String... webServices) {

		this.nextProxyUrl = nextProxyWSDL;

		this.wsMethodName = wsMethodName;

		myProxy = new Proxy();
		for (String webServiceEndpoint : webServices) {
			System.out.println("Adding WS at: " + webServiceEndpoint);
			myProxy.addWebService(webServiceEndpoint);
		}

	}

	@WebMethod
	public void setUpWebService(String webServiceWSDL) {
		setUpWebService(nextProxyUrl, wsMethodName, webServiceWSDL);
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
		if (nextProxyInvoker != null)
			this.nextProxyInvoker.setTimeout(timeout);

	}

}