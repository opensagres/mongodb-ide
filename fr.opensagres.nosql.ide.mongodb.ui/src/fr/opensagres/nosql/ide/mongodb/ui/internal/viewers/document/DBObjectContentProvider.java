package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.BSONObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.mongodb.DBObject;

public class DBObjectContentProvider implements ITreeContentProvider {

	private static final Object[] EMPTY_OBJECTS = new Object[0];
	private static DBObjectContentProvider instance;

	public static DBObjectContentProvider getInstance() {
		synchronized (DBObjectContentProvider.class) {
			if (instance == null) {
				instance = new DBObjectContentProvider();
			}
			return instance;
		}
	}

	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Object[]) {
			return (Object[]) inputElement;
		}
		if (inputElement instanceof Collection) {
			return ((Collection) inputElement).toArray();
		}
		return EMPTY_OBJECTS;
	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Map) {
			Map map = (Map) parentElement;
			return map.entrySet().toArray();
		}
		if (parentElement instanceof BSONObject) {
			return getChildren(((BSONObject) parentElement).toMap());
		}
		if (parentElement instanceof Entry) {
			Entry entry = (Entry) parentElement;
			return getChildren(entry.getValue());
		}
		if (parentElement instanceof Collection) {
			return ((Collection) parentElement).toArray();
		}
		return EMPTY_OBJECTS;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof DBObject) {
			return true;
		}
		if (element instanceof Entry) {
			Entry entry = (Entry) element;
			return (entry.getValue() instanceof DBObject);
		}
		return false;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	public void dispose() {

	}

}
