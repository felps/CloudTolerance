package proxy.choreography;

import java.util.ArrayList;

public class BPMNTask {

	private String name;
	private String endpoint;
	private String nextLink;
	private String methodName;
	private ArrayList<String> providingServicesWsdlList;

	// <name> <proxy publishable endpoint> <next proxy endpoint> <service method name> <services' WSDL url>
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
	
	public String getPort(){
		System.out.println(endpoint);
		System.out.println(endpoint.split(":")[2]);
		return endpoint.split(":")[2].split("/")[0];
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
