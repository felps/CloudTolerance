package utils.exitmanager;

public class ExitManagerMock implements ExitManager {
	public boolean exitWasCalled;
	public int exitCode;

	public void exit(int exitCode) {
		exitWasCalled = true;
		this.exitCode = exitCode;
	}
}
