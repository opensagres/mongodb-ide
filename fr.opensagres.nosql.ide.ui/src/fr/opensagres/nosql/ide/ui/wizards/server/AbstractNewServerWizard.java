package fr.opensagres.nosql.ide.ui.wizards.server;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.ui.wizards.AbstractNewWizard;

public abstract class AbstractNewServerWizard extends AbstractNewWizard {

	@Override
	protected boolean doPerformFinish() throws Exception {
		IServer server = createServer();
		Platform.getServerManager().addServer(server);
		return true;
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	protected abstract IServer createServer();
}
