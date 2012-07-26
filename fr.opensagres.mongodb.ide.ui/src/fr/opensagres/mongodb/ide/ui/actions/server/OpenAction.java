package fr.opensagres.mongodb.ide.ui.actions.server;

import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.ServerUI;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class OpenAction extends AbstractServerAction {

	public OpenAction(ISelectionProvider selectionProvider) {
		super(selectionProvider, Messages.actionOpen);
	}

	@Override
	public void perform(Server server) {
		ServerUI.editServer(server);
	}

	@Override
	public void perform(Database database) {
		ServerUI.editDatabase(database);
	}

}
