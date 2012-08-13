package fr.opensagres.mongodb.ide.ui.wizards.database;

import fr.opensagres.mongodb.ide.ui.dialogs.StackTraceErrorDialog;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractSelectNodeWizard;

public class NewDatabaseWizard extends AbstractSelectNodeWizard {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.wizards.database.NewDatabaseWizard";

	private final NewDatabaseWizardPage page;

	public NewDatabaseWizard() {
		super(SelectType.Server);
		super.setWindowTitle(Messages.NewDatabaseWizard_title);
		super.setNeedsProgressMonitor(true);
		page = new NewDatabaseWizardPage();
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		String databaseName = page.getDatabaseName();
		try {
			getServer().createDatabase(databaseName);
		} catch (Exception e) {
			StackTraceErrorDialog.openError(getShell(), "dialogTitle", "title",
					e);
			return false;
		}
		return true;
	}

}
