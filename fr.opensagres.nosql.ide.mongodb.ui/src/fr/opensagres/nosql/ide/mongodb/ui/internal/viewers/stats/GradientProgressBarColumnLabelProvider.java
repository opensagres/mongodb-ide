package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats;

import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionStats;
import fr.opensagres.nosql.ide.mongodb.core.model.stats.IndexStats;
import fr.opensagres.nosql.ide.ui.viewers.editor.AbstractGradientProgressBarColumnLabelProvider;

public abstract class GradientProgressBarColumnLabelProvider extends
		AbstractGradientProgressBarColumnLabelProvider {

	@Override
	protected boolean hasControlEditor(Object element) {
		return element instanceof CollectionStats
				|| element instanceof IndexStats;
	}
}
