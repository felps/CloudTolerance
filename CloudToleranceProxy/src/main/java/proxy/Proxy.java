package proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import proxy.techniques.Active;
import proxy.techniques.FaultToleranceTechnique;
import proxy.techniques.Retry;
import proxy.techniques.RetryAlternate;
import proxy.techniques.Voting;
import proxy.webservice.handlers.WsInvoker;

public class Proxy {

	private HashMap<String, FaultToleranceTechnique> availableTechniques;
	private FaultToleranceTechnique currentTechnique;
	private List<WsInvoker> invokerList;
	private Logger log = Logger.getLogger(Proxy.class);
	private boolean reliableServices = true;
	private boolean realWorldEffects = false;
	
	public Proxy() {
		
		invokerList = new ArrayList<WsInvoker>();
		
		availableTechniques = new HashMap<String, FaultToleranceTechnique>();

		availableTechniques.put("Retry", new Retry());
		availableTechniques.put("Active", new Active());
		availableTechniques.put("Voting", new Voting());
		availableTechniques.put("Alternate", new RetryAlternate());
		
		if(invokerList.size() <= 1){
			log.info("Retry Technique");
			currentTechnique = availableTechniques.get("Retry");
			currentTechnique.setTimeout(10000);
		}
		else{
			log.info("Retry Alternate with " + invokerList.size() + " services ");
			currentTechnique = availableTechniques.get("Alternate");
		}
		currentTechnique.addAvailableInvokers(invokerList);
	}
	
	public boolean isRealWorldEffects() {
		return realWorldEffects;
	}

	public void setRealWorldEffects(boolean realWorldEffects) {
		this.realWorldEffects = realWorldEffects;
	}

	public void setUnreliableServices(){
		if (availableTechniques.containsKey("Voting")){
			currentTechnique = availableTechniques.get("Voting");
			currentTechnique.addAvailableInvokers(invokerList);
			System.out.println("Current Technique: Voting");
					
		}
	}
	public void addWebService(String wsdlEndpoint) {
		WsInvoker newService = new WsInvoker(wsdlEndpoint);
		addWebService(newService);
	}
	
	private void addWebService(WsInvoker newService) {
		invokerList.add(newService);
		if(invokerList.size()<=1) {
			currentTechnique = availableTechniques.get("Retry");
			currentTechnique.addAvailableInvoker(newService);
			System.out.println("Service Pool: " + invokerList.size());
			System.out.println("Current Technique: Retry");
		}
		else if(realWorldEffects){
			currentTechnique = availableTechniques.get("Alternate");
			currentTechnique.addAvailableInvokers(invokerList);
			System.out.println("Service Pool: " + invokerList.size());
			System.out.println("Current Technique: Alternate");
		}
		else if(reliableServices){
			currentTechnique = availableTechniques.get("Active");
			currentTechnique.addAvailableInvokers(invokerList);
			System.out.println("Service Pool: " + invokerList.size());
			System.out.println("Current Technique: Active");
		} else{
			currentTechnique = availableTechniques.get("Voting");
			currentTechnique.addAvailableInvokers(invokerList);
			System.out.println("Service Pool: " + invokerList.size());
			System.out.println("Current Technique: Voting");
		}
	}
	
	public Object invokeMethod(String wsMethodName, Object... wsParameterValues){
		return currentTechnique.invokeMethod(wsMethodName, wsParameterValues);
	}
	
	public FaultToleranceTechnique getCurrentTechnique(){
		return currentTechnique;
	}

	public synchronized void dropWebService(String wsdl) {
		ArrayList<WsInvoker> invokers = new ArrayList<WsInvoker>(invokerList);
		invokerList.removeAll(invokers);
		for (WsInvoker invoker : invokers) {
			if(!invoker.getWsdlURL().contentEquals(wsdl))
				addWebService(invoker);
		}
	}
}
