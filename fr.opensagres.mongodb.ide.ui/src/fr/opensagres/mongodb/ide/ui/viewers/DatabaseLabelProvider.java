package fr.opensagres.mongodb.ide.ui.viewers;

import org.eclipse.jface.viewers.LabelProvider;

import fr.opensagres.mongodb.ide.core.model.Database;

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
		return ((Database) element).getName();
	}

}
