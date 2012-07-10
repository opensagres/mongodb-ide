package fr.opensagres.mongodb.ide.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Server;

public class NewServerWizard extends Wizard implements INewWizard {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.wizards.NewServerWizard";

	private final NewServerWizardPage page;

	public NewServerWizard() {
		super();
		super.setNeedsProgressMonitor(true);
		page = new NewServerWizardPage();
	}

	@Override
	public void addPages() {
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		Server server = new Server(page.getName(), page.getHost(),
				page.getPort());
		try {
			Platform.getServerManager().addServer(server);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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

}
