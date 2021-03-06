package fr.opensagres.mongodb.ide.ui.actions.server;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.actions.AbstractTreeNodeAction;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;

public class StartServerAction extends TreeNodeActionGroupAdapter {

	public StartServerAction(Shell shell, ISelectionProvider selectionProvider,
			List<Action> actions) {
		super(shell, selectionProvider, actions);
		setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_ELCL_START));
		setHoverImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_CLCL_START));
		setDisabledImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_DLCL_START));
	}

	/**
	 * Return true if this server can currently be acted on.
	 * 
	 * @return boolean
	 * @param server
	 *            a server
	 */
	@Override
	public boolean accept(Server server) {
		for (Action action : actions) {
			if (((AbstractTreeNodeAction) action).accept(server)) {
				return true;
			}
		}
		return false;
	}
}
