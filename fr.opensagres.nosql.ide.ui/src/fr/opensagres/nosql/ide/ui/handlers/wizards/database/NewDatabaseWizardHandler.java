package fr.opensagres.nosql.ide.ui.handlers.wizards.database;

import org.eclipse.core.commands.ExecutionEvent;

import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.ui.handlers.ContextHandlerEvent;
import fr.opensagres.nosql.ide.ui.handlers.OpenWizardHandler;
import fr.opensagres.nosql.ide.ui.wizards.AbstractWizard;
import fr.opensagres.nosql.ide.ui.wizards.database.NewDatabaseWizard;

public class NewDatabaseWizardHandler extends OpenWizardHandler {

	public static final String ID = "fr.opensagres.nosql.ide.ui.handlers.wizards.database.NewDatabaseWizardHandler";

	@Override
	protected AbstractWizard createWizard(ExecutionEvent event,
			ContextHandlerEvent contextEvent) {
		NewDatabaseWizard wizard = new NewDatabaseWizard();
		wizard.init((ITreeSimpleNode) contextEvent.getModel());
		return wizard;
	}

}
