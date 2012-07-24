package fr.opensagres.mongodb.ide.launching.internal.jobs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.osgi.util.NLS;

import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.ServerEvent;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;
import fr.opensagres.mongodb.ide.launching.internal.Activator;
import fr.opensagres.mongodb.ide.launching.internal.Messages;
import fr.opensagres.mongodb.ide.launching.internal.ProgressUtil;
import fr.opensagres.mongodb.ide.launching.internal.ServerLauncherManager;
import fr.opensagres.mongodb.ide.launching.internal.Trace;

public class StartJob extends ServerJob {
	protected static final byte PUBLISH_NONE = 0;
	protected static final byte PUBLISH_BEFORE = 1;
	protected static final byte PUBLISH_AFTER = 2;

	protected String launchMode;

	public StartJob(Server server) {
		super(server, NLS.bind(Messages.jobStarting, server.getName()));
		setRule(server);
	}

	protected IStatus run(IProgressMonitor monitor) {
		IStatus stat = startImpl(monitor);
		return stat;
	}

	private IStatus startImpl(IProgressMonitor monitor) {
		final boolean[] notified = new boolean[1];
		monitor = ProgressUtil.getMonitorFor(monitor);

		final Server server = getServer();

		// add listener to the server
		IServerListener listener = new IServerListener() {
			public void serverChanged(ServerEvent event) {
				int eventKind = event.getKind();
				Server server = event.getServer();
				if (eventKind == (ServerEvent.SERVER_CHANGE | ServerEvent.STATE_CHANGE)) {
					ServerState state = server.getServerState();
					if (state == ServerState.Started
							|| state == ServerState.Stopped) {
						// notify waiter
						synchronized (notified) {
							try {
								if (Trace.FINEST) {
									Trace.trace(Trace.STRING_FINEST,
											"synchronousStart notify");
								}
								notified[0] = true;
								notified.notifyAll();
							} catch (Exception e) {
								if (Trace.SEVERE) {
									Trace.trace(Trace.STRING_SEVERE,
											"Error notifying server start",
											e);
								}
							}
						}
					}
				}
			}
		};
		server.addServerListener(listener);

		class Timer {
			boolean timeout;
			boolean alreadyDone;
		}
		final Timer timer = new Timer();

		final int serverTimeout = 5000;
		final IProgressMonitor monitor2 = monitor;
		Thread thread = new Thread("Server Start Timeout") {
			public void run() {
				try {
					int totalTimeout = serverTimeout;
					if (totalTimeout < 0)
						totalTimeout = 1;
					boolean userCancelled = false;
					int retryPeriod = 1000;
					while (!notified[0] && totalTimeout > 0
							&& !userCancelled && !timer.alreadyDone) {
						Thread.sleep(retryPeriod);
						if (serverTimeout > 0)
							totalTimeout -= retryPeriod;
						if (!notified[0] && !timer.alreadyDone
								&& monitor2.isCanceled()) {
							// user canceled - set the server state to
							// stopped
							userCancelled = true;
							ILaunch launch = getServer().getData(
									ILaunch.class);
							if (launch != null && !launch.isTerminated())
								launch.terminate();
							// notify waiter
							synchronized (notified) {
								if (Trace.FINEST) {
									Trace.trace(Trace.STRING_FINEST,
											"synchronousStart user cancelled");
								}
								notified[0] = true;
								notified.notifyAll();
							}
						}
					}
					if (!userCancelled && !timer.alreadyDone
							&& !notified[0]) {
						// notify waiter
						synchronized (notified) {
							if (Trace.FINEST) {
								Trace.trace(Trace.STRING_FINEST,
										"synchronousStart notify timeout");
							}
							if (!timer.alreadyDone && totalTimeout <= 0)
								timer.timeout = true;
							notified[0] = true;
							notified.notifyAll();
						}
					}
				} catch (Exception e) {
					if (Trace.SEVERE) {
						Trace.trace(Trace.STRING_SEVERE,
								"Error notifying server start timeout", e);
					}
				}
			}
		};

		if (Trace.FINEST) {
			Trace.trace(Trace.STRING_FINEST, "synchronousStart 2");
		}

		// start the server
		try {
			startImpl2(launchMode, monitor);

			thread.setDaemon(true);
			thread.start();
		} catch (CoreException e) {
			server.removeServerListener(listener);
			return e.getStatus();
		}
		if (monitor.isCanceled()) {
			server.removeServerListener(listener);
			timer.alreadyDone = true;
			return Status.CANCEL_STATUS;
		}

		if (Trace.FINEST) {
			Trace.trace(Trace.STRING_FINEST, "synchronousStart 3");
		}

		// wait for it! wait for it! ...
		synchronized (notified) {
			try {
				while (!notified[0]
						&& !monitor.isCanceled()
						&& !timer.timeout
						&& !(server.getServerState() == ServerState.Started || server
								.getServerState() == ServerState.Stopped)) {
					notified.wait();
				}
			} catch (Exception e) {
				if (Trace.SEVERE) {
					Trace.trace(Trace.STRING_SEVERE,
							"Error waiting for server start", e);
				}
			}
			timer.alreadyDone = true;
		}
		server.removeServerListener(listener);

		if (timer.timeout) {
			// stop(false);
			try {
				server.stop(false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
					NLS.bind(Messages.errorStartTimeout, new String[] {
							getName(), (serverTimeout / 1000) + "" }), null);
		}

		if (!monitor.isCanceled()
				&& server.getServerState() == ServerState.Stopped)
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
					NLS.bind(Messages.errorStartFailed, getName()), null);

		if (Trace.FINEST) {
			Trace.trace(Trace.STRING_FINEST, "synchronousStart 4");
		}
		return Status.OK_STATUS;
	}
	
	private void startImpl2(String launchMode2, IProgressMonitor monitor)
			throws CoreException {
		Server server = getServer();
		try {
			ILaunchConfiguration launchConfig = ServerLauncherManager.getLaunchConfiguration(
					server, true, monitor);
			if (launchConfig != null) {
				ILaunch launch = launchConfig.launch(
						ILaunchManager.RUN_MODE, monitor); // , true); -
				// causes
				// workspace
				// lock
				server.setData(launch);
			}

			if (Trace.FINEST) {
				Trace.trace(Trace.STRING_FINEST, "Launch: "
						+ getServer().getData(ILaunch.class));
			}
		} catch (CoreException e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE, "Error starting server "
						+ server.toString(), e);
			}
			throw e;
		}
	}
}