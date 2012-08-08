package fr.opensagres.mongodb.ide.ui.viewers;

import org.eclipse.swt.graphics.Image;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.ui.viewers.editor.GradientProgressBarColumnLabelProvider;

public class StatsCountColumnLabelProvider extends
		GradientProgressBarColumnLabelProvider {

	private static StatsCountColumnLabelProvider instance;

	public static StatsCountColumnLabelProvider getInstance() {
		synchronized (StatsCountColumnLabelProvider.class) {
			if (instance == null) {
				instance = new StatsCountColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof CollectionStats) {
			double count = ((CollectionStats) element).getCount();
			return String.valueOf(count);
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
