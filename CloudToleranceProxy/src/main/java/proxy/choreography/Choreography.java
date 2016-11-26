package proxy.choreography;

import java.util.HashMap;

import proxy.ChoreographyActor;
import proxy.ChoreographyEndpoint;
import proxy.ProxyEndpoint;

public class Choreography {

	private HashMap<String, BPMNTask>	processingTasks;	// Louzy name for
															// describing tasks
															// which are not the
															// endpoint
	private BPMNTask					endpoint;			// Particular task
															// which is an
															// endpoint

	private long						timeout;

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

	public void enact() {
		if (validate()) {
			for (BPMNTask task : processingTasks.values()) {
				ChoreographyActor proxy = new ProxyEndpoint(task);
				proxy.setTimeout(timeout);
				proxy.publishWS(task.getEndpoint());
				System.out.println("Published proxy at " + task.getEndpoint());
			}

			ChoreographyActor chorEp = new ChoreographyEndpoint(endpoint);
			chorEp.setTimeout(timeout);
			
			chorEp.publishWS(endpoint.getEndpoint());
			
			System.out.println("Published choreography at " + endpoint.getEndpoint());

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
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

			if (task.getEndpoint().startsWith("http://0.0.0.0") || task.getEndpoint().startsWith("http://127.0.0.1")
					|| task.getEndpoint().startsWith("http://localhost")) {
				String portAndService = nextLink.substring(nextLink.lastIndexOf(':'), nextLink.indexOf("?"));

				if (task.getEndpoint().startsWith("http://127.0.0.1") && task.getEndpoint().endsWith(portAndService)) {
					found = true;
				} else if (task.getEndpoint().startsWith("http://0.0.0.0")
						&& task.getEndpoint().endsWith(portAndService)) {
					found = true;
				} else if (task.getEndpoint().startsWith("http://localhost")
						&& task.getEndpoint().endsWith(portAndService)) {
					found = true;
				} else
					System.out.println(task.getEndpoint() + " does not end with " + portAndService);
			} else if (task.getEndpoint().contentEquals(nextLink)) {
				found = true;
			}

		}
		BPMNTask task = endpoint;

		if (task.getEndpoint().startsWith("http://0.0.0.0") || task.getEndpoint().startsWith("http://127.0.0.1")
				|| task.getEndpoint().startsWith("http://localhost")) {
			String portAndService = nextLink.substring(nextLink.lastIndexOf(':'), nextLink.indexOf("?"));

			if (task.getEndpoint().startsWith("http://127.0.0.1") && task.getEndpoint().endsWith(portAndService)) {
				found = true;
			} else if (task.getEndpoint().startsWith("http://0.0.0.0") && task.getEndpoint().endsWith(portAndService)) {
				found = true;
			} else if (task.getEndpoint().startsWith("http://localhost") && task.getEndpoint().endsWith(portAndService)) {
				found = true;
			}
		} else if (task.getEndpoint().contentEquals(nextLink)) {
			found = true;
		}

		if (!found) {
			return false;
		} else
			return true;
	}
}
