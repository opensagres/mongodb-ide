package fr.opensagres.mongodb.ide.ui.actions;

import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.Users;
import fr.opensagres.mongodb.ide.ui.ServerUI;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class OpenAction extends AbstractTreeNodeAction {

	public OpenAction(ISelectionProvider selectionProvider) {
		super(selectionProvider, Messages.actionOpen);
	}

	@Override
	public boolean accept(Server server) {
		return true;
	}

	@Override
	public void perform(Server server) {
		ServerUI.editServer(server);
	}

	@Override
	public boolean accept(Database database) {
		return true;
	}

	@Override
	public void perform(Database database) {
		ServerUI.editDatabase(database);
	}

	@Override
	public void perform(Collection collection) {
		ServerUI.editCollection(collection);
	}

	@Override
	public boolean accept(Collection collection) {
		return true;
	}

	@Override
	public boolean accept(Users users) {
		return true;
	}

	@Override
	public void perform(Users users) {
		ServerUI.editUsers(users);
	}

}
