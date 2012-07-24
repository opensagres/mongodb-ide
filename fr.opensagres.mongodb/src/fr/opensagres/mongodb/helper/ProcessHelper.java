package fr.opensagres.mongodb.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProcessHelper {

	public static String getVersion(File mongoProcess) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder(
				mongoProcess.getPath(), "-version");
		processBuilder.redirectErrorStream(true);
		return execute(processBuilder);
	}

	public static String execute(ProcessBuilder processBuilder)
			throws IOException {
		Process pwd = processBuilder.start();
		BufferedReader outputReader = new BufferedReader(new InputStreamReader(
				pwd.getInputStream()));
		String output;
		StringBuilder lines = new StringBuilder();
		while ((output = outputReader.readLine()) != null) {
			lines.append(output.toString());
		}
		return lines.toString();
	}

	public static File getProcessFile(File mongoBaseDir, String processName) {
		File f = new File(mongoBaseDir, processName + ".exe");
		if (f.exists()) {
			return f;
		}
		return new File(mongoBaseDir, processName + ".sh");
	}

	public static void startServer(File mongodFile, Integer port)
			throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(mongodFile.getPath());
		processBuilder.redirectErrorStream(true);
		Process pwd = processBuilder.start();
		BufferedReader outputReader = new BufferedReader(new InputStreamReader(
				pwd.getInputStream()));
		String output;
		List lines = new ArrayList();
		while ((output = outputReader.readLine()) != null) {
			System.err.println(output.toString());
		}
		if (pwd.exitValue() == 0)
			pwd.waitFor();
	}

	public static void startShell(File mongoFile) throws IOException,
			InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(mongoFile.getPath());
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();
		
		// you need this if you're going to write something to the command's input stream
	    // (such as when invoking the 'sudo' command, and it prompts you for a password).
	    OutputStream stdOutput = process.getOutputStream();
	    
	    // i'm currently doing these on a separate line here in case i need to set them to null
	    // to get the threads to stop.
	    // see http://java.sun.com/j2se/1.5.0/docs/guide/misc/threadPrimitiveDeprecatio...
	    InputStream inputStream = process.getInputStream();
	    InputStream errorStream = process.getErrorStream();

	    // these need to run as java threads to get the standard output and error from the command.
	    // the inputstream handler gets a reference to our stdOutput in case we need to write
	    // something to it, such as with the sudo command
	    ThreadedStreamHandler inputStreamHandler = new ThreadedStreamHandler(inputStream, stdOutput, "");
	    ThreadedStreamHandler errorStreamHandler = new ThreadedStreamHandler(errorStream);

	    // TODO the inputStreamHandler has a nasty side-effect of hanging if the password is wrong; fix it
	    inputStreamHandler.start();
	    errorStreamHandler.start();

	    // TODO a better way to do this?
	    int exitValue = process.waitFor();

	    // TODO a better way to do this?
	    inputStreamHandler.interrupt();
	    errorStreamHandler.interrupt();
	    inputStreamHandler.join();
	    errorStreamHandler.join();

		
	}
}
