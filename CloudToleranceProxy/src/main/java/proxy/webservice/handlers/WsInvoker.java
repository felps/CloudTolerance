package proxy.webservice.handlers;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import proxy.utils.Result;

public class WsInvoker {

	private String wsdlURL;
	private Client webServiceClient;
	// private List<WsInvokation> pendingRequests;
	private long timeout;

	public long getTimeout() {
		return timeout;
	}

	
	public String getWsdlURL() {
		return wsdlURL;
	}


	public void setTimeout(long newTimeout) {

		timeout = newTimeout;
		HTTPConduit http = (HTTPConduit) webServiceClient.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		// ClassLoader clAfterClientPolicy =
		// httpClientPolicy.getClass().getClassLoader();
		httpClientPolicy.setConnectionTimeout(newTimeout);
		http.setClient(httpClientPolicy);
	}

	public WsInvoker(String wsdlURL) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		webServiceClient = dcf.createClient(wsdlURL);
		this.wsdlURL = wsdlURL;
	}

	public WsInvokation invokeWebMethod(String methodName, Object... args) throws RuntimeException {
		WsInvokation invokation = new WsInvokation(wsdlURL, webServiceClient,
				timeout, methodName, args);
		try {
			invokation.perform();
		} catch (RuntimeException e) {
			throw e;
		}

		return invokation;
	}

	public WsInvokation invokeWebMethod(Result resultSetter, String methodName,
			Object... args) throws RuntimeException {
		WsInvokation invokation = new WsInvokation(resultSetter, wsdlURL,
				webServiceClient, methodName, args);
		try {
			invokation.perform();
		} catch (RuntimeException e) {
			throw e;
		}

		return invokation;
	}
}
