package fr.opensagres.mongodb.ide.ui.actions.collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.ui.actions.server.TreeNodeActionAdapter;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.WizardHelper;

public class NewCollectionAction extends TreeNodeActionAdapter {

	public NewCollectionAction(ISelectionProvider selectionProvider) {
		super(selectionProvider, Messages.NewCollectionAction_text);
		super.setToolTipText(Messages.NewCollectionAction_toolTipText);
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_COLLECTION_NEW_16));
	}

	public void run() {
//		try {
//			//WizardHelper.openWizard(NewCollectionWizard.ID);
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}
	}

}
