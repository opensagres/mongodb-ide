package fr.opensagres.mongodb.ide.core.internal;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;

import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.IServerManager;
import fr.opensagres.mongodb.ide.core.internal.settings.ServersSettings;
import fr.opensagres.mongodb.ide.core.model.Server;

public class ServerManager extends AbstractManager<Server> implements
		IServerManager {

	private static final long serialVersionUID = -8525280179973199825L;

	private final ListenerList listeners;

	public ServerManager() {
		super("servers.xml", ServersSettings.getInstance());
		this.listeners = new ListenerList();
	}

	public void addServer(Server server) throws Exception {
		super.add(server);
		super.save();
		processListeners(server, true);
	}

	public void removeServer(Server server) throws Exception {
		super.remove(server);
		super.save();
		processListeners(server, false);
		server.dispose();
	}

	private void processListeners(Server server, boolean start) {
		Object[] changeListeners = this.listeners.getListeners();
		if (changeListeners.length == 0)
			return;
		for (int i = 0; i < changeListeners.length; ++i) {
			final IServerListener l = (IServerListener) changeListeners[i];
			if (start) {
				l.serverAdded(server);
			} else {
				l.serverRemoved(server);
			}
		}
	}

	public List<Server> getServers() {
		return this;
	}

	public void addListener(IServerListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IServerListener listener) {
		listeners.remove(listener);
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
		for (Server server : this) {
			if (serverId.equals(server.getName())) {
				return server;
			}
		}
		return null;
	}

}
