package fr.opensagres.mongodb.ide.ui.views;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import fr.opensagres.mongodb.ide.core.IServerLifecycleListener;
import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.ServerEvent;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;

/**
 * Tree view showing servers and their associations.
 */
public class ServerTreeViewer extends TreeViewer {

	protected static final String ROOT = "root";
	protected static Set<String> starting = new HashSet<String>(4);

	protected IServerLifecycleListener serverResourceListener;
	protected IServerListener serverListener;

	public ServerTreeViewer(Composite parent, int style) {
		super(parent, style);
	}

	protected void initialize() {
		addListeners();
	}

	protected void addListeners() {
		serverResourceListener = new IServerLifecycleListener() {
			public void serverAdded(Server server) {
				addServer(server);
				server.addServerListener(serverListener);
			}

			public void serverChanged(Server server) {
				refreshServer(server);
			}

			public void serverRemoved(Server server) {
				removeServer(server);
				server.removeServerListener(serverListener);
			}
		};
		Platform.getServerManager().addServerLifecycleListener(
				serverResourceListener);

		serverListener = new IServerListener() {
			public void serverChanged(ServerEvent event) {
				if (event == null)
					return;

				int eventKind = event.getKind();
				Server server = event.getServer();
				if ((eventKind & ServerEvent.SERVER_CHANGE) != 0) {
					// server change event
					refreshServer(server);					
				}
			}
		};

		// add listeners to servers
		List<Server> servers = Platform.getServerManager().getServers();
		for (Server server : servers) {
			server.addServerListener(serverListener);
		}
	}

	protected void addServer(final Server server) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				add(Platform.getServerManager(), server);
			}
		});
	}

	protected void removeServer(final Server server) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				remove(server);
			}
		});
	}

	protected void refreshServer(final Server server) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					refresh(server);
					ISelection sel = ServerTreeViewer.this.getSelection();
					ServerTreeViewer.this.setSelection(sel);
				} catch (Exception e) {
					// ignore
				}
			}

			private void refresh(Server server) {
				ServerTreeViewer.this.refresh(server);
				if (ServerState.Started.equals(server.getServerState())) {
					ServerTreeViewer.this.expandToLevel(server, 1);
				}
			}
		});
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		// remove listeners from server
		List<Server> servers = Platform.getServerManager().getServers();
		for (Server server : servers) {
			if (serverListener != null)
				server.removeServerListener(serverListener);
		}
		Platform.getServerManager().removeServerLifecycleListener(
				serverResourceListener);
		super.handleDispose(event);
	}
}
