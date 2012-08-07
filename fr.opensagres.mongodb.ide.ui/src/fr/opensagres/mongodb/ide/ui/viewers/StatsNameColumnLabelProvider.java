package fr.opensagres.mongodb.ide.ui.viewers;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;

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
			return ((CollectionStats) element).getCollection().getName();
		}
		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof CollectionStats) {
			return ImageResources.getImage(ImageResources.IMG_COLLECTION_16);
		}
		return super.getImage(element);
	}
}
