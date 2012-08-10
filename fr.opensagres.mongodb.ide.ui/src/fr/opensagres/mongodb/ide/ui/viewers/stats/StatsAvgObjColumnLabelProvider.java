package fr.opensagres.mongodb.ide.ui.viewers.stats;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;
import fr.opensagres.mongodb.ide.ui.viewers.editor.GradientProgressBarColumnLabelProvider;

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
