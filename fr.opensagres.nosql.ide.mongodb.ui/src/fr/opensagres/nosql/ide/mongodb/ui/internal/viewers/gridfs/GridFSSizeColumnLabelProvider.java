package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.gridfs;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.tools.driver.StatsHelper;

public class GridFSSizeColumnLabelProvider extends ColumnLabelProvider {

	private static GridFSSizeColumnLabelProvider instance;

	public static GridFSSizeColumnLabelProvider getInstance() {
		synchronized (GridFSSizeColumnLabelProvider.class) {
			if (instance == null) {
				instance = new GridFSSizeColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof GridFSDBFile) {
			return StatsHelper.formatAsBytes(((GridFSDBFile) element)
					.getLength());
		}
		return "";
	}

}
