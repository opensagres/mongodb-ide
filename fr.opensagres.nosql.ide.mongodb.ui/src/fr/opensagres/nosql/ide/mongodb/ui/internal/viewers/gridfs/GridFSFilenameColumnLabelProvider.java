package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.gridfs;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import com.mongodb.gridfs.GridFSDBFile;

public class GridFSFilenameColumnLabelProvider extends ColumnLabelProvider {

	private static GridFSFilenameColumnLabelProvider instance;

	public static GridFSFilenameColumnLabelProvider getInstance() {
		synchronized (GridFSFilenameColumnLabelProvider.class) {
			if (instance == null) {
				instance = new GridFSFilenameColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof GridFSDBFile) {
			return ((GridFSDBFile) element).getFilename();
		}
		return "";
	}

}
