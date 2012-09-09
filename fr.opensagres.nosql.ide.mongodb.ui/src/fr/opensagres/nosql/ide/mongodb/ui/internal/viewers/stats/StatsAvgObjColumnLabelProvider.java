package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats;

import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionStats;

public class StatsAvgObjColumnLabelProvider extends
		GradientProgressBarColumnLabelProvider {

	private static StatsAvgObjColumnLabelProvider instance;

	public static StatsAvgObjColumnLabelProvider getInstance() {
		synchronized (StatsAvgObjColumnLabelProvider.class) {
			if (instance == null) {
				instance = new StatsAvgObjColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof CollectionStats) {
			Integer avgObj = ((CollectionStats) element).getAvgObj();
			return StringUtils.getValue(avgObj);
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
			value = stats.getPercentAvgObj();
		}
		return value != null ? value : 0;
	}
}
