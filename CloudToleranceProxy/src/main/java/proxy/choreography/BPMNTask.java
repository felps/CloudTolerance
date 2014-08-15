package proxy.choreography;

import java.util.ArrayList;

import com.sun.xml.internal.ws.api.pipe.NextAction;

public class BPMNTask {

	private String name;
	private String endpoint;
	private String nextLink;
	private String methodName;
	private ArrayList<String> providingServicesWsdlList;
	private String nextAction;
	

	// <proxy publishable endpoint> <next proxy endpoint> <service method name> <services' WSDL url>
	public BPMNTask( String name, String publishEndpoint, String nextLink, String methodName, String wsdlFile) {
		setEndpoint(publishEndpoint);
		this.nextLink = nextLink;
		this.methodName = methodName;
		providingServicesWsdlList = new ArrayList<String>(); 
		addProvidingServiceWsdl(wsdlFile);
		this.name = name;
		
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getNextLink() {
		return nextLink;
	}

	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void addProvidingServiceWsdl(String wsdlFile) {
		providingServicesWsdlList.add(wsdlFile);
	}
	
	public ArrayList<String> getProvidingServicesWsdlList() {
		return providingServicesWsdlList;
	}

	public String getName() {
		return name;
	}

}
