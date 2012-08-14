package fr.opensagres.mongodb.ide.ui.views;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import fr.opensagres.mongodb.ide.core.IServerLifecycleListener;
import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.ServerEvent;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;

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

				if ((eventKind & ServerEvent.DATABASE_DROPPED) == ServerEvent.DATABASE_DROPPED) {
					// remove database
					removeDatabase(event.getDatabase());
				} else if ((eventKind & ServerEvent.DATABASE_CREATED) == ServerEvent.DATABASE_CREATED) {
					// add database
					addDatabase(event.getDatabase());
				} else if ((eventKind & ServerEvent.SERVER_CHANGE) != 0) {
					// refresh server state
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
				if (server.isConnected()) {
					ServerTreeViewer.this.expandToLevel(server, 1);
				}
			}
		});
	}

	private void addDatabase(final Database database) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					// Add the created database in the treeviewer
					ServerTreeViewer.this.add(database.getParent(), database);
					// Select the database
					ISelection sel = new StructuredSelection(database);
					ServerTreeViewer.this.setSelection(sel);
					// Expand the database tree item
					ServerTreeViewer.this.expandToLevel(database, 1);
				} catch (Exception e) {
					// ignore
				}
			}
		});
	}

	private void removeDatabase(final Database database) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					// Add the created database in the treeviewer
					ServerTreeViewer.this.remove(database);
					// Select the server
					ISelection sel = new StructuredSelection(database
							.getParent());
					ServerTreeViewer.this.setSelection(sel);
				} catch (Exception e) {
					// ignore
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
