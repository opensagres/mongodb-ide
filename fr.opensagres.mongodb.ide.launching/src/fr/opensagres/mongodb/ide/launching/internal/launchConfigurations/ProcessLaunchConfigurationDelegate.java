package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.osgi.util.NLS;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.launching.internal.Activator;

public abstract class ProcessLaunchConfigurationDelegate implements
		ILaunchConfigurationDelegate {

	private final MongoProcessType processType;

	public ProcessLaunchConfigurationDelegate(MongoProcessType processType) {
		this.processType = processType;
	}

	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		if (monitor.isCanceled()) {
			return;
		}
		onStart(configuration);
		MongoRuntime runtime = getRuntime(configuration);
		if (runtime == null) {
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"Runtime is missing");
			throw new CoreException(status);
		}
		// resolve location
		IPath location = getLocation(configuration, runtime);

		if (monitor.isCanceled()) {
			return;
		}

		// resolve arguments
		String[] arguments = getArguments(configuration, runtime);

		if (monitor.isCanceled()) {
			return;
		}

		int cmdLineLength = 1;
		if (arguments != null) {
			cmdLineLength += arguments.length;
		}
		String[] cmdLine = new String[cmdLineLength];
		cmdLine[0] = location.toOSString();
		if (arguments != null) {
			System.arraycopy(arguments, 0, cmdLine, 1, arguments.length);
		}

		File workingDir = null;

		if (monitor.isCanceled()) {
			return;
		}

		String[] envp = DebugPlugin.getDefault().getLaunchManager()
				.getEnvironment(configuration);

		if (monitor.isCanceled()) {
			return;
		}
		Process p = DebugPlugin.exec(cmdLine, workingDir, envp);
		IProcess process = null;

		// add process type to process attributes
		Map processAttributes = new HashMap();
		String programName = location.lastSegment();
		String extension = location.getFileExtension();
		if (extension != null) {
			programName = programName.substring(0, programName.length()
					- (extension.length() + 1));
		}
		programName = programName.toLowerCase();
		processAttributes.put(IProcess.ATTR_PROCESS_TYPE, programName);

		if (p != null) {
			monitor.beginTask(
					NLS.bind("a", new String[] { configuration.getName() }),
					IProgressMonitor.UNKNOWN);
			process = createProcess(launch, location, p, processAttributes);
		}
		if (p == null || process == null) {
			if (p != null)
				p.destroy();
			// throw new CoreException(new Status(IStatus.ERROR,
			// IExternalToolConstants.PLUGIN_ID,
			// IExternalToolConstants.ERR_INTERNAL_ERROR,
			// ExternalToolsProgramMessages.ProgramLaunchDelegate_4, null));
		}
		// process.setAttribute(IProcess.ATTR_CMDLINE,
		// generateCommandLine(cmdLine));
		onEnd(configuration, process, false);

		// while (!process.isTerminated()) {
		// try {
		// if (monitor.isCanceled()) {
		// process.terminate();
		// break;
		// }
		// Thread.sleep(50);
		// } catch (InterruptedException e) {
		// }
		// }

		// if (scope != null) {
		// BackgroundResourceRefresher refresher = new
		// BackgroundResourceRefresher(
		// configuration, process);
		// refresher.startBackgroundRefresh();

		// Mongo mongo;
		// try {
		// mongo = new Mongo("localhost");
		// DB db = mongo.getDB("admin");
		// CommandResult shutdownResult = db.command(new
		// BasicDBObject("shutdown", 1));
		// shutdownResult.throwOnError();
		//
		// } catch (UnknownHostException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (MongoException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// }
	}

	protected void onStart(ILaunchConfiguration configuration)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	protected void onEnd(ILaunchConfiguration configuration,
			IProcess newProcess, boolean error) throws CoreException {
		// TODO Auto-generated method stub

	}

	protected abstract String[] getArguments(
			ILaunchConfiguration configuration, MongoRuntime runtime)
			throws CoreException;

	protected abstract IProcess createProcess(ILaunch launch, IPath location,
			Process p, Map processAttributes);

	protected Path getLocation(ILaunchConfiguration configuration,
			MongoRuntime runtime) throws CoreException {
		switch (processType) {
		case mongo:
			return runtime.getMongoProcessLocation();
		default:
			return runtime.getMongodProcessLocation();
		}
	}

	protected abstract MongoRuntime getRuntime(
			ILaunchConfiguration configuration) throws CoreException;
}
