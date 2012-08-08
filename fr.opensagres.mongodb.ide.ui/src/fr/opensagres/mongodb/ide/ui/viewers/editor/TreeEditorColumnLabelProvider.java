package fr.opensagres.mongodb.ide.ui.viewers.editor;

import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public abstract class TreeEditorColumnLabelProvider<Editor extends Control>
		extends
		ControlEditorColumnLabelProvider<Tree, TreeItem, TreeEditor, Editor> {

	@Override
	protected Tree getParent(TreeItem item) {
		return item.getParent();
	}

	@Override
	protected TreeEditor doCreateControlEditor(Tree parent, Object element) {
		TreeEditor editor = new TreeEditor(parent);
		editor.grabHorizontal = editor.grabVertical = true;
		return editor;
	}

	@Override
	protected void updateControlEditor(TreeEditor controlEditor,
			Editor editor, TreeItem item, int columnIndex) {
		controlEditor.setEditor(editor, item, columnIndex);
	}
}
