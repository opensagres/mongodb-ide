package fr.opensagres.nosql.ide.ui.viewers.database;

import org.eclipse.jface.viewers.LabelProvider;

import fr.opensagres.nosql.ide.core.model.IDatabase;

public class DatabaseLabelProvider extends LabelProvider {

	private static DatabaseLabelProvider instance;

	public static DatabaseLabelProvider getInstance() {
		synchronized (DatabaseLabelProvider.class) {
			if (instance == null) {
				instance = new DatabaseLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		return ((IDatabase) element).getName();
	}

}
