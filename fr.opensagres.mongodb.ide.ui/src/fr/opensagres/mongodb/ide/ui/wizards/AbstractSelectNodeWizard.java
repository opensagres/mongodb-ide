package fr.opensagres.mongodb.ide.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.TreeSimpleNode;

public abstract class AbstractSelectNodeWizard extends AbstractNewWizard {

	public enum SelectType {
		Server, Database, Collection
	}

	private Server server;
	private Database database;
	private Collection collection;

	public AbstractSelectNodeWizard() {
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
				} else if (node instanceof Collection) {
					collection = (Collection) node;
					database = collection.getDatabase();
					server = database.getParent();
				}
			}
		}
	}

	public Server getInitialServer() {
		return server;
	}

	public Database getInitialDatabase() {
		return database;
	}
}
