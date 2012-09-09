package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats;

import com.mongodb.tools.driver.StatsHelper;

import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionStats;
import fr.opensagres.nosql.ide.mongodb.core.model.stats.IndexStats;

public class StatsIndexSizeColumnLabelProvider extends
		GradientProgressBarColumnLabelProvider {

	private static StatsIndexSizeColumnLabelProvider instance;

	public static StatsIndexSizeColumnLabelProvider getInstance() {
		synchronized (StatsIndexSizeColumnLabelProvider.class) {
			if (instance == null) {
				instance = new StatsIndexSizeColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof CollectionStats) {
			Integer size = ((CollectionStats) element).getTotalIndexSize();
			return StatsHelper.formatAsBytes(size);
		}
		if (element instanceof IndexStats) {
			Integer size = ((IndexStats) element).getIndexSize();
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
			value = stats.getPercentIndexSize();
		} else if (element instanceof IndexStats) {
			IndexStats stats = (IndexStats) element;
			value = stats.getPercentIndexSize();
		}
		return value != null ? value : 0;
	}
}
