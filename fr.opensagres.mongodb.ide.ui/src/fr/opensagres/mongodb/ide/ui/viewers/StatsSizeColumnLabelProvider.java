package fr.opensagres.mongodb.ide.ui.viewers;

import org.eclipse.swt.graphics.Image;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.ui.viewers.editor.GradientProgressBarColumnLabelProvider;

public class StatsSizeColumnLabelProvider extends
		GradientProgressBarColumnLabelProvider {

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

	@Override
	protected long getPercent(Object element) {
		if (element instanceof CollectionStats) {
			CollectionStats stats = (CollectionStats) element;
			return Math.round(stats.getPercentSize());

		}
		return 0;
	}
}
