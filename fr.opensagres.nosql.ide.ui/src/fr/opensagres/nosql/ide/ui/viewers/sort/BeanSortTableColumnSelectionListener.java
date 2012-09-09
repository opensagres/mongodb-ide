package fr.opensagres.nosql.ide.ui.viewers.sort;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class BeanSortTableColumnSelectionListener extends
		AbstractBeanSortColumnSelectionListener {

	public BeanSortTableColumnSelectionListener(String propertyName,
			TableViewer viewer) {
		super(propertyName, viewer);
	}

	public BeanSortTableColumnSelectionListener(String propertyName,
			int sortDirection, TableViewer viewer) {
		super(propertyName, sortDirection, viewer);
	}

	@Override
	protected void updateSortUI(SelectionEvent e) {
		// 1) Get tree column which fire this selection event
		TableColumn treeColumn = (TableColumn) e.getSource();
		// 2) Get the owner tree
		Table tree = treeColumn.getParent();
		// 3) Modify the SWT Table sort
		tree.setSortColumn(treeColumn);
		tree.setSortDirection(getSortDirection());
	}

}
