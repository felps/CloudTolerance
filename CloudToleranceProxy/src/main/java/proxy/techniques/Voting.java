package proxy.techniques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.Endpoint;

import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;

import proxy.ChoreographyEndpoint;
import proxy.Proxy;
import proxy.utils.Result;
import proxy.webservice.handlers.WsInvokation;
import proxy.webservice.handlers.WsInvoker;

public class Voting implements FaultToleranceTechnique {

	private HashSet<WsInvoker> webServicePool;
	Logger log;
	private long timeout;

	public Voting(Logger log) {
		webServicePool = new HashSet<WsInvoker>();
		this.log = log;
	}

	public Voting() {
		webServicePool = new HashSet<WsInvoker>();
	}

	public boolean setServicePoolSize() {
		return false;
	}

	public void addAvailableInvoker(WsInvoker availableInvoker) {
		webServicePool.add(availableInvoker);
	}

	public void addAvailableInvokers(List<WsInvoker> availableInvokers) {
		webServicePool.addAll(availableInvokers);
	}

	public void removeInvoker(WsInvoker unavailableInvoker) {
		if (webServicePool.contains(unavailableInvoker))
			webServicePool.remove(unavailableInvoker);
	}

	public Object invokeMethod(String wsMethodName, Object... wsParameterValues) {
		List<Result> resultSetters = createResultSetters(webServicePool.size());

		int i = 0;
		for (WsInvoker invoker : webServicePool) {
			invoker.setTimeout(timeout);
			System.out.println("Single try at " + invoker.toString());
			singleTry(invoker, wsMethodName, wsParameterValues, resultSetters.get(i++));
		}

		try {
			return getMostVotedReply(resultSetters);
		} catch (TimeoutException e) {
			System.out.println("ERROR: Invokation of " + wsMethodName
					+ " timed out.");
			log.info("ERROR: Invokation of " + wsMethodName + " timed out.");
			e.printStackTrace();
		}

		return null;
	}

	private int getMostVotedReply(List<Result> resultSetters)
			throws TimeoutException {
		Map<Integer, Integer> votesPerResult = new HashMap<Integer, Integer>();
		
		for (Result resultSetter : resultSetters) {
				int returnedValue = (Integer) resultSetter.getResultValue();
				if(votesPerResult.containsKey(returnedValue)){
					
					Integer previousVoteCount = votesPerResult.get(returnedValue);

					votesPerResult.put(returnedValue, previousVoteCount+1);
					
				}
				else votesPerResult.put(returnedValue, 1);
		}
		
		int mostVotedResponse = 0;
		int mostVotesPerResponse = 0;
		
		for (int result : votesPerResult.keySet()) {
			if (votesPerResult.get(result) > mostVotesPerResponse){
				mostVotedResponse = result;
				mostVotesPerResponse = votesPerResult.get(result);
			}
		}
		System.out.println("Most Voted response: "+ mostVotedResponse + " with " + mostVotesPerResponse);
		return mostVotedResponse;
	}

	private ArrayList<Result> createResultSetters(int amount) {

		ArrayList<Result> resultSetters;
		resultSetters = new ArrayList<Result>();

		for (int i = 0; i < amount; i++) {
			if (timeout > 0)
				resultSetters.add(new Result(timeout));
			else
				resultSetters.add(new Result());
		}
		return resultSetters;
	}

	private void singleTry(WsInvoker webService, String wsMethodName,
			Object[] wsParameterValues, Result resultSetter) {
		if (wsParameterValues != null && wsParameterValues.length > 0)
			System.out.println("VOTING: currentWS.invokeWebMethod("
					+ wsMethodName + ", ("
					+ wsParameterValues[0].getClass().getName() + ")"
					+ wsParameterValues[0].toString() + ");");

		try {
			webService.invokeWebMethod(resultSetter, wsMethodName,
					wsParameterValues);
		} catch (RuntimeException e) {

		}
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
