package fr.opensagres.mongodb.ide.launching.internal.jobs;

import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;

import com.mongodb.MongoException;
import com.mongodb.tools.driver.MongoDriverHelper;

import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.ServerEvent;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;
import fr.opensagres.mongodb.ide.launching.internal.Activator;
import fr.opensagres.mongodb.ide.launching.internal.Messages;
import fr.opensagres.mongodb.ide.launching.internal.ServerLauncherManager;
import fr.opensagres.mongodb.ide.launching.internal.Trace;

public class StopJob extends ServerJob {
	protected boolean force;

	public StopJob(Server server, boolean force) {
		super(server, NLS.bind(Messages.jobStopping, server.getName()));
		setRule(server);
		this.force = force;
	}

	protected IStatus run(IProgressMonitor monitor) {
		return stopImpl(getServer(), force, monitor);
	}

	protected IStatus stopImpl(final Server s, boolean force,
			IProgressMonitor monitor) {
		final Object mutex = new Object();

		// add listener to the server
		IServerListener listener = new IServerListener() {
			public void serverChanged(ServerEvent event) {
				int eventKind = event.getKind();
				Server server = event.getServer();
				if (eventKind == (ServerEvent.SERVER_CHANGE | ServerEvent.STATE_CHANGE)) {
					ServerState state = server.getServerState();
					if (s == server
							&& (state == ServerState.Stopped || state == ServerState.Started)) {
						// notify waiter
						synchronized (mutex) {
							try {
								mutex.notifyAll();
							} catch (Exception e) {
								if (Trace.SEVERE) {
									Trace.trace(Trace.STRING_SEVERE,
											"Error notifying server stop", e);
								}
							}
						}
					}
				}
			}
		};
		s.addServerListener(listener);

		class Timer {
			boolean timeout;
			boolean alreadyDone;
		}
		final Timer timer = new Timer();

		final int serverTimeout = s.getStopTimeout() * 1000;
		Thread thread = null;
		if (serverTimeout > 0) {
			thread = new Thread("Server Stop Timeout") {
				public void run() {
					try {
						int totalTimeout = serverTimeout;
						if (totalTimeout < 0)
							totalTimeout = 1;

						int retryPeriod = 1000;

						while (totalTimeout > 0 && !timer.alreadyDone) {
							Thread.sleep(retryPeriod);
							if (serverTimeout > 0)
								totalTimeout -= retryPeriod;
						}

						if (!timer.alreadyDone) {
							timer.timeout = true;
							// notify waiter
							synchronized (mutex) {
								if (Trace.FINEST) {
									Trace.trace(Trace.STRING_FINEST,
											"stop notify timeout");
								}
								mutex.notifyAll();
							}
						}
					} catch (Exception e) {
						if (Trace.SEVERE) {
							Trace.trace(Trace.STRING_SEVERE,
									"Error notifying server stop timeout", e);
						}
					}
				}
			};
		}

		// stop the server
		stopImpl2(s, force);

		if (thread != null) {
			thread.setDaemon(true);
			thread.start();
		}

		// wait for it! wait for it!
		synchronized (mutex) {
			try {
				while (!timer.timeout
						&& s.getServerState() != ServerState.Stopped
						&& s.getServerState() != ServerState.Started)
					mutex.wait();
			} catch (Exception e) {
				if (Trace.SEVERE) {
					Trace.trace(Trace.STRING_SEVERE,
							"Error waiting for server stop", e);
				}
			}
			timer.alreadyDone = true;
		}
		s.removeServerListener(listener);

		// can't throw exceptions
		/*
		 * if (timer.timeout) return new Status(IStatus.ERROR,
		 * ServerPlugin.PLUGIN_ID, 0, Messages.errorStopTimeout, getName()),
		 * null); else timer.alreadyDone = true;
		 */
		if (!monitor.isCanceled() && s.getServerState() == ServerState.Started)
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, NLS.bind(
					Messages.errorStopFailed, s.getName()), null);

		return Status.OK_STATUS;
	}

	protected void stopImpl2(Server server, boolean force) {
		if (server.getServerState() == ServerState.Stopped)
			return;

		if (Trace.FINEST) {
			Trace.trace(Trace.STRING_FINEST,
					"Stopping server: " + server.toString());
		}

		try {
			stop3(server, force);
		} catch (RuntimeException e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE,
						"Error calling delegate stop() " + server.toString(), e);
			}
			throw e;
		} catch (Throwable t) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE,
						"Error calling delegate stop() " + server.toString(), t);
			}
			throw new RuntimeException(t);
		}
	}

	public void stop3(Server server, boolean force) {
		if (force) {
			ServerLauncherManager.terminate(server);
			return;
		}
		ServerState state = server.getServerState();
		// If stopped or stopping, no need to run stop command again
		if (state == ServerState.Stopped || state == ServerState.Stopping)
			return;
		else if (state == ServerState.Starting) {
			ServerLauncherManager.terminate(server);
			return;
		}
		if (state != ServerState.Stopped)
			server.setServerState(ServerState.Stopping);

		try {
			MongoDriverHelper.stopMongoServerAndCloseIt(server.getMongo(),
					server.getUsername(), server.getPassword());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} finally {
			server.setServerState(ServerState.Stopped);
		}

	}

}
