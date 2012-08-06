package fr.opensagres.mongodb.ide.ui.actions.server;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.Users;

public class TreeNodeActionGroupAdapter extends
		AbstractTreeNodeActionGroup {

	public TreeNodeActionGroupAdapter(Shell shell,
			ISelectionProvider selectionProvider, List<Action> actions) {
		super(shell, selectionProvider, actions);
	}

	@Override
	protected boolean accept(Server server) {
		return false;
	}

	@Override
	protected void perform(Server server) {
	}

	@Override
	protected boolean accept(Database database) {
		return false;
	}

	@Override
	protected void perform(Database database) {
	}

	@Override
	protected boolean accept(Collection collection) {
		return false;
	}

	@Override
	protected void perform(Collection collection) {
	}

	@Override
	protected boolean accept(Users users) {
		return false;
	}

	@Override
	protected void perform(Users users) {
	}
}
