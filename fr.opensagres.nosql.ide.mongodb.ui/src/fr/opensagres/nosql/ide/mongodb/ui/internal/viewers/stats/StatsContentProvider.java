package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats;

import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionStats;

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
		return new Object[0];
	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof CollectionStats) {
			return ((CollectionStats)parentElement).toArray();
		}
		return null;
	}

	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChildren(Object element) {
		return element instanceof CollectionStats;
	}
}
