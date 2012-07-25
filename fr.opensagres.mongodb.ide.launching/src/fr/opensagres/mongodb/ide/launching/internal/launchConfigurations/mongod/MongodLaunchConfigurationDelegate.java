package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongod;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;
import fr.opensagres.mongodb.ide.launching.internal.LaunchHelper;
import fr.opensagres.mongodb.ide.launching.internal.ServerLauncherManager;
import fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.MongoProcessType;
import fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.ProcessLaunchConfigurationDelegate;

public class MongodLaunchConfigurationDelegate extends
		ProcessLaunchConfigurationDelegate {

	public MongodLaunchConfigurationDelegate() {
		super(MongoProcessType.mongod);
	}

	@Override
	protected void onStart(ILaunchConfiguration configuration)
			throws CoreException {
		Server server = LaunchHelper.getServer(configuration);
		server.setServerState(ServerState.Starting);

		PingThread ping = new PingThread(server, -1);
		server.setData(ping);

	}

	@Override
	protected void onEnd(ILaunchConfiguration configuration,
			IProcess newProcess, boolean error) throws CoreException {
		Server server = LaunchHelper.getServer(configuration);
		if (!error) {
			addProcessListener(server, newProcess);
			// server.setServerState(ServerState.Started);
		} else {
			ServerLauncherManager.terminate(server);
		}
	}

	@Override
	protected String[] getArguments(ILaunchConfiguration configuration,
			MongoRuntime runtime) throws CoreException {
		Server server = LaunchHelper.getServer(configuration);
		Integer port = server.getPort();
		if (port != null) {
			return new String[] { "-port", port.toString() };
		}
		return null;
	}

	@Override
	protected IProcess createProcess(ILaunch launch, IPath location, Process p,
			Map processAttributes) {
		return new MongodProcess(launch, p, location.toOSString(),
				processAttributes);
	}

	@Override
	protected MongoRuntime getRuntime(ILaunchConfiguration configuration)
			throws CoreException {
		Server server = LaunchHelper.getServer(configuration);
		if (server != null) {
			MongoRuntime runtime = server.getRuntime();
			if (runtime != null) {
				return runtime;
			}
		}
		return null;
	}

	private void addProcessListener(final Server server,
			final IProcess newProcess) {
		IDebugEventSetListener processListener = server
				.getData(IDebugEventSetListener.class);
		if (processListener != null || newProcess == null)
			return;

		processListener = new IDebugEventSetListener() {
			public void handleDebugEvents(DebugEvent[] events) {
				if (events != null) {
					int size = events.length;
					for (int i = 0; i < size; i++) {
						if (newProcess != null
								&& newProcess.equals(events[i].getSource())
								&& events[i].getKind() == DebugEvent.TERMINATE) {
							ServerLauncherManager.stopImpl(server);
						}
					}
				}
			}
		};
		DebugPlugin.getDefault().addDebugEventListener(processListener);
	}

}
