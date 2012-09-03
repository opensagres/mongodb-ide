package fr.opensagres.nosql.ide.ui.viewers;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class RuntimeContentProvider implements IStructuredContentProvider {

	private static RuntimeContentProvider instance;

	public static RuntimeContentProvider getInstance() {
		synchronized (RuntimeContentProvider.class) {
			if (instance == null) {
				instance = new RuntimeContentProvider();
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
