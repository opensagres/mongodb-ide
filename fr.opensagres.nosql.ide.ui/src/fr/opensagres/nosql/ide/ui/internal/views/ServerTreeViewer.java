package fr.opensagres.nosql.ide.ui.internal.views;

import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import fr.opensagres.nosql.ide.core.IServerLifecycleListener;
import fr.opensagres.nosql.ide.core.IServerListener;
import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.ServerEvent;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.IDatabase;
import fr.opensagres.nosql.ide.core.model.IServer;

public class ServerTreeViewer extends TreeViewer {

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
			public void serverAdded(IServer server) {
				addServer(server);
				server.addServerListener(serverListener);
			}

			public void serverChanged(IServer server) {
				refreshServer(server);
			}

			public void serverRemoved(IServer server) {
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
				IServer server = event.getServer();

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
		List<IServer> servers = Platform.getServerManager().getServers();
		for (IServer server : servers) {
			server.addServerListener(serverListener);
		}
	}

	protected void addServer(final IServer server) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IServerType serverType = server.getServerType();
				add(serverType, server);
				ServerTreeViewer.this.expandToLevel(serverType, 1);
			}
		});
	}

	protected void removeServer(final IServer server) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				remove(server);
			}
		});
	}

	protected void refreshServer(final IServer server) {
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

			private void refresh(IServer server) {
				ServerTreeViewer.this.refresh(server);
				if (server.isConnected()) {
					ServerTreeViewer.this.expandToLevel(server, 1);
				}
			}
		});
	}

	private void addDatabase(final IDatabase database) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					// Add the created database in the treeviewer
					ServerTreeViewer.this.add(database.getServer(), database);
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

	private void removeDatabase(final IDatabase database) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					// Add the created database in the treeviewer
					ServerTreeViewer.this.remove(database);
					// Select the server
					ISelection sel = new StructuredSelection(database
							.getServer());
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
		List<IServer> servers = Platform.getServerManager().getServers();
		for (IServer server : servers) {
			if (serverListener != null)
				server.removeServerListener(serverListener);
		}
		Platform.getServerManager().removeServerLifecycleListener(
				serverResourceListener);
		super.handleDispose(event);
	}

}
