package fr.opensagres.mongodb.ide.ui.actions.server;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.Users;
import fr.opensagres.mongodb.ide.ui.actions.AbstractTreeNodeActionGroup;

public class TreeNodeActionGroupAdapter extends
		AbstractTreeNodeActionGroup {

	public TreeNodeActionGroupAdapter(Shell shell,
			ISelectionProvider selectionProvider, List<Action> actions) {
		super(shell, selectionProvider, actions);
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
