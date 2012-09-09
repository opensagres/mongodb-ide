package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionStats;
import fr.opensagres.nosql.ide.mongodb.core.model.stats.IndexStats;
import fr.opensagres.nosql.ide.mongodb.ui.internal.ImageResources;

public class StatsNameColumnLabelProvider extends ColumnLabelProvider {

	private static StatsNameColumnLabelProvider instance;

	public static StatsNameColumnLabelProvider getInstance() {
		synchronized (StatsNameColumnLabelProvider.class) {
			if (instance == null) {
				instance = new StatsNameColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof CollectionStats) {
			return ((CollectionStats) element).getName();
		}
		if (element instanceof IndexStats) {
			return ((IndexStats) element).getId();
		}
		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof CollectionStats) {
			return ImageResources.getImage(ImageResources.IMG_COLLECTION_16);
		}
		if (element instanceof IndexStats) {
			return ImageResources.getImage(ImageResources.IMG_INDEX_16);
		}
		return super.getImage(element);
	}
}
