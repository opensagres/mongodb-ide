package fr.opensagres.mongodb.ide.ui.viewers.gridfs;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import com.mongodb.gridfs.GridFSDBFile;

import fr.opensagres.mongodb.ide.core.utils.DateUtils;

public class GridFSUploadedColumnLabelProvider extends ColumnLabelProvider {

	private static GridFSUploadedColumnLabelProvider instance;

	public static GridFSUploadedColumnLabelProvider getInstance() {
		synchronized (GridFSUploadedColumnLabelProvider.class) {
			if (instance == null) {
				instance = new GridFSUploadedColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof GridFSDBFile) {
			return DateUtils.formatDatetime(((GridFSDBFile) element)
					.getUploadDate());
		}
		return "";
	}

}
