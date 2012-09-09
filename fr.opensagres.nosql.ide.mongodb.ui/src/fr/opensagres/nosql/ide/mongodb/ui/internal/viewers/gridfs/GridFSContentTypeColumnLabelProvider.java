package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.gridfs;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import com.mongodb.gridfs.GridFSDBFile;

public class GridFSContentTypeColumnLabelProvider extends ColumnLabelProvider {

	private static GridFSContentTypeColumnLabelProvider instance;

	public static GridFSContentTypeColumnLabelProvider getInstance() {
		synchronized (GridFSContentTypeColumnLabelProvider.class) {
			if (instance == null) {
				instance = new GridFSContentTypeColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof GridFSDBFile) {
			return ((GridFSDBFile) element).getContentType();
		}
		return "";
	}

}
