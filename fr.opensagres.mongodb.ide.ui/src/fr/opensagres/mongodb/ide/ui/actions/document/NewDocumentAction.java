package fr.opensagres.mongodb.ide.ui.actions.document;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.actions.server.TreeNodeActionAdapter;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.WizardHelper;
import fr.opensagres.mongodb.ide.ui.wizards.document.NewDocumentWizard;

public class NewDocumentAction extends TreeNodeActionAdapter {

	public NewDocumentAction(ISelectionProvider selectionProvider) {
		super(selectionProvider, Messages.NewDocumentAction_text);
		super.setToolTipText(Messages.NewDocumentAction_toolTipText);
		// super.setImageDescriptor(ImageResources
		// .getImageDescriptor(ImageResources.IMG_DOCUMENT_NEW_16));
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
			WizardHelper.openWizard(NewDocumentWizard.ID,
					getSelectionProvider());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
