package webservices;

import javax.xml.ws.Endpoint;

import utils.exitmanager.*;
import exceptions.SystemCrash;

public class FaultyWebService {

	private double failstopProbability;
	private double faultyResponseProbability;
	private ExitManager exitManager;

	public FaultyWebService(double failStopProb, double faultyResponseProb) {
		this.failstopProbability = failStopProb;
		this.faultyResponseProbability = faultyResponseProb;
		setExitManager(new ExitManagerImpl());
	}

	protected static void raise(String[] args) {
		if (args.length != 2)
			System.out
					.println("USAGE: <WSname> <WS fail-stop probability> <WS faulty response probability>");

		double failStop = Double.parseDouble(args[0]);
		double faultyResponse = Double.parseDouble(args[1]);

		FaultyWebService ws = new FaultyWebService(failStop, faultyResponse);

		Endpoint.publish("0.0.0.0", ws);

		System.out.println("Web service up and Running! \n"
				+ "Fail-stop probability:       " + failStop + "\n"
				+ "Faulty Response probability: " + faultyResponse);
	}

	public FaultyWebService() {
		super();
	}

	protected boolean emulateFailStopFailures() {

		try {
			return evaluateIfWillFailStop();
		} catch (SystemCrash crash) {
			exitManager.exit(1);
		}

		return false;
	}

	public double getFailstopProbability() {
		return failstopProbability;
	}

	public void setFailstopProbability(double failstopProbability) {
		this.failstopProbability = failstopProbability;
	}

	public double getFaultyResponseProbability() {
		return faultyResponseProbability;
	}

	public void setFaultyResponseProbability(double faultyResponseProbability) {
		this.faultyResponseProbability = faultyResponseProbability;
	}

	protected boolean evaluateIfWillFailStop() throws SystemCrash {

		if (Math.random() < failstopProbability) {
			System.out.println("Fail-stop failure emulated");
			throw (new SystemCrash());
		}
		return false;

	}

	protected boolean willReturnWrongAnswer() {
		if (Math.random() < faultyResponseProbability)
			return true;
		else
			return false;
	}

	protected int getRandomIntegerAnswer() {
		return (int) Math.round(Math.random() * 1000);
	}

	protected boolean getRandomBooleanAnswer() {
		return (Math.random() > 0.5);

	}

	public void setExitManager(ExitManager exitManager) {
		this.exitManager = exitManager;
	}

}