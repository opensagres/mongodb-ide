package fr.opensagres.mongodb.ide.ui.viewers.editor;

import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.core.model.stats.IndexStats;

public abstract class GradientProgressBarColumnLabelProvider extends
		TreeEditorColumnLabelProvider<GradientProgressBar> implements
		IProgressBarValueProvider {

	@Override
	protected boolean hasControlEditor(Object element) {
		return element instanceof CollectionStats
				|| element instanceof IndexStats;
	}

	@Override
	protected void updateEditor(GradientProgressBar editor, Object element) {
		editor.setText(getText(element));
		editor.setValue(getProgressBarValue(element));
	}

	@Override
	protected GradientProgressBar createEditor(Tree parent,
			final TreeItem item, int columnIndex, Object element,
			final ViewerCell cell) {
		final TreeColumn treeColumn = parent.getColumn(columnIndex);
		IWidthProvider widthProvider = new IWidthProvider() {

			public int getWidth() {
				return treeColumn.getWidth();
			}
		};
		String text = getText(element);
		return new GradientProgressBar(parent, SWT.NONE, item.getBackground(),
				widthProvider, getProgressBarValue(element), text);
	}

}
