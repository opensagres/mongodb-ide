package fr.opensagres.nosql.ide.ui.viewers.editor;

import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public abstract class AbstractGradientProgressBarColumnLabelProvider extends
		TreeEditorColumnLabelProvider<GradientProgressBar> implements
		IProgressBarValueProvider {

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
