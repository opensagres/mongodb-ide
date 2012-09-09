package fr.opensagres.nosql.ide.ui.viewers.editor;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public abstract class ControlEditorColumnLabelProvider<Parent extends Composite, I extends Item, CE extends ControlEditor, Editor extends Control>
		extends ColumnLabelProvider {

	private static final String KEY = ControlEditorColumnLabelProvider.class
			.getName();

	@Override
	public void update(ViewerCell cell) {
		I item = (I) cell.getItem();
		Parent parent = getParent(item);
		if (parent != null) {
			int columnIndex = cell.getColumnIndex();
			String key = new StringBuilder("_ControlEditorColumnLabelProvider")
					.append(columnIndex).toString();

			// Create control editor
			CE controlEditor = (CE) item.getData(key);
			if (controlEditor == null) {
				controlEditor = createControlEditor(parent, cell.getElement());
				if (controlEditor != null) {
					item.setData(key, controlEditor);
					// Create editor
					Editor editor = createEditor(parent, item,
							cell.getColumnIndex(), cell.getElement(), cell);
					if (editor != null) {
						updateControlEditor(controlEditor, editor, item,
								cell.getColumnIndex());
					}
				}
			} else {
				updateEditor((Editor) controlEditor.getEditor(),
						cell.getElement());
			}
		}
		super.update(cell);
	}

	protected abstract void updateEditor(Editor editor, Object element);

	protected abstract void updateControlEditor(CE controlEditor,
			Editor editor, I item, int columnIndex);

	protected abstract Parent getParent(I item);

	protected CE createControlEditor(Parent parent, Object element) {
		if (hasControlEditor(element)) {
			return doCreateControlEditor(parent, element);
		}
		return null;
	}

	protected abstract boolean hasControlEditor(Object element);

	protected abstract CE doCreateControlEditor(Parent parent, Object element);

	protected abstract Editor createEditor(Parent parent, I item,
			int columnIndex, Object element, ViewerCell cell);
}
