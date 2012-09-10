package fr.opensagres.nosql.ide.ui.internal.actions.database;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.services.IServiceLocator;

import fr.opensagres.nosql.ide.core.extensions.ICommandIdProvider;
import fr.opensagres.nosql.ide.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.internal.actions.AbstractCommandAction;

public class NewDocumentAction extends AbstractCommandAction {

	public NewDocumentAction(ISelectionProvider provider,
			IServiceLocator serviceLocator, Shell parentShell) {
		super(ICommandIdProvider.OPEN_NEW_WIZARD, provider,
				Messages.NewDocumentAction_text, serviceLocator, parentShell);
	}

}
