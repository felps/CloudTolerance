package proxy.choreography;

import java.util.HashMap;

import proxy.ChoreographyEndpoint;
import proxy.ProxyEndpoint;

public class Choreography {

	private HashMap<String, BPMNTask> processingTasks; // Louzy name for
														// describing tasks
														// which are not the
														// endpoint
	BPMNTask endpoint; // Particular task which is an endpoint

	public HashMap<String, BPMNTask> getProcessingTasks() {
		return processingTasks;
	}

	public Choreography() {
		processingTasks = new HashMap<String, BPMNTask>();
	}

	public void addTask(BPMNTask newTask) {
		processingTasks.put(newTask.getName(), newTask);
	}

	public void setEndpoint(BPMNTask task) {
		if (processingTasks.containsValue(task))
			processingTasks.remove(task.getName());

		endpoint = task;
	}

	public static void enact(Choreography chor) {
		if (chor.validate()) {
			for (BPMNTask task : chor.processingTasks.values()) {
				ProxyEndpoint proxy = new ProxyEndpoint(task);
				proxy.publishWS(task.getEndpoint());
			}

			ChoreographyEndpoint chorEp = new ChoreographyEndpoint(
					chor.endpoint);
			chorEp.publishWS(chor.endpoint.getEndpoint());
		}
	}

	public Boolean validate() {
		Boolean found;
		found = isThereATaskAtThisEndpoint(endpoint.getNextLink());
		if (!found)
			return false;
		for (BPMNTask task : processingTasks.values()) {
			found = isThereATaskAtThisEndpoint(task.getNextLink());
			if (!found)
				return false;
		}

		return true;

	}

	private Boolean isThereATaskAtThisEndpoint(String nextLink) {
		Boolean found = false;
		System.out.println("Searching for task at " + nextLink);
		for (BPMNTask task : processingTasks.values()) {
			if (task.getEndpoint().split(":")[2] == nextLink.split(":")[2]
					.split("?")[0]) {
				found = true;
			}
		}
		if ((!found) && (endpoint.getEndpoint() != nextLink)) {
			return false;
		} else
			return true;
	}

}
