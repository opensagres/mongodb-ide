package fr.opensagres.mongodb.ide.ui.viewers;

import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.TreeItem;

import com.mongodb.BasicDBObject;

public class DBObjectKeyColumnLabelProvider extends ColumnLabelProvider {

	private static DBObjectKeyColumnLabelProvider instance;

	public static DBObjectKeyColumnLabelProvider getInstance() {
		synchronized (DBObjectKeyColumnLabelProvider.class) {
			if (instance == null) {
				instance = new DBObjectKeyColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Entry) {
			Entry entry = (Entry) element;
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Collection) {
				StringBuilder keyText = new StringBuilder(
						key != null ? key.toString() : "");
				keyText.append(" [");
				keyText.append(((Collection) value).size());
				keyText.append("]");
				return keyText.toString();
			}
			return (key != null) ? key.toString() : "";
		}
		return "";
	}

	@Override
	public void update(ViewerCell cell) {
		super.update(cell);
		Object element = cell.getElement();
		if (element instanceof BasicDBObject) {
			// Label for root item is like '(0) {...}'
			TreeItem item = (TreeItem) cell.getViewerRow().getItem();
			int index = item.getParent().indexOf(item);
			cell.setText("(" + index + ") {...}");
		}
	}

}
