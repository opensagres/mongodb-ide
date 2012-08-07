package fr.opensagres.mongodb.ide.ui.viewers;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;

public class StatsSizeColumnLabelProvider extends ColumnLabelProvider {

	private static StatsSizeColumnLabelProvider instance;

	public static StatsSizeColumnLabelProvider getInstance() {
		synchronized (StatsSizeColumnLabelProvider.class) {
			if (instance == null) {
				instance = new StatsSizeColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof CollectionStats) {
			Double size = ((CollectionStats) element).getSize();
			return size != null ? String.valueOf(size) : "";
		}
		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		return super.getImage(element);
	}
}
