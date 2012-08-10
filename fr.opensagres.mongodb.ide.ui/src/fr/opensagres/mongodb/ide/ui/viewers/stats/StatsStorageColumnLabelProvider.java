package fr.opensagres.mongodb.ide.ui.viewers.stats;

import com.mongodb.tools.driver.StatsHelper;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.ui.viewers.editor.GradientProgressBarColumnLabelProvider;

public class StatsStorageColumnLabelProvider extends
		GradientProgressBarColumnLabelProvider {

	private static StatsStorageColumnLabelProvider instance;

	public static StatsStorageColumnLabelProvider getInstance() {
		synchronized (StatsStorageColumnLabelProvider.class) {
			if (instance == null) {
				instance = new StatsStorageColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof CollectionStats) {
			double storage = ((CollectionStats) element).getStorage();
			return StatsHelper.formatAsBytes(storage);
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
			return Math.round(stats.getPercentStorage());
		}
		return 0;
	}
}
