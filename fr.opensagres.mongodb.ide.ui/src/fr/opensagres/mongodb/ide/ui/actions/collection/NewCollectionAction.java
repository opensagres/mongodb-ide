package fr.opensagres.mongodb.ide.ui.actions.collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.actions.server.TreeNodeActionAdapter;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.WizardHelper;
import fr.opensagres.mongodb.ide.ui.wizards.collection.NewCollectionWizard;

public class NewCollectionAction extends TreeNodeActionAdapter {

	public NewCollectionAction(ISelectionProvider selectionProvider) {
		super(selectionProvider, Messages.NewCollectionAction_text);
		super.setToolTipText(Messages.NewCollectionAction_toolTipText);
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_COLLECTION_NEW_16));
	}

	@Override
	public boolean accept(Database database) {
		return database.getParent().isConnected();
	}

	@Override
	public boolean accept(Collection collection) {
		return accept(collection.getDatabase());
	}
	
	@Override
	public void run() {
		try {
			WizardHelper.openWizard(NewCollectionWizard.ID,
					getSelectionProvider());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
