package fr.opensagres.mongodb.ide.ui.viewers.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;

public abstract class GradientProgressBarColumnLabelProvider extends
		TreeEditorColumnLabelProvider<GradientProgressBar> {

	@Override
	protected boolean hasControlEditor(Object element) {
		return element instanceof CollectionStats;
	}

	@Override
	protected void updateEditor(GradientProgressBar editor, Object element) {

	}

	@Override
	protected GradientProgressBar createEditor(Tree parent, TreeItem item,
			int columnIndex, Object element) {
		final TreeColumn treeColumn = parent.getColumn(columnIndex);
		IWidthProvider widthProvider = new IWidthProvider() {

			public int getWidth() {
				return treeColumn.getWidth();
			}
		};
		String text = getText(element);
		return new GradientProgressBar(parent, SWT.NONE, widthProvider,
				getPercent(element), text);
	}

	protected abstract long getPercent(Object element);

}
