package fr.opensagres.mongodb.ide.ui.viewers.stats;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;
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
			Integer count = ((CollectionStats) element).getCount();
			return StringUtils.getValue(count);
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
			value = stats.getPercentCount();
		}
		return value != null ? value : 0;
	}
}
