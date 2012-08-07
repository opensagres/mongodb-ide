package fr.opensagres.mongodb.ide.ui.actions.server;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;

public class StopShellAction extends TreeNodeActionGroupAdapter {

	public StopShellAction(Shell shell, ISelectionProvider selectionProvider,
			List<Action> actions) {
		super(shell, selectionProvider, actions);
		setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_ELCL_STOP_SHELL));
		setHoverImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_CLCL_STOP_SHELL));
		setDisabledImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_DLCL_STOP_SHELL));
	}

	/**
	 * Return true if this database can currently be acted on.
	 * 
	 * @return boolean
	 * @param database
	 *            a database
	 */
	@Override
	protected boolean accept(Database database) {
		for (Action action : actions) {
			if (((AbstractTreeNodeAction) action).accept(database)) {
				return true;
			}
		}
		return false;
	}

}
