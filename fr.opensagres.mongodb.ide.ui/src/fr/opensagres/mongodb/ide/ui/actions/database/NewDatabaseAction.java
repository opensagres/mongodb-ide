package fr.opensagres.mongodb.ide.ui.actions.database;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.Users;
import fr.opensagres.mongodb.ide.ui.actions.server.TreeNodeActionAdapter;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.WizardHelper;
import fr.opensagres.mongodb.ide.ui.wizards.database.NewDatabaseWizard;

public class NewDatabaseAction extends TreeNodeActionAdapter {

	public NewDatabaseAction(ISelectionProvider selectionProvider) {
		super(selectionProvider, Messages.NewDatabaseAction_text);
		super.setToolTipText(Messages.NewDatabaseAction_toolTipText);
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_DATABASE_NEW_16));
	}

	@Override
	public boolean accept(Server server) {
		return server.isConnected();
	}

	@Override
	public boolean accept(Database database) {
		return accept(database.getParent());
	}

	@Override
	public boolean accept(Collection collection) {
		return accept(collection.getDatabase());
	}

	@Override
	public boolean accept(Users users) {
		return accept(users.getParent());
	}

	@Override
	public void run() {
		try {
			WizardHelper.openWizard(NewDatabaseWizard.ID,
					getSelectionProvider());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
