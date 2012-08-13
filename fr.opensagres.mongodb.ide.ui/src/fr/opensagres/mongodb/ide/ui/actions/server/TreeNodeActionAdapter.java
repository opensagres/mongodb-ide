package fr.opensagres.mongodb.ide.ui.actions.server;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.Users;
import fr.opensagres.mongodb.ide.ui.actions.AbstractTreeNodeAction;

public class TreeNodeActionAdapter extends AbstractTreeNodeAction {

	public TreeNodeActionAdapter(ISelectionProvider selectionProvider,
			String text) {
		super(selectionProvider, text);
	}

	public TreeNodeActionAdapter(Shell shell,
			ISelectionProvider selectionProvider, String text) {
		super(shell, selectionProvider, text);
	}

	@Override
	public boolean accept(Server server) {
		return false;
	}

	@Override
	public void perform(Server server) {
	}

	@Override
	public boolean accept(Database database) {
		return false;
	}

	@Override
	public void perform(Database database) {
	}

	@Override
	public boolean accept(Collection collection) {
		return false;
	}

	@Override
	public void perform(Collection collection) {
	}

	@Override
	public boolean accept(Users users) {
		return false;
	}

	@Override
	public void perform(Users users) {
	}

}
