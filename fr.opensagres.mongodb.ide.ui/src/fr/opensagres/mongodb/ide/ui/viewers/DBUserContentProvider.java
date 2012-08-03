package fr.opensagres.mongodb.ide.ui.viewers;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class DBUserContentProvider implements IStructuredContentProvider {

	private static DBUserContentProvider instance;

	public static DBUserContentProvider getInstance() {
		synchronized (DBUserContentProvider.class) {
			if (instance == null) {
				instance = new DBUserContentProvider();
			}
			return instance;
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	public Object[] getElements(Object inputElement) {
		return ((Collection)inputElement).toArray();
	}
}
