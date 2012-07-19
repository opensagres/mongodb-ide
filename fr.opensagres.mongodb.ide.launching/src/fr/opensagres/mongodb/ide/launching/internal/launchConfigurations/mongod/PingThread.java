package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongod;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;
import fr.opensagres.mongodb.ide.launching.internal.Trace;
/**
 * Thread used to ping server to test when it is started.
 */
public class PingThread {
	// delay before pinging starts
	private static final int PING_DELAY = 2000;

	// delay between pings
	private static final int PING_INTERVAL = 250;

	// maximum number of pings before giving up
	private int maxPings;

	private boolean stop = false;
	private Server server;

	/**
	 * Create a new PingThread.
	 * 
	 * @param server
	 * @param url
	 * @param maxPings the maximum number of times to try pinging, or -1 to continue forever
	 * @param behaviour
	 */
	public PingThread(Server server, int maxPings) {
		super();
		this.server = server;
		this.maxPings = maxPings;
		Thread t = new Thread("MongoDB Ping Thread") {
			public void run() {
				ping();
			}
		};
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Ping the server until it is started. Then set the server
	 * state to STATE_STARTED.
	 */
	protected void ping() {
		int count = 0;
		try {
			Thread.sleep(PING_DELAY);
		} catch (Exception e) {
			// ignore
		}
		while (!stop) {
			try {
				if (count == maxPings) {
					try {
						server.stop(false);
					} catch (Exception e) {
						Trace.trace(Trace.STRING_FINEST, "Ping: could not stop server");
					}
					stop = true;
					break;
				}
				count++;
				
				Trace.trace(Trace.STRING_FINEST, "Ping: pinging " + count);
				server.getMongo().getDatabaseNames();
				
				// ping worked - server is up
				if (!stop) {
					Trace.trace(Trace.STRING_FINEST, "Ping: success");
					Thread.sleep(200);
					//behaviour.setServerStarted();
					server.setServerState(ServerState.Started);
				}
				stop = true;
			}
//			} catch (FileNotFoundException fe) {
//				try {
//					Thread.sleep(200);
//				} catch (Exception e) {
//					// ignore
//				}
//				//behaviour.setServerStarted();
//				server.setServerState(ServerState.Started);
//				stop = true;
//			} 
			catch (Exception e) {
				Trace.trace(Trace.STRING_FINEST, "Ping: failed");
				// pinging failed
				if (!stop) {
					try {
						Thread.sleep(PING_INTERVAL);
					} catch (InterruptedException e2) {
						// ignore
					}
				}
			}
		}
	}

	/**
	 * Tell the pinging to stop.
	 */
	public void stop() {
		Trace.trace(Trace.STRING_FINEST, "Ping: stopping");
		stop = true;
	}
}
