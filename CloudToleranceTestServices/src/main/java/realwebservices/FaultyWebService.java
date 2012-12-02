package realwebservices;

import utils.exitmanager.ExitManager;
import utils.exitmanager.ExitManagerImpl;
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
		if (Math.random()*100 < faultyResponseProbability)
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