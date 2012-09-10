package fr.opensagres.nosql.ide.ui.internal.actions.database;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.services.IServiceLocator;

import fr.opensagres.nosql.ide.core.extensions.ICommandIdProvider;
import fr.opensagres.nosql.ide.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.internal.actions.AbstractCommandAction;

public class NewDatabaseAction extends AbstractCommandAction {

	public NewDatabaseAction(ISelectionProvider provider,
			IServiceLocator serviceLocator, Shell parentShell) {
		super(ICommandIdProvider.OPEN_NEW_WIZARD, provider,
				Messages.NewDatabaseAction_text, serviceLocator, parentShell);
	}

}
