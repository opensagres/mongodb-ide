package fr.opensagres.mongodb.ide.core.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;

import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.IServerManager;
import fr.opensagres.mongodb.ide.core.model.Server;

public class ServerManager extends ArrayList<Server> implements IServerManager {

	private static final long serialVersionUID = -8525280179973199825L;

	private final ListenerList listeners;

	public ServerManager() {
		this.listeners = new ListenerList();
	}

	public void addServer(Server server) {
		super.add(server);
		processListeners(server, true);
	}

	public void removeServer(Server server) {
		super.remove(server);
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

}
