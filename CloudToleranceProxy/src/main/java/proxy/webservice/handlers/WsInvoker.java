package proxy.webservice.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class WsInvoker {

	private String wsdlURL;
	private Client webServiceClient;
	private List<WsInvokation> pendingRequests;

	public WsInvoker(String wsdlURL) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		webServiceClient = dcf.createClient(wsdlURL);
		this.wsdlURL = wsdlURL;
		pendingRequests = new ArrayList<WsInvokation>();
	}

	public WsInvokation invokeWebMethod(String methodName, Object... args) {
		WsInvokation invokation = new WsInvokation(wsdlURL, webServiceClient, methodName, args);
		pendingRequests.add(invokation);
		invokation.perform();
		return invokation;
	}
}
