package fr.opensagres.mongodb.ide.ui.viewers;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class DatabaseContentProvider implements IStructuredContentProvider {

	private static DatabaseContentProvider instance;

	public static DatabaseContentProvider getInstance() {
		synchronized (DatabaseContentProvider.class) {
			if (instance == null) {
				instance = new DatabaseContentProvider();
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
