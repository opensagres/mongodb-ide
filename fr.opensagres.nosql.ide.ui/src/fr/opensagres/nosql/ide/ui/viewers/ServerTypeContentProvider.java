package fr.opensagres.nosql.ide.ui.viewers;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ServerTypeContentProvider implements IStructuredContentProvider {

	private static ServerTypeContentProvider instance;

	public static ServerTypeContentProvider getInstance() {
		synchronized (ServerTypeContentProvider.class) {
			if (instance == null) {
				instance = new ServerTypeContentProvider();
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
