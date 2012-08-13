package fr.opensagres.mongodb.ide.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.TreeSimpleNode;

public abstract class AbstractSelectNodeWizard extends Wizard implements
		INewWizard {

	public enum SelectType {
		Server, Database, Collection
	}

	private final SelectType type;
	private Server server;
	private Database database;

	private SelectServerWizardPage selectServerWizardPage;
	private SelectDatabaseWizardPage selectDatabaseWizardPage;

	public AbstractSelectNodeWizard(SelectType type) {
		this.type = type;
	}

	@Override
	public void addPages() {
		if (server == null) {
			selectServerWizardPage = new SelectServerWizardPage();
			addPage(selectServerWizardPage);
		}
		if (type != SelectType.Server && database == null) {
			selectDatabaseWizardPage = new SelectDatabaseWizardPage();
			addPage(selectDatabaseWizardPage);
		}
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.server = null;
		this.database = null;
		if (!selection.isEmpty()) {
			Object element = selection.getFirstElement();
			if (element instanceof TreeSimpleNode) {
				TreeSimpleNode node = (TreeSimpleNode) element;
				if (node instanceof Server) {
					server = (Server) node;
				} else if (node instanceof Database) {
					database = (Database) node;
					server = database.getParent();
				} else {

				}
			}
		}
	}

	public Server getServer() {
		if (selectServerWizardPage != null) {
			return selectServerWizardPage.getServer();
		}
		return server;
	}

	public Database getDatabase() {
		return database;
	}
}
