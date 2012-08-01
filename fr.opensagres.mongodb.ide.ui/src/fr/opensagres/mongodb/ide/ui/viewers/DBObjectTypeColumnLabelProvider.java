package fr.opensagres.mongodb.ide.ui.viewers;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.ColumnLabelProvider;

public class DBObjectTypeColumnLabelProvider extends ColumnLabelProvider {
	
	private static DBObjectTypeColumnLabelProvider instance;

	public static DBObjectTypeColumnLabelProvider getInstance() {
		synchronized (DBObjectTypeColumnLabelProvider.class) {
			if (instance == null) {
				instance = new DBObjectTypeColumnLabelProvider();
			}
			return instance;
		}
	}
	
	@Override
	public String getText(Object element) {
		Object value = element;
		if (element instanceof Entry) {
			Entry entry = (Entry) element;
			value = entry.getValue();
		}
		return (value != null) ? value.getClass().getSimpleName() : "";
	}

}
