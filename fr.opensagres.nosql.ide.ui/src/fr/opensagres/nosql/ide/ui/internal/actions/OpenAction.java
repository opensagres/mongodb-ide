package fr.opensagres.nosql.ide.ui.internal.actions;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.services.IServiceLocator;

import fr.opensagres.nosql.ide.core.extensions.ICommandIdProvider;
import fr.opensagres.nosql.ide.ui.internal.Messages;

public class OpenAction extends AbstractCommandAction {

	public OpenAction(ISelectionProvider provider,
			IServiceLocator serviceLocator, Shell parentShell) {
		super(ICommandIdProvider.OPEN_EDITOR, provider, Messages.actionOpen,
				serviceLocator, parentShell);
	}

}
