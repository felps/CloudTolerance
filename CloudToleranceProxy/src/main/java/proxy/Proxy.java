package proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import proxy.techniques.Active;
import proxy.techniques.FaultToleranceTechnique;
import proxy.techniques.Retry;
import proxy.webservice.handlers.WsInvoker;

public class Proxy {

	private HashMap<String, FaultToleranceTechnique> availableTechniques;
	private FaultToleranceTechnique currentTechnique;
	private List<WsInvoker> invokerList;
	
	public Proxy() {
		availableTechniques = new HashMap<String, FaultToleranceTechnique>();

		invokerList = new ArrayList<WsInvoker>();

		availableTechniques.put("Retry", new Retry());
		availableTechniques.put("Active", new Active());
		
		if(invokerList.size()<=1)
			currentTechnique = availableTechniques.get("Retry");
		else{
			currentTechnique = availableTechniques.get("Active");
			currentTechnique.addAvailableInvokers(invokerList);
			
		}
	}
	
	public void addWebService(String wsdlEndpoint) {
		WsInvoker newService = new WsInvoker(wsdlEndpoint);
		invokerList.add(newService);
		if(invokerList.size()<=1) {
			currentTechnique = availableTechniques.get("Retry");
			currentTechnique.addAvailableInvoker(newService);
		}
		else{
			currentTechnique = availableTechniques.get("Active");
			currentTechnique.addAvailableInvokers(invokerList);
		}

	}
	
	
	public Object invokeMethod(String wsMethodName, Object... wsParameterValues){
		return currentTechnique.invokeMethod(wsMethodName, wsParameterValues);
	}
}
