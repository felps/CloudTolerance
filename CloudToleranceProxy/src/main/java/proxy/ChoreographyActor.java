package proxy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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
	protected ChoreographyActor				nextProxy;
	protected String						nextProxyUrl;
	protected Endpoint						ep;
	protected String						publishURL;
	protected ArrayList<String>				myProvidingWebServices;
	private URL								detailedPublishUrl;

	private long							timeout	= 10000;

	private BPMNTask						task;

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
		this.task = task;
	}

	public ChoreographyActor getNextActor() {
		return nextProxy;
	}

	public void setNextProxy(ProxyEndpoint nextProxy) {
		this.nextProxy = nextProxy;
		task.setNextLink(nextProxy.publishURL);
	}

	// public void setNextProxy(String nextProxyUrl) {
	// // TODO
	// }

	public String getWsMethodName() {
		return wsMethodName;
	}

	public void setWsMethodName(String wsMethodName) {
		this.wsMethodName = wsMethodName;
		task.setMethodName(wsMethodName);
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
		for (String wsdlFile : myProvidingWebServices) {
			task.addProvidingServiceWsdl(wsdlFile);
		}
	}

	public void setOtherProxies(ArrayList<ChoreographyActor> otherProxies) {
		this.otherProxies = otherProxies;
	}

	@WebMethod
	public abstract int playRole(int parameter, int key);

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
		task.setEndpoint(url);
		//TODO : This is lazy...
		try {
			detailedPublishUrl = new URL(publishURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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
		task.setNextLink(newUrl);
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
		task.addProvidingServiceWsdl(wsEndpoint);
	}

	public void publishWS() {
		if (publishURL.contains("127.0.0.1"))
			publishWS(publishURL.replace("127.0.0.1", "0.0.0.0"));
		else if (publishURL.contains("localhost"))
			publishWS(publishURL.replace("localhost", "0.0.0.0"));
		else
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

		try {
			System.out.println("Actor: " + publishURL + "\n Notifying: " + nextProxyUrl);
			nextProxyInvoker = getNextProxyInvoker();
			Retry retry = new Retry(3);

			retry.setTimeout(timeout);

			retry.addAvailableInvoker(nextProxyInvoker);
			retry.setCurrentWS(nextProxyInvoker);

			Object result = retry.invokeMethod("playRole", parameter, key);
			while (result == null || nextProxyInvoker == null) {
				result = retry.invokeMethod("playRole", parameter, key);
			}
		} catch (ProxyNotOnlineException e) {
			System.out.println("Actor: " + publishURL + "Next proxy not reachable at " + e.url);
			ChoreographyActor newProxy = redeployNextProxy();
			newProxy.playRole(parameter, key);
		}
	}

	private ChoreographyActor redeployNextProxy() {
		System.err.println("Recovery is needed... Redeploying next proxy");
		for (ChoreographyActor erroredProxy : otherProxies) {
			if (erroredProxy.getPublishURL().contentEquals(nextProxyUrl)) {
				System.err.println("Replacing missing proxy at " + erroredProxy.getPublishURL()
						+ " with new proxy at \"" + publishURL + "recovered\"");

				ProxyEndpoint recoveredProxy = new ProxyEndpoint(erroredProxy.getTask());

				// recoveredProxy.setNextProxyUrl(erroredProxy.getNextProxyUrl());
				recoveredProxy.addProvidingWebServices(erroredProxy.getMyProvidingWebServices());
				recoveredProxy.setMyProxy(erroredProxy.getMyProxy());
				recoveredProxy.setPublishURL(publishURL + "recovered");

//				System.out.println("Recovered proxy data:\n" + "recoveredProxy.setNextProxyUrl("
//						+ erroredProxy.getNextProxyUrl() + ")\n" + "recoveredProxy.addProvidingWebServices("
//						+ erroredProxy.getMyProvidingWebServices() + ");" + '\n' + "recoveredProxy.setMyProxy("
//						+ erroredProxy.getMyProxy() + ")\n" +
//
//						"setNextProxyUrl(" + recoveredProxy.getPublishURL() + ");\n" + "recoveredProxy.setPublishURL("
//						+ publishURL + "recovered)");

				setNextProxyUrl(recoveredProxy.getPublishURL()+"?wsdl");
				recoveredProxy.publishWS();

				otherProxies.remove(erroredProxy);
				otherProxies.add(recoveredProxy);
				return recoveredProxy;

			}
		}
		return null;
	}

	private BPMNTask getTask() {
		return task;
	}

	@SuppressWarnings("restriction")
	protected boolean checkServiceAvailability(String wsdlURL) throws URISyntaxException, IOException {
		EndpointAddress ep = new EndpointAddress(wsdlURL);
		ep.openConnection().getContent();
		return true;
	}

	protected WsInvoker getNextProxyInvoker() throws ProxyNotOnlineException {
		try {
			String url = nextProxyUrl;
			if (!url.endsWith("?wsdl"))
				url += "?wsdl";
			System.out.println("Contacting proxy at " + nextProxyUrl);
			if (nextProxyInvoker == null && checkServiceAvailability(nextProxyUrl)) {
				nextProxyInvoker = new WsInvoker(nextProxyUrl);
				nextProxyInvoker.setTimeout(timeout);
			}
			if (checkServiceAvailability(nextProxyUrl))
				return new WsInvoker(nextProxyUrl);
		} catch (URISyntaxException e1) {
			System.out.println("URI Syntax Exception");
			System.out.println("Next proxy is not online, recovery needed");
			ProxyNotOnlineException exc = new ProxyNotOnlineException();
			exc.url = nextProxyUrl;
			throw exc;
		} catch (IOException e1) {
			System.out.println("IO Exception");
			System.out.println("Next proxy is not online, recovery needed");
			ProxyNotOnlineException exc = new ProxyNotOnlineException();
			exc.url = nextProxyUrl;
			throw exc;
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