package fr.opensagres.mongodb.ide.ui.actions.database;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.actions.AbstractTreeNodeAction;
import fr.opensagres.mongodb.ide.ui.actions.server.TreeNodeActionGroupAdapter;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;

public class StartShellAction extends TreeNodeActionGroupAdapter {

	public StartShellAction(Shell shell, ISelectionProvider selectionProvider,
			List<Action> actions) {
		super(shell, selectionProvider, actions);
		setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_ELCL_START_SHELL));
		setHoverImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_CLCL_START_SHELL));
		setDisabledImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_DLCL_START_SHELL));
	}

	/**
	 * Return true if this server can currently be acted on.
	 * 
	 * @return boolean
	 * @param server
	 *            a server
	 */
	@Override
	public boolean accept(Database database) {
		for (Action action : actions) {
			if (((AbstractTreeNodeAction) action).accept(database)) {
				return true;
			}
		}
		return false;
	}
}
