package fr.opensagres.mongodb.ide.ui.actions.server;

import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.core.extensions.IServerRunnerType;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;

public class ServerRunnerAction extends TreeNodeActionAdapter {

	private final IServerRunnerType serverRunnerType;
	private final boolean start;

	public ServerRunnerAction(IServerRunnerType serverRunnerType,
			boolean start, ISelectionProvider selectionProvider) {
		super(selectionProvider, start ? serverRunnerType.getStartName()
				: serverRunnerType.getStopName());
		this.start = start;
		this.serverRunnerType = serverRunnerType;
	}

	@Override
	protected boolean accept(Server server) {
		if (!serverRunnerType.getRunner().canSupport(server)) {
			return false;
		}
		if (start) {
			return canStartServer(server);
		}
		return canStopServer(server);
	}

	private boolean canStartServer(Server server) {
		if (server.getServerState() == ServerState.Connected) {
			return false;
		}
		return true;
	}

	private boolean canStopServer(Server server) {
		ServerState state = server.getServerState();
		if (state == ServerState.Stopped || state == ServerState.Stopping
				|| state == ServerState.Disconnected) {
			return false;
		}
		return true;
	}

	@Override
	protected void perform(Server server) {
		try {
			if (start) {
				serverRunnerType.getRunner().start(server);

			} else {
				serverRunnerType.getRunner().stop(server, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
