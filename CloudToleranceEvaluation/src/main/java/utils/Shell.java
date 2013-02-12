package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Process;

public class Shell {

	public static void runCommandNow(String command) {
		int pid = -1;

		try {
			// Run ls command
			Process process = Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	public static String runCommandAndGetOutput(String command) {

		String output = "";
		try {
			Process proc = Runtime.getRuntime().exec(command);
			int result = proc.waitFor();
			BufferedInputStream buffer = new BufferedInputStream(
					proc.getInputStream());

			BufferedReader commandOutput = new BufferedReader(
					new InputStreamReader(buffer));

			String line = null;

			while ((line = commandOutput.readLine()) != null) {
				output.concat(line + '\n');
			}

			commandOutput.close();

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return output;
	}

}
