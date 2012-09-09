package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import com.mongodb.DBObject;

public class DBObjectValueColumnLabelProvider extends ColumnLabelProvider {

	private static DBObjectValueColumnLabelProvider instance;

	public static DBObjectValueColumnLabelProvider getInstance() {
		synchronized (DBObjectValueColumnLabelProvider.class) {
			if (instance == null) {
				instance = new DBObjectValueColumnLabelProvider();
			}
			return instance;
		}
	}
	
	@Override
	public String getText(Object element) {
		if (element instanceof DBObject) {
			return "";
		}
		if (element instanceof Entry) {
			Entry entry = (Entry) element;
			Object value = entry.getValue();
			if (value instanceof DBObject) {
				return "";
			}
			if (value instanceof byte[]) {
				return "";
			}
			return (value != null) ? value.toString() : "";
		}
		return "";
	}

}
