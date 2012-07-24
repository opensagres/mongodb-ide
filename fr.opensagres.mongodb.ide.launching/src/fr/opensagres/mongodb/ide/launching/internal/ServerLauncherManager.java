package fr.opensagres.mongodb.ide.launching.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;

import fr.opensagres.mongodb.ide.core.IServerLauncherManager;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;
import fr.opensagres.mongodb.ide.launching.internal.jobs.StartJob;
import fr.opensagres.mongodb.ide.launching.internal.jobs.StartShellJob;
import fr.opensagres.mongodb.ide.launching.internal.jobs.StopJob;
import fr.opensagres.mongodb.ide.launching.internal.jobs.StopShellJob;
import fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongod.PingThread;

public class ServerLauncherManager implements IServerLauncherManager {

	protected static final char[] INVALID_CHARS = new char[] { '\\', '/', ':',
			'*', '?', '"', '<', '>', '|', '\0', '@', '&' };

	public void start(Server server) throws Exception {
		// see bug 250999 - debug UI must be loaded before looking for debug
		// consoles
		org.eclipse.debug.ui.console.IConsole.class.toString();

		StartJob startJob = new StartJob(server);
		startJob.schedule();
		// startJob.join();

	}

	public void stop(Server server, boolean force) throws Exception {
		if (server.getServerState() == ServerState.Stopped)
			return;
		// see bug 250999 - debug UI must be loaded before looking for debug
		// consoles
		org.eclipse.debug.ui.console.IConsole.class.toString();

		StopJob job = new StopJob(server, force);
		job.schedule();
	}

	/**
	 * Return the launch configuration for this server. If one does not exist,
	 * it will be created if "create" is true, and otherwise will return
	 * <code>null</code>. Will return <code>null</code> if this server type has
	 * no associated launch configuration type (i.e. the server cannot be
	 * started).
	 * 
	 * @param server
	 * 
	 * @param create
	 *            <code>true</code> if a new launch configuration should be
	 *            created if there are none already
	 * @param monitor
	 *            a progress monitor, or <code>null</code> if progress reporting
	 *            and cancellation are not desired
	 * @return the launch configuration, or <code>null</code> if there was no
	 *         existing launch configuration and <code>create</code> was false
	 * @throws CoreException
	 */
	public static ILaunchConfiguration getLaunchConfiguration(Server server,
			boolean create, IProgressMonitor monitor) throws CoreException {
		ILaunchConfigurationType launchConfigType = LaunchHelper
				.getMongodLaunchConfigurationType();
		return getLaunchConfiguration(launchConfigType, server.getId(),
				LaunchHelper.ATTR_SERVER_ID, server.getName(), create, monitor);
	}

	protected static String getValidLaunchConfigurationName(String s) {
		if (s == null || s.length() == 0)
			return "1";
		int size = INVALID_CHARS.length;
		for (int i = 0; i < size; i++) {
			s = s.replace(INVALID_CHARS[i], '_');
		}
		return s;
	}

	public static void setupLaunchConfiguration(
			ILaunchConfigurationWorkingCopy workingCopy,
			IProgressMonitor monitor) {
		try {
			// getBehaviourDelegate(monitor).setupLaunchConfiguration(workingCopy,
			// monitor);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				// Trace.trace(Trace.STRING_SEVERE,
				// "Error calling delegate setupLaunchConfiguration() "
				// + toString(), e);
			}
		}
	}

	/**
	 * Terminates the server.
	 */
	public static void terminate(Server server) {
		if (server.getServerState() == ServerState.Stopped)
			return;

		try {
			server.setServerState(ServerState.Stopping);
			// if (Trace.isTraceEnabled())
			// Trace.trace(Trace.FINER, "Killing the Tomcat process");
			ILaunch launch = server.getData(ILaunch.class);
			if (launch != null) {
				launch.terminate();
				stopImpl(server);
			}
		} catch (Exception e) {
			// Trace.trace(Trace.SEVERE, "Error killing the process", e);
		}
	}

	public static void stopImpl(Server server) {
		PingThread ping = server.getData(PingThread.class);
		if (ping != null) {
			ping.stop();
			ping = null;
		}
		IDebugEventSetListener processListener = server
				.getData(IDebugEventSetListener.class);
		if (processListener != null) {
			DebugPlugin.getDefault().removeDebugEventListener(processListener);
			processListener = null;
		}
		server.setServerState(ServerState.Stopped);
	}

	public void startShell(Database database) {
		// see bug 250999 - debug UI must be loaded before looking for debug
		// consoles
		org.eclipse.debug.ui.console.IConsole.class.toString();

		StartShellJob startJob = new StartShellJob(database);
		startJob.schedule();

	}

	public void stopShell(Database database) {
		// see bug 250999 - debug UI must be loaded before looking for debug
		// consoles
		org.eclipse.debug.ui.console.IConsole.class.toString();

		StopShellJob stopJob = new StopShellJob(database);
		stopJob.schedule();

	}

	public static ILaunchConfiguration getLaunchConfiguration(
			Database database, boolean create, IProgressMonitor monitor)
			throws CoreException {
		ILaunchConfigurationType launchConfigType = LaunchHelper
				.getMongoLaunchConfigurationType();
		return getLaunchConfiguration(launchConfigType, database.getId(),
				LaunchHelper.ATTR_DATABASE_ID, database.getName(), create,
				monitor);
	}

	public static ILaunchConfiguration getLaunchConfiguration(
			ILaunchConfigurationType launchConfigType, String id,
			String attrId, String name, boolean create, IProgressMonitor monitor)
			throws CoreException {
		if (launchConfigType == null)
			return null;
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		ILaunchConfiguration[] launchConfigs = null;
		try {
			launchConfigs = launchManager
					.getLaunchConfigurations(launchConfigType);

		} catch (CoreException e) {
			// ignore
		}

		ILaunchConfiguration existingLaunchConfiguration = getExistingLaunchConfiguration(
				id, attrId, launchConfigs);
		if (existingLaunchConfiguration != null) {
			return existingLaunchConfiguration;
		}
		if (!create)
			return null;

		// create a new launch configuration
		String launchName = getValidLaunchConfigurationName(name);
		launchName = launchManager
				.generateUniqueLaunchConfigurationNameFrom(launchName);
		ILaunchConfigurationWorkingCopy wc = launchConfigType.newInstance(null,
				launchName);
		wc.setAttribute(attrId, id);
		setupLaunchConfiguration(wc, monitor);
		return wc.doSave();
	}

	private static ILaunchConfiguration getExistingLaunchConfiguration(
			String id, String attrId, ILaunchConfiguration[] launchConfigs) {
		if (launchConfigs != null) {
			int size = launchConfigs.length;
			for (int i = 0; i < size; i++) {
				try {
					String databaseId = launchConfigs[i].getAttribute(attrId,
							(String) null);
					if (id.equals(databaseId)) {
						final ILaunchConfigurationWorkingCopy wc = launchConfigs[i]
								.getWorkingCopy();
						// setupLaunchConfiguration(wc, monitor);
						if (wc.isDirty()) {
							final ILaunchConfiguration[] lc = new ILaunchConfiguration[1];
							Job job = new Job("Saving launch configuration") {
								protected IStatus run(IProgressMonitor monitor2) {
									try {
										lc[0] = wc.doSave();
									} catch (CoreException ce) {
										if (Trace.SEVERE) {
											Trace.trace(Trace.STRING_SEVERE,
													"Error configuring launch",
													ce);
										}
									}
									return Status.OK_STATUS;
								}
							};
							job.setSystem(true);
							job.schedule();
							try {
								job.join();
							} catch (Exception e) {
								if (Trace.SEVERE) {
									Trace.trace(Trace.STRING_SEVERE,
											"Error configuring launch", e);
								}
							}
							if (job.getState() != Job.NONE) {
								job.cancel();
								lc[0] = wc.doSave();
							}

							return lc[0];
						}
						return launchConfigs[i];
					}
				} catch (CoreException e) {
					if (Trace.SEVERE) {
						Trace.trace(Trace.STRING_SEVERE,
								"Error configuring launch", e);
					}
				}
			}
		}
		return null;
	}
}
