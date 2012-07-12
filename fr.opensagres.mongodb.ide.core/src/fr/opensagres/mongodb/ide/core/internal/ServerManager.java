package fr.opensagres.mongodb.ide.core.internal;

import java.util.ArrayList;
import java.util.List;

import fr.opensagres.mongodb.ide.core.IServerLifecycleListener;
import fr.opensagres.mongodb.ide.core.IServerManager;
import fr.opensagres.mongodb.ide.core.internal.settings.ServersSettings;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;

public class ServerManager extends AbstractManager<Server> implements
		IServerManager {

	private static final long serialVersionUID = -8525280179973199825L;

	private static final byte EVENT_ADDED = 0;
	private static final byte EVENT_CHANGED = 1;
	private static final byte EVENT_REMOVED = 2;

	private final List<IServerLifecycleListener> serverListeners;

	public ServerManager() {
		super("servers.xml", ServersSettings.getInstance());
		this.serverListeners = new ArrayList<IServerLifecycleListener>();
	}

	public void addServer(Server server) throws Exception {
		if (server == null)
			return;
		
		if (!super.contains(server)) {
			super.add(server);
			fireServerEvent(server, EVENT_ADDED);
		}
		else
			fireServerEvent(server, EVENT_CHANGED);		
		super.save();		
	}

	public void removeServer(Server server) throws Exception {
		super.remove(server);
		super.save();
		fireServerEvent(server, EVENT_REMOVED);
		server.dispose();
	}

	public List<Server> getServers() {
		return this;
	}

	public void addServerLifecycleListener(IServerLifecycleListener listener) {
		if (Trace.LISTENERS) {
			Trace.trace(Trace.STRING_LISTENERS,
					"Adding server lifecycle listener " + listener + " to "
							+ this);
		}

		synchronized (serverListeners) {
			serverListeners.add(listener);
		}
	}

	/*
	 *
	 */
	public void removeServerLifecycleListener(IServerLifecycleListener listener) {
		if (Trace.LISTENERS) {
			Trace.trace(Trace.STRING_LISTENERS,
					"Removing server lifecycle listener " + listener + " from "
							+ this);
		}

		synchronized (serverListeners) {
			serverListeners.remove(listener);
		}
	}

	/**
	 * Fire a server event.
	 */
	private void fireServerEvent(final Server server, byte b) {
		if (Trace.LISTENERS) {
			Trace.trace(Trace.STRING_LISTENERS, "->- Firing server event: "
					+ server.getName() + " ->-");
		}

		if (serverListeners.isEmpty())
			return;

		List<IServerLifecycleListener> clone = new ArrayList<IServerLifecycleListener>();
		clone.addAll(serverListeners);
		for (IServerLifecycleListener srl : clone) {
			if (Trace.LISTENERS) {
				Trace.trace(Trace.STRING_LISTENERS, "  Firing server event to "
						+ srl);
			}
			try {
				if (b == EVENT_ADDED)
					srl.serverAdded(server);
				else if (b == EVENT_CHANGED)
					srl.serverChanged(server);
				else if (b == EVENT_REMOVED)
					srl.serverRemoved(server);
			} catch (Exception e) {
				if (Trace.SEVERE) {
					Trace.trace(Trace.STRING_SEVERE,
							"  Error firing server event to " + srl, e);
				}
			}
		}
		if (Trace.LISTENERS) {
			Trace.trace(Trace.STRING_LISTENERS,
					"-<- Done firing server event -<-");
		}
	}

	public void dispose() {
		for (Server server : this) {
			try {
				server.dispose();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}

	public Server findServer(String serverId) {
		if (StringUtils.isEmpty(serverId)) {
			return null;
		}
		for (Server server : this) {
			if (serverId.equals(server.getId())) {
				return server;
			}
		}
		return null;
	}

}
