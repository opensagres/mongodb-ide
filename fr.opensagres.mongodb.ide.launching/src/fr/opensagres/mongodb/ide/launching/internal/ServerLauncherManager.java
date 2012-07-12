package fr.opensagres.mongodb.ide.launching.internal;

import java.net.UnknownHostException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.osgi.util.NLS;

import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.IServerLauncherManager;
import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.ServerEvent;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;
import fr.opensagres.mongodb.ide.core.utils.MongoHelper;

public class ServerLauncherManager implements IServerLauncherManager {

	protected static final char[] INVALID_CHARS = new char[] { '\\', '/', ':',
			'*', '?', '"', '<', '>', '|', '\0', '@', '&' };

	public void start(Server server) throws Exception {
		StartJob startJob = new StartJob(server);
		startJob.schedule();
		// startJob.join();

	}

	public void stop(Server server, boolean force) throws Exception {
		/*
		 * StopJob job = new StopJob(force); job.schedule();
		 */
		if (server.getServerState() == ServerState.Stopped)
			return;

		StopJob job = new StopJob(server, force);
		job.schedule();
	}

	private abstract class ServerJob extends Job {
		private final Server server;

		public ServerJob(Server server, String name) {
			super(name);
			this.server = server;
		}

		// public boolean belongsTo(Object family) {
		// return ServerUtil.SERVER_JOB_FAMILY.equals(family);
		// }

		public Server getServer() {
			return server;
		}
	}

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
				ILaunchConfiguration launchConfig = getLaunchConfiguration(
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
	public ILaunchConfiguration getLaunchConfiguration(Server server,
			boolean create, IProgressMonitor monitor) throws CoreException {
		ILaunchConfigurationType launchConfigType = LaunchHelper
				.getMongodLaunchConfigurationType();
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

		if (launchConfigs != null) {
			int size = launchConfigs.length;
			for (int i = 0; i < size; i++) {
				try {
					String serverId = launchConfigs[i].getAttribute(
							LaunchHelper.ATTR_SERVER_ID, (String) null);
					if (server.getId().equals(serverId)) {
						final ILaunchConfigurationWorkingCopy wc = launchConfigs[i]
								.getWorkingCopy();
						setupLaunchConfiguration(wc, monitor);
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

		if (!create)
			return null;

		// create a new launch configuration
		String launchName = getValidLaunchConfigurationName(server.getName());
		launchName = launchManager
				.generateUniqueLaunchConfigurationNameFrom(launchName);
		ILaunchConfigurationWorkingCopy wc = launchConfigType.newInstance(null,
				launchName);
		wc.setAttribute(LaunchHelper.ATTR_SERVER_ID, server.getId());
		setupLaunchConfiguration(wc, monitor);
		return wc.doSave();
	}

	protected String getValidLaunchConfigurationName(String s) {
		if (s == null || s.length() == 0)
			return "1";
		int size = INVALID_CHARS.length;
		for (int i = 0; i < size; i++) {
			s = s.replace(INVALID_CHARS[i], '_');
		}
		return s;
	}

	public void setupLaunchConfiguration(
			ILaunchConfigurationWorkingCopy workingCopy,
			IProgressMonitor monitor) {
		try {
			// getBehaviourDelegate(monitor).setupLaunchConfiguration(workingCopy,
			// monitor);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE,
						"Error calling delegate setupLaunchConfiguration() "
								+ toString(), e);
			}
		}
	}

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
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
					NLS.bind(Messages.errorStopFailed, s.getName()), null);

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
				Trace.trace(
						Trace.STRING_SEVERE,
						"Error calling delegate stop() "
								+ server.toString(), e);
			}
			throw e;
		} catch (Throwable t) {
			if (Trace.SEVERE) {
				Trace.trace(
						Trace.STRING_SEVERE,
						"Error calling delegate stop() "
								+ server.toString(), t);
			}
			throw new RuntimeException(t);
		}
	}
	
	public void stop3(Server server, boolean force) {
		if (force) {
			terminate(server);
			return;
		}
		ServerState state = server.getServerState();
		// If stopped or stopping, no need to run stop command again
		if (state == ServerState.Stopped ||  state == ServerState.Stopping)
			return;
		else if (state == ServerState.Starting) {
			terminate(server);
			return;
		}
		if (state != ServerState.Stopped )
			server.setServerState(ServerState.Stopping);
		
		try {
			MongoHelper.stopMongoServerAndCloseIt(server.getMongo(),
					server.getUserName(), server.getPassword());			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		finally {
			server.setServerState(ServerState.Stopped);
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
//			if (Trace.isTraceEnabled())
//				Trace.trace(Trace.FINER, "Killing the Tomcat process");
			ILaunch launch = server.getData(ILaunch.class);
			if (launch != null) {
				launch.terminate();
				stopImpl(server);
			}
		} catch (Exception e) {
			//Trace.trace(Trace.SEVERE, "Error killing the process", e);
		}
	}
	
	public static void stopImpl(Server server) {
		IDebugEventSetListener processListener = server.getData(IDebugEventSetListener.class);
		if (processListener != null) {
			DebugPlugin.getDefault().removeDebugEventListener(processListener);
			processListener = null;
		}
		server.setServerState(ServerState.Stopped);
	}
	
}
