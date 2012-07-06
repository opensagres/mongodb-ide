package fr.opensagres.mongodb.ide.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;

import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.NewServerWizard;
import fr.opensagres.mongodb.ide.ui.wizards.WizardHelper;

public class NewServerAction extends Action {

	public NewServerAction() {
		super.setText(Messages.NewServerAction_text);
		super.setToolTipText(Messages.NewServerAction_toolTipText);
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_SERVER_NEW_16));
	}

	public void run() {
		try {
			WizardHelper.openWizard(NewServerWizard.ID);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
