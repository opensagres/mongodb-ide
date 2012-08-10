package fr.opensagres.mongodb.ide.ui.viewers.stats;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.ui.viewers.editor.GradientProgressBarColumnLabelProvider;

public class StatsPaddingColumnLabelProvider extends
		GradientProgressBarColumnLabelProvider {

	private static StatsPaddingColumnLabelProvider instance;

	public static StatsPaddingColumnLabelProvider getInstance() {
		synchronized (StatsPaddingColumnLabelProvider.class) {
			if (instance == null) {
				instance = new StatsPaddingColumnLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof CollectionStats) {
			double padding = ((CollectionStats) element).getPadding();
			return String.valueOf(padding);
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
			return Math.round(stats.getPercentPadding());
		}
		return 0;
	}
}
