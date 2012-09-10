package fr.opensagres.nosql.ide.orientdb.ui.internal.handlers;

import org.eclipse.core.commands.ExecutionEvent;

import fr.opensagres.nosql.ide.orientdb.ui.wizards.server.NewServerWizard;
import fr.opensagres.nosql.ide.ui.handlers.ContextHandlerEvent;
import fr.opensagres.nosql.ide.ui.handlers.OpenWizardHandler;
import fr.opensagres.nosql.ide.ui.wizards.AbstractWizard;

public class NewServerWizardHandler extends OpenWizardHandler {

	public static final String ID = "fr.opensagres.nosql.ide.orientdb.ui.handlers.NewServerWizardHandler";

	@Override
	protected AbstractWizard createWizard(ExecutionEvent event,
			ContextHandlerEvent contextEvent) {
		NewServerWizard wizard = new NewServerWizard();
		return wizard;
	}

}
