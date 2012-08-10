package fr.opensagres.mongodb.ide.ui.viewers.stats;

import com.mongodb.tools.driver.StatsHelper;

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
			double size = ((CollectionStats) element).getSize();
			return StatsHelper.formatAsBytes(size);
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.opensagres.mongodb.ide.ui.viewers.editor.IProgressBarValueProvider
	 * #getProgressBarValue(java.lang.Object)
	 */
	public double getProgressBarValue(Object element) {
		if (element instanceof CollectionStats) {
			CollectionStats stats = (CollectionStats) element;
			return Math.round(stats.getPercentSize());
		}
		return 0;
	}
}
