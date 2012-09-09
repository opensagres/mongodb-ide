package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats;

import com.mongodb.tools.driver.StatsHelper;

import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionStats;

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
			Integer storage = ((CollectionStats) element).getStorage();
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
	public int getProgressBarValue(Object element) {
		Integer value = null;
		if (element instanceof CollectionStats) {
			CollectionStats stats = (CollectionStats) element;
			value = stats.getPercentStorage();
		}
		return value != null ? value : 0;
	}
}
