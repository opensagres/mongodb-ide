package fr.opensagres.mongodb.ide.ui.viewers;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class StatsContentProvider implements ITreeContentProvider {

	private static StatsContentProvider instance;

	public static StatsContentProvider getInstance() {
		synchronized (StatsContentProvider.class) {
			if (instance == null) {
				instance = new StatsContentProvider();
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
		if (inputElement instanceof Collection) {
			return ((Collection) inputElement).toArray();
		}
		return ((Collection) inputElement).toArray();
	}

	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}
}
