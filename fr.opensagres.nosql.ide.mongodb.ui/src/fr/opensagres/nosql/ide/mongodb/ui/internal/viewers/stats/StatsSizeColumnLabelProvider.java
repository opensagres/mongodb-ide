package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats;

import com.mongodb.tools.driver.StatsHelper;

import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionStats;

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
			Integer size = ((CollectionStats) element).getSize();
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
	public int getProgressBarValue(Object element) {
		Integer value = null;
		if (element instanceof CollectionStats) {
			CollectionStats stats = (CollectionStats) element;
			value = stats.getPercentSize();
		}
		return value != null ? value : 0;
	}
}
