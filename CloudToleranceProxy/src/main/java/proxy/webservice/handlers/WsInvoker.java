package proxy.webservice.handlers;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class WsInvoker {

	private String wsdlURL;
	private Client webServiceClient;
//	private List<WsInvokation> pendingRequests;
	private long timeout;

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long newTimeout) {
		
		timeout=newTimeout;
		HTTPConduit http = (HTTPConduit) webServiceClient.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
//        ClassLoader clAfterClientPolicy = httpClientPolicy.getClass().getClassLoader();
        httpClientPolicy.setConnectionTimeout(newTimeout);
        http.setClient(httpClientPolicy);
	}

	public WsInvoker(String wsdlURL) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		webServiceClient = dcf.createClient(wsdlURL);
		this.wsdlURL = wsdlURL;
	}

	public WsInvokation invokeWebMethod(String methodName, Object... args) {
		WsInvokation invokation = new WsInvokation(wsdlURL, webServiceClient,
				timeout, methodName, args);
		invokation.perform();
		return invokation;
	}
}
