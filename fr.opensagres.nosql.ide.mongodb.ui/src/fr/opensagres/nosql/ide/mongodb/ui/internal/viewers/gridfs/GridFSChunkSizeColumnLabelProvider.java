package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.gridfs;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.tools.driver.StatsHelper;

public class GridFSChunkSizeColumnLabelProvider extends ColumnLabelProvider {

	private static GridFSChunkSizeColumnLabelProvider instance;

	public static GridFSChunkSizeColumnLabelProvider getInstance() {
		synchronized (GridFSChunkSizeColumnLabelProvider.class) {
			if (instance == null) {
				instance = new GridFSChunkSizeColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof GridFSDBFile) {
			return StatsHelper.formatAsBytes(((GridFSDBFile) element)
					.getChunkSize());
		}
		return "";
	}

}
