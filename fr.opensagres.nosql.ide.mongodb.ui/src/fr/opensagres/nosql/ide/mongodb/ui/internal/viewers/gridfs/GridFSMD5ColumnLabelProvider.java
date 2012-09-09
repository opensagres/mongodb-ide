package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.gridfs;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import com.mongodb.gridfs.GridFSDBFile;

public class GridFSMD5ColumnLabelProvider extends ColumnLabelProvider {

	private static GridFSMD5ColumnLabelProvider instance;

	public static GridFSMD5ColumnLabelProvider getInstance() {
		synchronized (GridFSMD5ColumnLabelProvider.class) {
			if (instance == null) {
				instance = new GridFSMD5ColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof GridFSDBFile) {
			return ((GridFSDBFile) element).getMD5();
		}
		return "";
	}

}
