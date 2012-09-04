package fr.opensagres.nosql.ide.ui.internal.actions.servers;

import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.nosql.ide.core.extensions.IServerRunnerType;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.model.ServerState;
import fr.opensagres.nosql.ide.ui.internal.ImageResources;
import fr.opensagres.nosql.ide.ui.internal.actions.AbstractTreeNodeAction;

public class ServerRunnerAction extends AbstractTreeNodeAction {

	private final IServerRunnerType serverRunnerType;
	private final boolean start;

	public ServerRunnerAction(IServerRunnerType serverRunnerType,
			boolean start, ISelectionProvider selectionProvider) {
		super(selectionProvider, start ? serverRunnerType.getStartName()
				: serverRunnerType.getStopName());
		this.start = start;
		this.serverRunnerType = serverRunnerType;
		if (start) {
			setImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_ELCL_START));
			setHoverImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_CLCL_START));
			setDisabledImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_DLCL_START));
		} else {
			setImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_ELCL_STOP));
			setHoverImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_CLCL_STOP));
			setDisabledImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_DLCL_STOP));
		}
	}

	@Override
	public boolean accept(Object obj) {
		if (!(obj instanceof IServer)) {
			return false;
		}
		IServer server = (IServer) obj;
		if (!serverRunnerType.getRunner().canSupport(server)) {
			return false;
		}
		if (start) {
			return canStartServer(server);
		}
		return canStopServer(server);
	}

	private boolean canStartServer(IServer server) {
		if (server.getServerState() == ServerState.Connected) {
			return false;
		}
		return true;
	}

	private boolean canStopServer(IServer server) {
		ServerState state = server.getServerState();
		if (state == ServerState.Stopped || state == ServerState.Stopping
				|| state == ServerState.Disconnected) {
			return false;
		}
		return true;
	}

	@Override
	public void perform(Object obj) {
		IServer server = (IServer) obj;
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
