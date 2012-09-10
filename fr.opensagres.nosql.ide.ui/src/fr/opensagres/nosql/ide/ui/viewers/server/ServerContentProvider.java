package fr.opensagres.nosql.ide.ui.viewers.server;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ServerContentProvider implements IStructuredContentProvider {

	private static ServerContentProvider instance;

	public static ServerContentProvider getInstance() {
		synchronized (ServerContentProvider.class) {
			if (instance == null) {
				instance = new ServerContentProvider();
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
