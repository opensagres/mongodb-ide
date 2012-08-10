package fr.opensagres.mongodb.ide.ui.viewers.stats;

import com.mongodb.tools.driver.StatsHelper;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.core.model.stats.IndexStats;
import fr.opensagres.mongodb.ide.ui.viewers.editor.GradientProgressBarColumnLabelProvider;

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
			double size = ((CollectionStats) element).getTotalIndexSize();
			return StatsHelper.formatAsBytes(size);
		}
		if (element instanceof IndexStats) {
			double size = ((IndexStats) element).getIndexSize();
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
			return Math.round(stats.getPercentIndexSize());
		}
		if (element instanceof IndexStats) {
			IndexStats stats = (IndexStats) element;
			return Math.round(stats.getPercentIndexSize());
		}
		return 0;
	}
}
