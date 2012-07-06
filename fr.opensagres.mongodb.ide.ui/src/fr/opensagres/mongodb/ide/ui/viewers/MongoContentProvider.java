package fr.opensagres.mongodb.ide.ui.viewers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import fr.opensagres.mongodb.ide.core.IServerManager;
import fr.opensagres.mongodb.ide.core.model.TreeContainerNode;
import fr.opensagres.mongodb.ide.core.model.TreeSimpleNode;

public class MongoContentProvider implements ITreeContentProvider {

	private static MongoContentProvider instance;

	public static MongoContentProvider getInstance() {
		synchronized (MongoContentProvider.class) {
			if (instance == null) {
				instance = new MongoContentProvider();
			}
			return instance;
		}
	}

	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof IServerManager) {
			return ((IServerManager) inputElement).getServers().toArray(
					TreeSimpleNode.EMPTY);
		}
		return TreeSimpleNode.EMPTY;
	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof TreeContainerNode) {
			return ((TreeContainerNode) parentElement).getChildren().toArray(
					TreeSimpleNode.EMPTY);
		}
		return TreeSimpleNode.EMPTY;
	}

	public Object getParent(Object element) {
		return ((TreeSimpleNode) element).getParent();
	}

	public boolean hasChildren(Object element) {
		return element instanceof TreeContainerNode;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	public void dispose() {

	}

}
