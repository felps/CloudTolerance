package proxy.choreography;

import java.util.HashMap;

import proxy.ChoreographyEndpoint;
import proxy.ProxyEndpoint;

public class Choreography {

	// Louzy name for describing tasks which are not the endpoint
	private HashMap<String, BPMNTask> processingTasks;
	
	public HashMap<String, BPMNTask> getProcessingTasks() {
		return processingTasks;
	}

	// Particular task which is an endpoint
	BPMNTask endpoint;
	
	public Choreography(){
		processingTasks = new HashMap<String, BPMNTask>();
	}

	public void addTask(BPMNTask newTask) {
		processingTasks.put(newTask.getName(), newTask);
	}

	public void setEndpoint(BPMNTask task) {
		if(processingTasks.containsValue(task))
			processingTasks.remove(task.getName());
		
		endpoint = task;
	}

	public static void enact(Choreography chor) {
		for (BPMNTask task : chor.processingTasks.values()) {
			ProxyEndpoint proxy = new ProxyEndpoint(task);
			proxy.publishWS(task.getEndpoint()); 
		}
		
		ChoreographyEndpoint chorEp = new ChoreographyEndpoint(chor.endpoint);
		chorEp.publishWS(chor.endpoint.getEndpoint());
	}

}
