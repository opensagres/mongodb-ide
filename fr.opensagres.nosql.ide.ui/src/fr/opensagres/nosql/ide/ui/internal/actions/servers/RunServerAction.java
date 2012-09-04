package fr.opensagres.nosql.ide.ui.internal.actions.servers;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.ui.internal.ImageResources;
import fr.opensagres.nosql.ide.ui.internal.actions.AbstractTreeNodeActionGroup;

public class RunServerAction extends AbstractTreeNodeActionGroup {

	private final Map<IServerType, Collection<Action>> actions;

	public RunServerAction(boolean start, ISelectionProvider provider,
			Map<IServerType, Collection<Action>> actions) {
		super(provider, "");
		this.actions = actions;
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
	protected Collection<Action> getActions(Object obj) {
		if (obj instanceof IServer) {
			IServerType serverType = ((IServer) obj).getServerType();
			return actions.get(serverType);
		}
		return Collections.emptyList();
	}

	@Override
	protected Action getDefaultAction(Object obj) {
		if (obj instanceof IServer) {
			// IServerType serverType = ((IServer) obj).getServerType();
			// return defaultActions.get(serverType);
			return null;
		}
		return null;
	}
}
