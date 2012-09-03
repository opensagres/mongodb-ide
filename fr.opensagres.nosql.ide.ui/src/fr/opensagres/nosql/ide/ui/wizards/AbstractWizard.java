package fr.opensagres.nosql.ide.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

import fr.opensagres.nosql.ide.ui.dialogs.StackTraceErrorDialog;

public abstract class AbstractWizard extends Wizard {

	@Override
	public final boolean performFinish() {
		try {
			return doPerformFinish();
		} catch (Exception e) {
			StackTraceErrorDialog.openError(getShell(), "dialogTitle", "title",
					e);
			return false;
		}
	}

	protected abstract boolean doPerformFinish() throws Exception;

}
