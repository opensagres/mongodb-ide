package fr.opensagres.nosql.ide.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import fr.opensagres.nosql.ide.core.model.ICollection;
import fr.opensagres.nosql.ide.core.model.IDatabase;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.model.TreeSimpleNode;

public abstract class AbstractSelectNodeWizard extends AbstractNewWizard {

	public enum SelectType {
		Server, Database, Collection
	}

	private IServer server;
	private IDatabase database;
	private ICollection collection;

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
				init(node);
			}
		}
	}

	public void init(ITreeSimpleNode node) {
		if (node instanceof IServer) {
			server = (IServer) node;
		} else if (node instanceof IDatabase) {
			database = (IDatabase) node;
			server = database.getParent();
		} else if (node instanceof ICollection) {
			collection = (ICollection) node;
			database = collection.getDatabase();
			server = database.getParent();
		}
	}

	public IServer getInitialServer() {
		return server;
	}

	public IDatabase getInitialDatabase() {
		return database;
	}
}
