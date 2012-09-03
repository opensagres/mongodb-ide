package fr.opensagres.nosql.ide.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class WizardHelper {

	public static IWizard openWizard(String id) throws CoreException {
		return openWizard(id, null);

	}

	public static IWizard openWizard(String id, ISelectionProvider selection)
			throws CoreException {
		// First see if this is a "new wizard".
		IWizardDescriptor descriptor = PlatformUI.getWorkbench()
				.getNewWizardRegistry().findWizard(id);
		// If not check if it is an "import wizard".
		if (descriptor == null) {
			descriptor = PlatformUI.getWorkbench().getImportWizardRegistry()
					.findWizard(id);
		}
		// Or maybe an export wizard
		if (descriptor == null) {
			descriptor = PlatformUI.getWorkbench().getExportWizardRegistry()
					.findWizard(id);
		}
		// Then if we have a wizard, open it.
		if (descriptor != null) {
			IWizard wizard = descriptor.createWizard();
			WizardDialog wd = new WizardDialog(PlatformUI.getWorkbench()
					.getDisplay().getActiveShell(), wizard);
			wd.setTitle(wizard.getWindowTitle());
			if (selection != null && wizard instanceof IWorkbenchWizard) {
				((IWorkbenchWizard) wizard).init(null,
						(IStructuredSelection) selection.getSelection());
			}
			wd.open();
			return wizard;
		}
		return null;
	}

}
